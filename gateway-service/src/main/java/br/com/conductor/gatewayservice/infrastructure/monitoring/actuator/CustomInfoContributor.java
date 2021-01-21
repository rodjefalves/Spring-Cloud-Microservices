package br.com.conductor.gatewayservice.infrastructure.monitoring.actuator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CustomInfoContributor implements InfoContributor {

    public static final String METRICS_KEY = "metrics";
    public static final String JVM_MEMORY_USED_KEY = "jvm.memory.used";
    public static final String JVM_MEMORY_TOTAL_KEY = "jvm.memory.max";
    public static final String EXTRAS_KEY = "extras";
    public static final String HEALTH_KEY = "health";
    public static final String STATUS_KEY = "status";
    public static final String SPRING_PROFILES_ACTIVE_KEY = "spring.profiles.active";

    @Autowired
    private MetricsEndpoint metricsContributor;

    @Autowired
    private HealthEndpoint healthContributor;

    @Autowired
    private Environment environment;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> health = new HashMap<>();
        Map<String, Double> metrics = new HashMap<>();
        Map<String, String> extras = new HashMap<>();
        health.put(STATUS_KEY, healthContributor.health().getStatus().toString());
        metrics.put(JVM_MEMORY_USED_KEY, getMetric(JVM_MEMORY_USED_KEY));
        metrics.put(JVM_MEMORY_TOTAL_KEY, getMetric(JVM_MEMORY_TOTAL_KEY));
        extras.put(SPRING_PROFILES_ACTIVE_KEY, String.join(",", environment.getActiveProfiles()));
        builder.withDetail(HEALTH_KEY, health);
        builder.withDetail(METRICS_KEY, metrics);
        builder.withDetail(EXTRAS_KEY, extras);
    }

    private Double getMetric(String metricName) {
        return metricsContributor.metric(metricName, null).getMeasurements().get(0).getValue();
    }

}

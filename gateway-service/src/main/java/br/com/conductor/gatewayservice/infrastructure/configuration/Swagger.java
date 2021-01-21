package br.com.conductor.gatewayservice.infrastructure.configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesEndpoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Primary
@Configuration
@EnableSwagger2
public class Swagger implements SwaggerResourcesProvider {

    private static final String DOCS_URI = "/v2/api-docs";
    private static final String DOCS_VERSION = "2.0";

    @Autowired
    private RoutesEndpoint routesEndpoint;

    @Override
    public List<SwaggerResource> get() {
        Map<String, String> map = routesEndpoint.invoke();
        return map.entrySet().stream().map(k -> {
            String service = k.getValue();
            return swaggerResource(service, "/" + service + DOCS_URI, DOCS_VERSION);
        }).filter(s -> null != s.getName()).collect(Collectors.toList());
    }
    /*public List<SwaggerResource> get() {
        Map<String, String> map = routesEndpoint.invoke();
        return map.keySet().stream().map(k -> {
            String service = StringUtils.substringBetween(k, "-", "-service");
            return swaggerResource(service, "/" + service + DOCS_URI, DOCS_VERSION);
        }).filter(s -> null != s.getName()).collect(Collectors.toList());
    }*/

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
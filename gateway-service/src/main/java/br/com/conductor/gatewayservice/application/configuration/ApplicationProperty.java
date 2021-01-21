package br.com.conductor.gatewayservice.application.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("gateway")
public class ApplicationProperty {

    private Seguranca seguranca = new Seguranca();

    public Seguranca getSeguranca() {
        return seguranca;
    }

    public void setSeguranca(Seguranca seguranca) {
        this.seguranca = seguranca;
    }

    public static class Seguranca {
        private List<String> origensPermitidas;

        public List<String> getOrigensPermitidas() {
            return origensPermitidas;
        }

        public void setOrigensPermitidas(List<String> origensPermitidas) {
            this.origensPermitidas = origensPermitidas;
        }

    }

}
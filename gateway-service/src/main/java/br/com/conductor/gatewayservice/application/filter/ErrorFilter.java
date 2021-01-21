package br.com.conductor.gatewayservice.application.filter;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import br.com.conductor.gatewayservice.infrastructure.log.LogBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ErrorFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorFilter.class);

    private static final String TIPO_FILTRO = "error";
    private static final String CHAVE_THROWABLE = "throwable";
    private static final int ORDEM_FILTRO = -1;
    private static final int LIMITE_REQUEST = 5000;

    @Override
    public String filterType() {
        return TIPO_FILTRO;
    }

    @Override
    public int filterOrder() {
        return ORDEM_FILTRO;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final Object throwable = ctx.get(CHAVE_THROWABLE);

        try {
            if (throwable instanceof ZuulException) {
                final ZuulException exception = (ZuulException) throwable;
                HttpServletRequest request = ctx.getRequest();
                int status = ctx.getResponse().getStatus();

                String conteudo = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

                LOG.error(LogBuilder.of()
                        .header("Falha ao consumir endpoint")
                        .row("url", ctx.getRequest().getRequestURL())
                        .row("request", StringUtils.abbreviate(conteudo, LIMITE_REQUEST))
                        .row("response", ctx.getResponseBody())
                        .row("status", ctx.getResponse().getStatus())
                        .build(), exception);

                ctx.remove(CHAVE_THROWABLE);
                ctx.setResponseStatusCode(0 == status ? HttpStatus.SERVICE_UNAVAILABLE.value() : status);
            }
        } catch (Exception ex) {
            LOG.error(LogBuilder.of()
                    .header("Erro ao consumir endpoint")
                    .row("url", ctx.getRequest().getRequestURL())
                    .build(), ex);
        }
        return null;
    }
}

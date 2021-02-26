package br.com.conductor.crudservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.service.contexts.SecurityContext;
import java.util.Arrays;
import java.util.List;

import static br.com.conductor.crudservice.security.JwtTokenUtil.AUTH_HEADER;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String AUTH_HEADER = "Authorization";
    private static final String HTTP_HEADER = "header";
    private static final String SCOPE = "global";
    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";

    @Autowired
    private ApplicationProperty appProperty;

    /*public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(appProperty.getApiInfo().getPacote()))
                //.apis(RequestHandlerSelectors.basePackage("br.com.conductor.crudservice.rest.controller"))
                .paths(PathSelectors.any())
                //.paths(PathSelectors.regex("/error/*"))
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(getApiKeys())
                .securityContexts(securityContext());

    }*/

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(appProperty.getApiInfo().getPacote()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(getApiKeys())
                .securityContexts(securityContext());
    }

    private List<ApiKey> getApiKeys() {
        return Arrays.asList(new ApiKey(AUTH_HEADER, AUTH_HEADER, HTTP_HEADER));
    }
    private List<SecurityReference> getSecurityReferences() {
        return Arrays.asList((new SecurityReference(AUTH_HEADER,
                (AuthorizationScope[]) Arrays.asList(new AuthorizationScope(SCOPE, SCOPE)).toArray())));
    }

    private List<SecurityContext> securityContext() {
        SecurityContext context = SecurityContext.builder()
                .securityReferences(getSecurityReferences())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
        return Arrays.asList(context);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(appProperty.getApiInfo().getTitulo())
                .description(appProperty.getApiInfo().getDescricao())
                .version(appProperty.getApiInfo().getVersao())
                .build();
    }
}
package br.com.conductor.crudservice.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String SCOPE = "global";
    private static final String HTTP_HEADER = "header";
    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";

    private ApplicationProperty appProperty;

    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(appProperty.getApiInfo().getPacote()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(appProperty.getApiInfo().getTitulo())
                .description(appProperty.getApiInfo().getDescricao())
                .version(appProperty.getApiInfo().getVersao())
                .build();
    }
}
package com.mou.gameforum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringfoxConfiguration {
    /**
     * config apis, for swagger
     * @return docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    /**
     * config api info, for swagger
     * @return api information
     */
    private ApiInfo apiInfo(){
        return new ApiInfo("论坛API文档",
                "Welcome us Api docs",
                "1.0",
                "Terms of Service",

                new Contact("OpenAPI",
                        "localhost:8080",
                        "i@mou.best"),
                "Apache",
                "http://www.apache.org/",
                Collections.emptyList()
        );
    }
}

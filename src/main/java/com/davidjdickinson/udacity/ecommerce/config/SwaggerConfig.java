package com.davidjdickinson.udacity.ecommerce.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String apiTitle = "eCommerce API - Udacity JDND Project 4";
    private String apiDescription = "A simple eCommerce application.";
    private String apiVersion = "0.1";
    private Contact apiContact = new springfox.documentation.service.Contact("David Dickinson", "https://github.com/biscaboy", "biscaboy@pm.me");
    private String apiLicense = "The MIT License";
    private String getApiLicenseUri = "/mit-license.html";
    private String apiTermsOfServiceUri = "/tos.html";

    @Bean
    public Docket api(@Qualifier("eCommerceServerUrl") String url) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo(url))
                .useDefaultResponseMessages(false);
    }

    private ApiInfo getApiInfo(String url) {

        ApiInfo info =  new ApiInfo(
                apiTitle,
                apiDescription,
                apiVersion,
                url + apiTermsOfServiceUri,
                apiContact,
                apiLicense,
                url + getApiLicenseUri,
                Collections.emptyList());
        return info;
    }

}


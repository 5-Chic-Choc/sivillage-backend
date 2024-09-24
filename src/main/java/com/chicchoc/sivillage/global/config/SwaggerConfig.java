package com.chicchoc.sivillage.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {

        Info info = new Info()
                .title("SiVillage API")
                .version("0.01")
                .description("SiVillage API")
                .termsOfService("http://swagger.io/terms/");

        // Security Secheme명
        String jwtSchemeName = "jwtAuth";

        // API
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        // SecuritySchemes 설정
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER) //
                        .name("Authorization"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    @Bean
    public GroupedOpenApi AllApi() {
        return GroupedOpenApi.builder()
                .group("All")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi MemberApi() {
        return GroupedOpenApi.builder()
                .group("Auth-인증")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi BatchApi() {
        return GroupedOpenApi.builder()
                .group("batch-배치")
                .pathsToMatch("/batch/**")
                .build();
    }

    @Bean
    public GroupedOpenApi TermsApi() {
        return GroupedOpenApi.builder()
                .group("terms-약관")
                .pathsToMatch("/api/v1/terms/**")
                .build();
    }

    @Bean GroupedOpenApi PromotionApi() {
        return GroupedOpenApi.builder()
                .group("promotion-프로모션")
                .pathsToMatch("/api/v1/promotion/**", "/api/promotion/like/**")
                .build();
    }

    @Bean GroupedOpenApi OauthApi() {
        return GroupedOpenApi.builder()
                .group("Oauth-인증")
                .pathsToMatch("/api/v1/oauth/**")
                .build();
    }

    @Bean GroupedOpenApi ProductsApi() {
        return GroupedOpenApi.builder()
                .group("Product-상품")
                .pathsToMatch("/api/v1/products/**", "/api/v1/product/like/**")
                .build();
    }

    @Bean GroupedOpenApi DataApi() {
        return GroupedOpenApi.builder()
                .group("Data-데이터 삽입")
                .pathsToMatch("/data/")
                .build();
    }

    @Bean GroupedOpenApi DeliveryApi(){
        return GroupedOpenApi.builder()
                .group("DeliveryTemplate-배송지")
                .pathsToMatch("/api/v1/deliveryTemplate/**")
                .build();
    }

    @Bean GroupedOpenApi CartApi(){
        return GroupedOpenApi.builder()
                .group("Cart-장바구니")
                .pathsToMatch("/api/v1/cart/**")
                .build();
    }

    @Bean GroupedOpenApi BrandApi(){
        return GroupedOpenApi.builder()
                .group("Brand-브랜드")
                .pathsToMatch("/api/v1/brand/**")
                .build();
    }

    @Bean GroupedOpenApi S3Api(){
        return GroupedOpenApi.builder()
                .group("S3-이미지업로드")
                .pathsToMatch("/api/v1/s3/**")
                .build();
    }

    @Bean GroupedOpenApi ReviewApi(){
        return GroupedOpenApi.builder()
                .group("Review-리뷰")
                .pathsToMatch("/api/v1/review/**")
                .build();
    }

    @Bean GroupedOpenApi OrderApi(){
        return GroupedOpenApi.builder()
                .group("Order-주문")
                .pathsToMatch("/api/v1/order/**")
                .build();
    }

    @Bean GroupedOpenApi UnsignedMemberApi(){
        return GroupedOpenApi.builder()
                .group("UnsignedMember-비회원")
                .pathsToMatch("/api/v1/unsignedMember/**")
                .build();
    }

    @Bean GroupedOpenApi SizeApi(){
        return GroupedOpenApi.builder()
                .group("Size-사이즈")
                .pathsToMatch("/api/v1/sizes/**")
                .build();
    }

    @Bean GroupedOpenApi EtcOptionApi(){
        return GroupedOpenApi.builder()
                .group("EtcOption-기타옵션")
                .pathsToMatch("/api/v1/etcoptions/**")
                .build();
    }

    @Bean GroupedOpenApi ColorsApi(){
        return GroupedOpenApi.builder()
                .group("Colors-색깔")
                .pathsToMatch("/api/v1/colors/**")
                .build();
    }

    @Bean GroupedOpenApi CategoryApi(){
        return GroupedOpenApi.builder()
                .group("Category-카테고리")
                .pathsToMatch("/api/v1/category/**")
                .build();
    }

    @Bean GroupedOpenApi MediaApi(){
        return GroupedOpenApi.builder()
                .group("Media-미디어")
                .pathsToMatch("/api/v1/media/**", "/api/v1/product-media/**")
                .build();
    }
}



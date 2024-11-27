package com.coinwork.base.acommon.config;

import com.coinwork.base.acommon.security.ApiTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 리소스 설정. yml 파일내에서 설정.
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

//    /**
//     * Thymeleaf template 경로설정.
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(final ResourceHandlerRegistry registry ){
//    registry.addResourceHandler("/**")
//            .addResourceLocations("classpath:/templates/", "classpath:/static/")
//            .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
////                .allowedOrigins("http://localhost:8080")
//                .allowedOriginPatterns("*")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(3600)
//                ;
//    }

    @Bean
    public FilterRegistrationBean registerCorsFilter() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new CustomCORSFilter());
        filterRegistrationBean.setName("customCORSFilter");
        filterRegistrationBean.setOrder(0);

        return filterRegistrationBean;
    }

    // apiTokenFilter 는 Servlet 필터에서 거치치 않도록 disable 처리.
    // apiTokenFilter 를 Bean으로 등록시에는 spring은 기본으로 servlet Filter에 등록처리 함.
    // 이후 springSecurityFilter + servlet Filter 등 중복으로 처리 되어 이중처리됨.
    // Security web.ignoring() URI는 ApiTokenFilter를 거치지 않도록 처리.
    @Bean
    public FilterRegistrationBean<ApiTokenFilter> registration(ApiTokenFilter filter) {
        FilterRegistrationBean<ApiTokenFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);    // servlet 필터에서는 disable 해주어야...그냥 security는 Bypass
        return registration;
    }
}

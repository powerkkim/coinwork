package com.coinwork.base.acommon.config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.coinwork.base.acommon.security.ApiTokenFilter;

/**
 * 리소스 설정. yml 파일내에서 설정.
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
 
   @Override
   public void addResourceHandlers(final ResourceHandlerRegistry registry ){
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                        @Override
                        protected Resource getResource(String resourcePath, Resource location) throws IOException {
                            Resource requestedResource = location.createRelative(resourcePath);
                            return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : new ClassPathResource("/static/index.html");
                        }
                    })
                ;
   }

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

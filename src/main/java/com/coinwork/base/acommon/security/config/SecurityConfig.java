package com.coinwork.base.acommon.security.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.util.AntPathMatcher;

import com.coinwork.base.acommon.security.ApiTokenFilter;
import com.coinwork.base.acommon.security.handler.CustomAccessDeniedHandler;
import com.coinwork.base.acommon.security.handler.CustomAuthenticationEntryPoint;
import com.coinwork.base.acommon.security.service.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity  // EnableWebSecurity 를 입력하는 순간 기존 Spring의 설정을 무시하고 내가 설정하겠다고 지정하는 것임.
public class SecurityConfig  {

    private UserDetailsServiceImpl userDetailsService;
    private ApiTokenFilter apiTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, ApiTokenFilter apiTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.apiTokenFilter = apiTokenFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager( ) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService( userDetailsService );

        return new ProviderManager(authenticationProvider);
    }

    // 기본적으로 무조건 허용이 필요한 목록.
    private static final String[] DEFAULT_LIST = {
         "/auth/signUp", "/auth/token", "/error"
    };

    // 추가적으로 허용이 되는 url
    // apiTokenFilter 는 통과하되. 인증검사는 하지않아도 Controller로 허용됨. 
    private static final String[] WHITE_LIST = {
           "/board/**"
    };

    // API 요청으로 간주할 URL 패턴 정의
    private static final String[] API_PATTERNS = {
        "/api/**",     // API 요청 prefix
        "/v1/**",      // API 버전 prefix
        "/rest/**"     // REST 요청 prefix
    };

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CustomAuthenticationEntryPoint entryPoint;


    /**
     * apiTokenFilter 필터 조차 건너뛴다.
     * @return
     */
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers( "/*", 
                    "/assets/**",           // 정적 리소스
                    "/css/**", 
                    "/js/**", 
                    "/images/**",
                    "/favicon.ico",
                    "/hello" 
                )
                // .requestMatchers(request -> !isApiRequest(request.getRequestURI())) 
                ;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf( c-> c.disable() )
                .cors( c -> c.disable() )
                .headers(h -> h.frameOptions(f -> f.disable()).disable())
                .httpBasic(h -> h.disable())   // 로그인 인증창.
                .sessionManagement( s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authorizeHttpRequests(authorize ->
                                        authorize
                                                .requestMatchers(DEFAULT_LIST).permitAll()
                                                .requestMatchers(WHITE_LIST).permitAll()
                                                .requestMatchers("/hello2").hasRole("ADMIN")
                                                .requestMatchers("/hello3").hasRole("USER") 
                                                .requestMatchers(request -> !isApiRequest(request.getRequestURI())).permitAll()
                                                .anyRequest().authenticated()
                )
                .addFilterBefore(apiTokenFilter, AuthorizationFilter.class)
                .authenticationManager( authenticationManager() )
                .exceptionHandling(c -> c.authenticationEntryPoint(entryPoint).accessDeniedHandler(accessDeniedHandler))
        ;

        return http.build();
    }

    private boolean isApiRequest(String requestURI) {
        return Arrays.stream(API_PATTERNS)
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, requestURI));
    }
}

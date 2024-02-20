package io.hysena.todoapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hysena.todoapp.impl.UserDetailsServiceImpl;
import io.hysena.todoapp.jwt.JwtAuthorizationFilter;
import io.hysena.todoapp.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService, objectMapper);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // CSRF 설정
        httpSecurity.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식이 아닌 JWT 를 사용하기 위한 설정
        httpSecurity.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        httpSecurity.authorizeHttpRequests((authorizeHttpRequest) ->
                authorizeHttpRequest
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용
                        .requestMatchers("/users/**").permitAll() // "api/v1/users" 로 시작하는 모든 uri 허용
                        .anyRequest().authenticated() // 이 외에 나머지는 인증 처리 필요
        );

        // <<필터 관리>>
        // 인증 : 현재 로그인은 UserController에서 처리
        // 인가 : 필터를 이용해서 토큰을 통한 인가 처리
        httpSecurity.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}

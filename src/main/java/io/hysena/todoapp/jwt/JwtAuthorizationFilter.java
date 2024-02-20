package io.hysena.todoapp.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hysena.todoapp.dto.CommonResponseDto;
import io.hysena.todoapp.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request); // 토큰 추출

        if(Objects.nonNull(token)){
            if(jwtUtil.validateToken(token)){
                Claims info = jwtUtil.getUserInfoFromToken(token);
                // <<인증정보에 유저정보 넣기>>
                // 1. 빈 SecurityContext 생성
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                // 2. username 가져옴
                String username = info.getSubject(); //createToken에서 username 넣음
                // 3. userRepository에서 해당 username을 가진 user를 찾아서 userDetails로 받아옴
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 4. 인증객체 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // 5. SecurityContext 에  넣기
                context.setAuthentication(authentication);
                // 6. SecurityContextHolder에 넣기
                SecurityContextHolder.setContext(context);
                //이제 @AuthenticationPrincipal 로 조회가능
            }else{
                // 인증정보가 유효하지 않은 경우
                CommonResponseDto<Void> commonResponseDto = new CommonResponseDto<>("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value(),null);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}

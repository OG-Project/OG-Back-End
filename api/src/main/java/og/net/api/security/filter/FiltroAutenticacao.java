package og.net.api.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import og.net.api.security.HttpRequestConfig.CustomHttpServletRequestWrapper;
import og.net.api.security.utils.CookieUtil;
import og.net.api.security.utils.JwtUtil;
import og.net.api.service.AutenticacaoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Component
@AllArgsConstructor
public class FiltroAutenticacao extends OncePerRequestFilter {
    private SecurityContextRepository securityContextRepository;
    private final CookieUtil cookieUtil = new CookieUtil();
    private final JwtUtil jwtUtil = new JwtUtil();
    private final AutenticacaoService autenticacaoService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest requestCopy = request;

        if (!rotaPublica(request)) {
            Cookie cookie = cookieUtil.getCookie(request, "JWT");
            String token = cookie.getValue();
            String username = jwtUtil.getUsername(token);
            UserDetails user = autenticacaoService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user, user.getPassword(), user.getAuthorities()
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);
        }

        if(Objects.equals(requestCopy.getRequestURI(), "/equipe") && requestCopy.getMethod().equals("PUT")){
            CustomHttpServletRequestWrapper customHttpServletRequestWrapper = new CustomHttpServletRequestWrapper(request);
            filterChain.doFilter(customHttpServletRequestWrapper, response);
        }else{
            filterChain.doFilter(requestCopy, response);
        }
    }

    private boolean rotaPublica(HttpServletRequest request){
        return ((request.getRequestURI().equals("/login") &&
                request.getMethod().equals("POST") || (request.getRequestURI().equals("/usuario") &&
                request.getMethod().equals("POST"))|| (request.getRequestURI().equals("/"))));
    };
}

package com.ada.pedidocompra.infraestrutura.configuracao.seguranca.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        var token = JWTService.getToken(request);

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        var email = JWTService.getEmail(token);
        var roles = JWTService.getRoles(token);

        var userAuthToken = new UsernamePasswordAuthenticationToken(
                email,
                null,
                List.of(new SimpleGrantedAuthority(roles.get(0)))
        );

        SecurityContextHolder.getContext().setAuthentication(userAuthToken);

        chain.doFilter(request, response);
    }

}

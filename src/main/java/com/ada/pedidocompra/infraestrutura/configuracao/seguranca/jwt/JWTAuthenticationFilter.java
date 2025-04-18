package com.ada.pedidocompra.infraestrutura.configuracao.seguranca.jwt;

import com.ada.pedidocompra.infraestrutura.configuracao.seguranca.LoginResponse;
import com.ada.pedidocompra.infraestrutura.configuracao.seguranca.UserContext;
import com.ada.pedidocompra.infraestrutura.configuracao.seguranca.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            var loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            var userAuthentication = new UsernamePasswordAuthenticationToken(
                    loginRequest.email(),
                    loginRequest.senha()
            );

            return authenticationManager.authenticate(userAuthentication);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        var userContext = (UserContext) authResult.getPrincipal();

        var email = userContext.getUsername();
        var perfil = userContext.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();

        var token = JWTService.createToken(email, perfil);
        var loginResponse = new LoginResponse(token);

        response.getWriter().write(new ObjectMapper().writeValueAsString(loginResponse));
        response.getWriter().flush();

    }
}

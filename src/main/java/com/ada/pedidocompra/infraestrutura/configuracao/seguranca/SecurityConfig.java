package com.ada.pedidocompra.infraestrutura.configuracao.seguranca;

import com.ada.pedidocompra.infraestrutura.configuracao.seguranca.jwt.JWTAuthenticationFilter;
import com.ada.pedidocompra.infraestrutura.configuracao.seguranca.jwt.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity // Habilita a configuração de segurança, para as minhas customizações
@EnableMethodSecurity // Habilita a configuração de segurança em métodos, como @PreAuthorize, @Secured, etc.
public class SecurityConfig {

    private static final RequestMatcher[] WHITELIST = {
            AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
            AntPathRequestMatcher.antMatcher("/swagger-ui.html"),
            AntPathRequestMatcher.antMatcher("/v3/**"),
            AntPathRequestMatcher.antMatcher("/h2-console/**")
    };
    private static final RequestMatcher[] PUBLIC = {
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/clientes"),
    };

    final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        var authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        var authManager = authManagerBuilder.build();

        http.csrf(AbstractHttpConfigurer::disable) // Desabilita o CSRF (Cross-Site Request Forgery) Para JWT não é necessário
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(WHITELIST).permitAll() // Endpoints públicos como Swagger e H2
                        .requestMatchers(PUBLIC).permitAll() // Endpoints públicos /clientes apenas para POST
                        .anyRequest().authenticated()) // Qualquer outra requisição deve ser autenticada
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // Permite o acesso ao H2 console
                .addFilter(new JWTAuthenticationFilter(authManager)) // Filtro de autenticação JWT
                .addFilter(new JWTAuthorizationFilter(authManager)) // Filtro de autorização JWT
                .authenticationManager(authManager) // Classe de autenticação do Spring Security
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Define o gerenciamento de sessão como sem estado (stateless)

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**") // Permite acesso a todos os endpoints
                        .allowedOrigins("*") // Permite requisições de qualquer origem
                        .allowedMethods("*"); // Permite todos os métodos HTTP (GET, POST, PUT, DELETE, etc.)
            }
        };
    }

    /*
        CORS (Cross-Origin Resource Sharing) é um mecanismo de segurança implementado por navegadores que controla
        como as páginas web de um domínio (origem) podem interagir com recursos de outro domínio.
        Isso é essencial quando você faz chamadas de API de um frontend (por exemplo, no localhost:4200)
        para um backend (no localhost:8080), e ambos são hospedados em origens diferentes.

        Postman não tem essa limitação, pois não é um navegador. Ele pode fazer requisições para qualquer origem
        E as respostas não são afetadas por CORS. Portanto, você pode testar suas APIs no Postman sem se preocupar
        com CORS. No entanto, quando você tenta acessar a mesma API de um navegador, o CORS entra em cena.
    */

    /*
        CSRF (Cross-Site Request Forgery) é um tipo de ataque onde um site malicioso tenta fazer uma
        requisição em nome de um usuário autenticado em outro site. Isso pode acontecer quando um usuário
        está autenticado em um site e, simultaneamente, visita um site malicioso que tenta fazer uma
        requisição para o site autenticado, usando as credenciais do usuário.

        Em Spring Security, o CSRF é habilitado por padrão para proteger contra esse tipo de ataque.
        No entanto, quando você está usando autenticação baseada em token (como JWT), o CSRF pode ser
        desabilitado, pois o token JWT é enviado explicitamente pelo cliente, não sendo vulnerável ao tipo
        de ataque que o CSRF previne.

     */

    /*
        SAMEORIGIN é uma política de segurança que permite que o conteúdo seja carregado apenas
        de origens que são consideradas seguras. Isso significa que o conteúdo só pode ser carregado
        de páginas que estão no mesmo domínio ou subdomínio. Isso ajuda a prevenir ataques de
        clickjacking, onde um site malicioso tenta enganar o usuário para que ele clique em algo
        diferente do que ele pensa que está clicando, potencialmente comprometendo a segurança
        do usuário.

        Por padrão, o Spring Security aplica a política de SAMEORIGIN para proteger contra-ataques de clickjacking.
        Para que o H2 console funcione corretamente, é necessário desabilitar essa proteção.

     */

}

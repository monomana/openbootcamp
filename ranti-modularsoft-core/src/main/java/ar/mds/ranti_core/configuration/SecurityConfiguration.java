package ar.mds.ranti_core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfiguration {

    private final JwtService jwtService;

    @Autowired
    public SecurityConfiguration(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .logout().disable()
                .addFilterAt(bearerAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
//
//        http
//                .cors()
//                .and()
//               // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//               // .and()
//                .csrf().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
//                .and()
//                .authorizeRequests()
//                .antMatchers("/", "/error", "/api/all", "/api/auth/**", "/oauth2/**","/company","/category").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .oauth2Login()
//                .authorizationEndpoint()
//                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
//                .and()
//                .redirectionEndpoint()
//                .and()
//                .userInfoEndpoint()
//                .oidcUserService(customOidcUserService)
//                .userService(customOAuth2UserService)
//                .and()
//                .tokenEndpoint()
//                .accessTokenResponseClient(authorizationCodeTokenResponseClient())
//                .and()
//                .successHandler(oAuth2AuthenticationSuccessHandler)
//                .failureHandler(oAuth2AuthenticationFailureHandler);

    }

    private AuthenticationWebFilter bearerAuthenticationFilter() {
        AuthenticationWebFilter bearerAuthenticationFilter =
                new AuthenticationWebFilter(new JwtAuthenticationManager(jwtService));
        bearerAuthenticationFilter.setServerAuthenticationConverter(serverWebExchange -> {
            String token = jwtService.extractBearerToken(
                    serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
            return Mono.just(new UsernamePasswordAuthenticationToken(token, token));
        });
        return bearerAuthenticationFilter;
    }


}

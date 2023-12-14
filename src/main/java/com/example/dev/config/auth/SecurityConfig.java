package com.example.dev.config.auth;

import com.example.dev.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Bean
  @Order(SecurityProperties.BASIC_AUTH_ORDER)
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf((csrfConfig) ->
            csrfConfig.disable()
        )
        .headers((headerConfig) ->
            headerConfig.frameOptions(frameOptionsConfig ->
                frameOptionsConfig.disable()
            )
        )
        .authorizeHttpRequests((authorizeRequests) ->
            authorizeRequests
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .requestMatchers("/posts/**", "/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
        )
/*        .formLogin((formLogin) ->
            formLogin
                .loginPage("/login/")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login/login-proc")
                .defaultSuccessUrl("/", true)
        )*/
        .logout((logoutConfig) ->
            logoutConfig.logoutSuccessUrl("/")
        )
        .oauth2Login(oauth2 -> oauth2
            /*.loginPage("/login")*/
            .userInfoEndpoint(userInfo -> userInfo
                .userService(customOAuth2UserService)
            )
        );
    return http.build();
  }
}

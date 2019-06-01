package com.internhub.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImplementation userDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    private static final String[] PRIVATE_ROUTES = new String[] {
            "/api/applications/**", "/api/suggestions/**",
            "/api/auth/me", "/api/auth/password/change"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final JWTAuthenticationFilter authenticationFilter = new JWTAuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl(JWTConstants.LOGIN_URL);
        final JWTAuthorizationFilter authorizationFilter = new JWTAuthorizationFilter(authenticationManager(), userRepository);
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, PRIVATE_ROUTES).permitAll()
                .antMatchers(PRIVATE_ROUTES).authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilter(authenticationFilter)
                .addFilter(authorizationFilter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
    }
}

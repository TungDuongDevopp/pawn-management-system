package com.tungduong.pawnmanagementsystem.config;

import com.tungduong.pawnmanagementsystem.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;


@Configuration
public class SecurityConfig {
    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(AccountService service){
        return  new CustomerUserDetailService(service);
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider(userDetailsService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;

    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http){
        http.authorizeHttpRequests((requests) ->
                requests.requestMatchers("/login","/register","/").permitAll()
                        .requestMatchers("/accounts/**","/dashboards/**","/customers/**","/staffs/**","/categories/**","/collaterals/**").hasRole("ADMIN")
                        .anyRequest().authenticated());
        http.formLogin(login-> login.loginPage("/login").failureUrl("/login?error")
                .successHandler(customSuccessHandler)
        );
        http.exceptionHandling(exception -> exception
                .accessDeniedPage("/403")
        );
        http.sessionManagement(s->s.maximumSessions(1).maxSessionsPreventsLogin(false).expiredUrl("/login?expire"));
        http.rememberMe(r->r.rememberMeServices(rememberMeServices()));
        return http.build();
    }
    @Bean
    SpringSessionRememberMeServices rememberMeServices(){
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(false);
        rememberMeServices.setValiditySeconds(7*24*60*60);
        return rememberMeServices;
    }

}

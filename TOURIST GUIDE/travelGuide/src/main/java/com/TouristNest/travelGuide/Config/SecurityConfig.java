package com.TouristNestApplication.TravelGuide.Config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    @Bean
    public UserDetailsService getDetailsService(){
        return new CustomUserDetailsService();
    }


    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity httpSecurity)throws Exception{
        httpSecurity
                .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/touristNest/berlin","/css/**","/Berlin/**").permitAll()
                    .requestMatchers("/touristNest/**","/css/**","/image/**").permitAll()
                    .requestMatchers("/touristNest/signup/**").permitAll()
                    .requestMatchers("/hotel/**", "/css/**","/Berlin/**").permitAll()
                    .requestMatchers("/SpittelmarktBerlin/**").permitAll()
                .requestMatchers("/otherEndpoint").permitAll()
                .requestMatchers("/user/**").authenticated()
                    .and()
                .formLogin()
                    .loginPage("/signin")
                    .loginProcessingUrl("/login")
                    .usernameParameter("email")
                .defaultSuccessUrl("/user/profile").permitAll()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID");
        return  httpSecurity.build();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler successHandler = new SimpleUrlLogoutSuccessHandler();
        successHandler.setUseReferer(true); // Redirect to the referer page after logout
        return successHandler;
    }

}

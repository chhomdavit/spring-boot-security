package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

         httpSecurity

                 .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                         .requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll()
                         .requestMatchers(AntPathRequestMatcher.antMatcher("/saveUser")).permitAll()
                         .requestMatchers(AntPathRequestMatcher.antMatcher("/register")).permitAll()
                         .requestMatchers(AntPathRequestMatcher.antMatcher("/user/**")).authenticated()
                         .anyRequest().authenticated()
                 )
              
                 .formLogin(form -> form
                		 .loginPage("/signin")
                 	     .loginProcessingUrl("/userLogin")
                 	     .defaultSuccessUrl("/user/profile")
                 	     .permitAll()  
                 	     
                 );
         
         httpSecurity.logout((logout) ->
         logout.deleteCookies("remove")
                .invalidateHttpSession(true) // Explicitly invalidate the session
                .logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/userlogout").permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Add this line
       );
  
    
        return httpSecurity.build();
    }


		
}

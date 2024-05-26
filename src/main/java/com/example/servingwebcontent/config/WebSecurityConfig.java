package com.example.servingwebcontent.config;




import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.servingwebcontent.service.UserService;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig   {
	
	
	

	
	@Autowired
	private UserService userService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity  http) throws Exception{
		http.csrf().disable()
			.authorizeHttpRequests((requests)->requests
					.requestMatchers("/","/registration").permitAll()
					.anyRequest().authenticated()		
					)
					.formLogin((form)->form
							.loginPage("/login")
							.permitAll()
							)
					 	.logout(LogoutConfigurer::permitAll);
						
		return http.build();
	}
	
	@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authenticationProvider;
    }
	
	
	
	
	
	
	
	
	
	
	
	 /* @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	        AuthenticationManagerBuilder auth = 
	                http.getSharedObject(AuthenticationManagerBuilder.class);
	        auth.jdbcAuthentication()
	            .dataSource(dataSource)
	            .passwordEncoder(NoOpPasswordEncoder.getInstance())
	            .usersByUsernameQuery("select username,password,active from usr where username=?")
	            .authoritiesByUsernameQuery("select u.username,ur.roles from usr u inner join user_role ur on u.id=ur.id_user where u.username=?");
	        return auth.build();
	    }*/
   
	
	
	
}

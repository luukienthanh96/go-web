/**
 * 
 */
package com.goweb.webapp.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.goweb.webapp.core.service.imp.UserDetailsServiceImpl;

/**
 * @author Kraken
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}

	// Register HttpSessionEventPublisher
	@Bean
	public static ServletListenerRegistrationBean httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Setting Service to find User in the database.
		// And Setting PassswordEncoder
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		/*auth.jdbcAuthentication()
		.usersByUsernameQuery("")
		.authoritiesByUsernameQuery("")
		.dataSource(dataSource);*/

	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/css/**")
		.antMatchers("/fonts/**")
		.antMatchers("/images/**")
		.antMatchers("/js/**")
		.antMatchers("/vendor/**")
		.antMatchers("/templates/**")
		.antMatchers("/resources/**")
		.antMatchers("/i18n/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		// The pages does not require login
		http.authorizeRequests().antMatchers("/login", "/logout").permitAll();
		
		// For ADMIN only.
		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
		
		// /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
		// If no login, it will redirect to /login page.
		http.authorizeRequests().antMatchers("/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
		
		// AccessDeniedException will be thrown.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		// Config for Login Form
		http.authorizeRequests().and().formLogin()//
				// Submit URL of login page.
				.loginProcessingUrl("/j_spring_security_check")		// Submit URL
				.loginPage("/login")								// Page
				.defaultSuccessUrl("/home")							// Login Success
				.failureUrl("/login?error=true")					// Login Fail
				
				// Param login
				.usernameParameter("username")//
				.passwordParameter("password")
				
				// Config for Logout Page
				.and().logout()
				.logoutSuccessUrl("/login")
				.logoutUrl("/logout")
				.invalidateHttpSession(true).deleteCookies("JSESSIONID");
		
		//Only one session login at time
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry());
		
	}
}

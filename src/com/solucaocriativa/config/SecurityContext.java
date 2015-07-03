package com.solucaocriativa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

import com.solucaocriativa.filter.CustomAuthenticationProvider;
import com.solucaocriativa.filter.SecurityLoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    
    @Autowired
    private SecurityLoginSuccessHandler securityLoginSuccessHandler;

    public SecurityContext() {
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	
	ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();
	 http = urlRegistry.and();
	    
	 http = http.authorizeRequests()
        	.antMatchers("/resources/**").permitAll()       
        	.antMatchers("/index.xhtml").permitAll()
        	.antMatchers("/erroLogin.xhtml").permitAll()
        	.antMatchers("/logout").permitAll()
        	.antMatchers("/paginas/**").access("isAuthenticated()")                           
	.and().formLogin()
        	.loginPage("/index.xhtml")
        	.usernameParameter("j_username")
        	.passwordParameter("j_password")
        	.successHandler(securityLoginSuccessHandler)
        	.failureUrl("/erroLogin.xhtml")
    	.and().formLogin()
        	.loginPage("/erroLogin.xhtml")
        	.usernameParameter("j_username")
        	.passwordParameter("j_password")
        	.successHandler(securityLoginSuccessHandler)
        	.failureUrl("/erroLogin.xhtml")
        .and().logout().logoutSuccessUrl("/index.xhtml")
	.and().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
	    throws Exception {
	auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
	web.ignoring().antMatchers("/resources/**");	
    }
}
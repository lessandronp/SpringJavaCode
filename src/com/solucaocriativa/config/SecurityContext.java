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
import com.solucaocriativa.util.Constants;

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
	
//	List<UsuarioDepartamento> gruposUsuarios = servicoGrupoUsuario.buscaTodos();
	ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry urlRegistry = http.authorizeRequests();
//	    for (UsuarioDepartamento grupoUsuario : gruposUsuarios) {
//	      if (grupoUsuario.getPermissoesGrupo().size() > 0) {
//	        for (PermissaoGrupo permissaoGrupo : grupoUsuario.getPermissoesGrupo()) {
//	          urlRegistry.antMatchers( permissaoGrupo.getTela().getUrl())
//	          	.access(HAS_ROLE.concat(preparaRegra(grupoUsuario.getAuthority())).concat(HAS_ROLE_CLOSE));
//	        }
//	      }
//	    }
	 http = urlRegistry.and();
	    
	 http = http.authorizeRequests()
        	.antMatchers("/resources/**").permitAll()       
        	.antMatchers("/index.xhtml").permitAll()
        	.antMatchers("/erroLogin.xhtml").permitAll()
//        	.antMatchers("/**").access("isAuthenticated()")                           
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
    
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
    
    private String preparaRegra(String grantedAuthority) {
	return Constants.ROLE_PREFFIX.concat(grantedAuthority.replaceAll(Constants.REGEX_ASC, "").toUpperCase());
    }
}
package com.solucaocriativa.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.solucaocriativa.jsf.FlashScope;
import com.solucaocriativa.jsf.ViewScope;

@Configuration
@ComponentScan(basePackages = "com.solucaocriativa")
public class SpringConfig {
    
    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
	CustomScopeConfigurer configurer = new CustomScopeConfigurer();
	Map<String, Object> scopes = new HashMap<String, Object>();
	scopes.put("view", new ViewScope());
	scopes.put("flash", new FlashScope());
	configurer.setScopes(scopes);
	return configurer;
    }
}

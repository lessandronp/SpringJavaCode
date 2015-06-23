package com.solucaocriativa.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.solucaocriativa.filter.AuthenticationManagerImpl;
import com.solucaocriativa.jsf.FlashScope;
import com.solucaocriativa.jsf.ViewScope;

@Configuration
@ComponentScan(basePackages = { "com.solucaocriativa" })
@EnableTransactionManagement(proxyTargetClass = true)
public class SpringConfig {

    @Bean
    public AuthenticationManager authenticationManager() {
	return new AuthenticationManagerImpl();
    }

    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean 
        	= new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaDialect(new EclipseLinkJpaDialect());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setDataSource(dataSource());
//        entityManagerFactoryBean.setPersistenceUnitName("solucaoCriativaPU");
//        entityManagerFactoryBean.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        entityManagerFactoryBean.setPackagesToScan("com.solucaocriativa.entidade");
//        entityManagerFactoryBean.setJpaProperties(eclipseLinkProperty());
        entityManagerFactoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());        
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();
    }
    
    @Bean(name = "jpaVendorAdapter")
    public JpaVendorAdapter jpaVendorAdapter() {
	EclipseLinkJpaVendorAdapter eclipseLinkJpaVendorAdapter = new EclipseLinkJpaVendorAdapter();
	eclipseLinkJpaVendorAdapter.setGenerateDdl(Boolean.TRUE);
	eclipseLinkJpaVendorAdapter.setDatabasePlatform("org.eclipse.persistence.platform.database.PostgreSQLPlatform");
	eclipseLinkJpaVendorAdapter.setShowSql(Boolean.TRUE);
	return eclipseLinkJpaVendorAdapter;
    }
    
    @Bean
    public DataSource dataSource() {
	BasicDataSource dataSource = new BasicDataSource();
	dataSource.setDriverClassName("org.postgresql.Driver");
	dataSource.setUrl("jdbc:postgresql://localhost:5432/solucaocriativa");
	dataSource.setUsername("postgres");
	dataSource.setPassword("admin");
	return dataSource;
    }
    
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(){
	JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
	jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
	return jpaTransactionManager;
    }
    
    private Properties eclipseLinkProperty(){
        Properties properties = new Properties();       
//        properties.put("eclipselink.deploy-on-startup", "true");
//        properties.put("eclipselink.ddl-generation", "create-or-extend-tables");
        properties.put("eclipselink.ddl-generation.output-mode", "database");
        properties.put("eclipselink.weaving", "static");
        properties.put("eclipselink.weaving.lazy", "true");
//        properties.put("eclipselink.weaving.internal", "true");
//        properties.put("eclipselink.logging.level", "SEVERE");
//        properties.put("eclipselink.query-results-cache.type", "WEAK");
//        properties.put("eclipselink.jdbc.batch-writing", "JDBC");
//        properties.put("eclipselink.jdbc.batch-writing.size", "1000");
        return properties;
    }
    
    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
	CustomScopeConfigurer configurer = new CustomScopeConfigurer();
	Map<String, Object> scopes = new HashMap<String, Object>();
	scopes.put("view", new ViewScope());
	scopes.put("flash", new FlashScope());
	configurer.setScopes(scopes);
	return configurer;
    }
    
    @Bean
    public BeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }
    
}
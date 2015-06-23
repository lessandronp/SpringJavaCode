package com.solucaocriativa.servlet;

import java.util.EnumSet;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.util.Log4jConfigListener;

import com.solucaocriativa.spring.SpringConfig;

public class WebAppInitializer implements WebApplicationInitializer {
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

	AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SpringConfig.class);
        ctx.setServletContext(servletContext);
        
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

	servletContext.addFilter("characterEncoding",
		characterEncodingFilter)
			.addMappingForUrlPatterns(dispatcherTypes, true, "/*");        

	servletContext.addFilter("springSecurityFilterChain",
		new DelegatingFilterProxy("springSecurityFilterChain"))
			.addMappingForUrlPatterns(dispatcherTypes, false, "/*");        

	servletContext.addFilter("sessionInView",
	        new OpenEntityManagerInViewFilter())
	        	.addMappingForUrlPatterns(null, true, "/*");
	
	servletContext.addFilter("PrimeFaces FileUpload Filter",
		new FileUploadFilter());        

        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
        servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
        servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING", "true");
        servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "client");
        servletContext.setInitParameter("javax.faces.WEBAPP_RESOURCES_DIRECTORY", "/resources");
        servletContext.setInitParameter("log4jConfigLocation", "classpath:/META-INF/log4j.properties");
        servletContext.setInitParameter("org.ajax4jsf.handleViewExpiredOnClient", "true");
        servletContext.setInitParameter("primefaces.THEME", "start");
        servletContext.setInitParameter("org.jboss.jbossfaces.WAR_BUNDLES_JSF_IMPL", "true");
        
        ServletRegistration.Dynamic facesServlet = servletContext.addServlet("Faces Servlet", FacesServlet.class);
        facesServlet.setLoadOnStartup(1);
        facesServlet.addMapping("*.xhtml");
        
	servletContext.addListener(new ContextLoaderListener(ctx));   
        servletContext.addListener(new RequestContextListener());
        servletContext.addListener(new Log4jConfigListener()); 
//        servletContext.addListener(ConfigureListener.class);
        
    }        
        
}
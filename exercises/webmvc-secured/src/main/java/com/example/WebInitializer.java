package com.example;

import com.example.config.SecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Arrays;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        rootContext.getEnvironment().addActiveProfile("jdbc");

        System.out.println("rootContext = " + Arrays.asList(rootContext.getEnvironment().getActiveProfiles()));

        rootContext.register(AppConfiguration.class, DispatcherConfig.class);
        rootContext.register(WebSecurityConfiguration.class);
        rootContext.scan(SecurityConfiguration.class.getPackage().getName());

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        //Register secrity filter
        FilterRegistration.Dynamic springSecurityFilterChain =
                container.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/*");

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher =
                container.addServlet("dispatcher", new DispatcherServlet(new GenericWebApplicationContext()));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}

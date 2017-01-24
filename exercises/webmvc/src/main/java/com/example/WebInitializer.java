package com.example;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        rootContext.getEnvironment().setActiveProfiles("jpa");
        rootContext.register(AppConfiguration.class, DispatcherConfig.class);

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher =
                container.addServlet("dispatcher", new DispatcherServlet(new GenericWebApplicationContext()));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}

package org.nigajuan.controller;


import org.codehaus.groovy.grails.web.context.GrailsContextLoaderListener;
import org.codehaus.groovy.grails.web.pages.GroovyPageResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContextEvent;

public class MyContextLoader extends GrailsContextLoaderListener {

    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        WebApplicationContext context = getCurrentWebApplicationContext();
        GroovyPageResourceLoader groovyPageResourceLoader = (GroovyPageResourceLoader) context.getBean(GroovyPageResourceLoader.BEAN_ID);
        groovyPageResourceLoader.setBaseResource(new FileSystemResource(event.getServletContext().getRealPath(".") + "\\"));
    }


}

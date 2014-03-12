package org.nigajuan.controller

import org.codehaus.groovy.grails.web.pages.GroovyPageCompiler
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.core.io.FileSystemResource
import org.springframework.web.context.ServletContextAware

import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine;
import org.codehaus.groovy.grails.web.servlet.view.GroovyPageView;
import org.nigajuan.domain.Account;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/gsp")
public class GspController implements ApplicationContextAware, ServletContextAware {

    ApplicationContext applicationContext;
    ServletContext servletContext

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String index(HttpServletRequest request , HttpServletResponse response) {
        /*GrailsWebRequest grailsWebRequest = new GrailsWebRequest(request , response , servletContext)


        FileSystemResource resourceText = new FileSystemResource("D:\\developpement\\testcontroller\\grails-app\\views\\foo\\toto.gsp")

        ResourceScriptSource groovyPageResourceScriptSource = new ResourceScriptSource(resourceText)

        GroovyPagesTemplateEngine groovyPagesTemplateEngine = new GroovyPagesTemplateEngine()
        groovyPagesTemplateEngine.setApplicationContext(applicationContext)
        groovyPagesTemplateEngine.setServletContext(servletContext)
        groovyPagesTemplateEngine.afterPropertiesSet()


        MyPageView gspSpringView = new MyPageView();
        gspSpringView.setServletContext(servletContext);
        gspSpringView.setUrl(resourceText.getURL().toString());
        gspSpringView.setApplicationContext(applicationContext);
        gspSpringView.setTemplateEngine(groovyPagesTemplateEngine);
        gspSpringView.setScriptSource(groovyPageResourceScriptSource);
        gspSpringView.afterPropertiesSet();

        StringWriter stringWriter = new StringWriter()

        gspSpringView.render([foo:["d","b"]] , request , response)*/

        def compiler = new GroovyPageCompiler()
        compiler.srcFiles.add(new File("D:\\developpement\\testcontroller\\grails-app\\views\\foo\\toto.gsp"))
        compiler.setTargetDir(new File("D:\\developpement\\SpringMVC-Grails\\target"))


        compiler.compile()



        return stringWriter.toString()
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext
    }
}

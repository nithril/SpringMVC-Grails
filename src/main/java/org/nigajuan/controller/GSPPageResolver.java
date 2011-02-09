package org.nigajuan.controller;

import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine;
import org.codehaus.groovy.grails.web.servlet.view.GroovyPageView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;


public class GSPPageResolver extends UrlBasedViewResolver {

    public GSPPageResolver() {
        setViewClass(requiredViewClass());
    }


    @Override
    protected Class requiredViewClass() {
        return GroovyPageView.class;
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        GroovyPageView view = (GroovyPageView) super.buildView(viewName);
        GroovyPagesTemplateEngine templateEngine = (GroovyPagesTemplateEngine) getApplicationContext().getBean(GroovyPagesTemplateEngine.BEAN_ID);
        view.setTemplateEngine(templateEngine);
        return view;
    }
}


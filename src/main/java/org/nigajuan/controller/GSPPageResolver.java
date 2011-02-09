package org.nigajuan.controller;

import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine;
import org.codehaus.groovy.grails.web.servlet.view.GroovyPageView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * Created by IntelliJ IDEA.
 * User: nigajuan
 * Date: 02/02/11
 * Time: 20:51
 * To change this template use File | Settings | File Templates.
 */
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


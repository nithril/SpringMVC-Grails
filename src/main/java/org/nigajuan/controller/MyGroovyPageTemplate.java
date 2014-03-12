package org.nigajuan.controller;


import java.util.Map;

import groovy.lang.Writable;
import groovy.text.Template;
import org.codehaus.groovy.grails.web.pages.GroovyPageMetaInfo;


/**
 * Knows how to make in instance of GroovyPageWritable.
 *
 * @author Graeme Rocher
 * @since 0.5
 */
public class MyGroovyPageTemplate implements Template {

    private GroovyPageMetaInfo metaInfo;
    private boolean allowSettingContentType = false;

    public MyGroovyPageTemplate(GroovyPageMetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    public Writable make() {
        return new MyGroovyPageWritable(metaInfo, allowSettingContentType);
    }

    @SuppressWarnings("rawtypes")
    public Writable make(Map binding) {
        MyGroovyPageWritable gptw = new MyGroovyPageWritable(metaInfo, allowSettingContentType);
        gptw.setBinding(binding);
        return gptw;
    }

    public GroovyPageMetaInfo getMetaInfo() {
        return metaInfo;
    }

    public boolean isAllowSettingContentType() {
        return allowSettingContentType;
    }

    public void setAllowSettingContentType(boolean allowSettingContentType) {
        this.allowSettingContentType = allowSettingContentType;
    }
}

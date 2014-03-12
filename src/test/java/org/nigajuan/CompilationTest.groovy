package org.nigajuan

import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.pages.GroovyPageBinding
import org.codehaus.groovy.grails.web.pages.GroovyPageMetaInfo
import org.codehaus.groovy.grails.web.pages.TagLibraryLookup
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.nigajuan.parser.GroovyPageCompiler
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.mock.web.MockServletContext
import org.testng.annotations.Test

import javax.servlet.ServletContext

/**
 * Created by nigajuan on 09/03/14.
 */
class CompilationTest {


    @Test
    public void testCompile(){

        def compiler = new GroovyPageCompiler()
        compiler.setViewsDir(new File("D:\\developpement\\testcontroller\\grails-app\\views\\foo\\"))
        compiler.srcFiles.add(new File("D:\\developpement\\testcontroller\\grails-app\\views\\foo\\toto.gsp"))
        compiler.setTargetDir(new File("D:\\developpement\\SpringMVC-Grails\\target\\test-classes"))

        compiler.compile()

        println("ok")
    }

    @Test
    public void testLoad(){
        MockServletContext mockServletContext = new MockServletContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest()
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse()


        Class clazz = Class.forName("gsp_defaulttoto_gsp")

        GroovyPage page = clazz.newInstance()

        StringWriter stringWriter = new StringWriter()

        GrailsWebRequest grailsWebRequest = new GrailsWebRequest(mockHttpServletRequest,mockHttpServletResponse,mockServletContext)

        GroovyPageMetaInfo groovyPageMetaInfo = new GroovyPageMetaInfo(clazz);


        /*TagLibArtefactHandler tagLibArtefactHandler = new TagLibArtefactHandler()
        GrailsClass grailsClass = tagLibArtefactHandler.newArtefactClass(ApplicationTagLib)


        TagLibraryLookup libraryLookup = new TagLibraryLookup()
        libraryLookup.registerTagLib(grailsClass)*/


        GroovyPageBinding binding = new GroovyPageBinding()
        binding.setVariable("foo" , ["b", "d" , "e"])

        page.setGspTagLibraryLookup()

        page.setBinding(binding)
        page.initRun(stringWriter, grailsWebRequest, groovyPageMetaInfo);
        page.run()

        println(stringWriter.toString())

    }
}

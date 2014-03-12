package org.nigajuan.parser


import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.Phases
import org.codehaus.groovy.grails.compiler.web.pages.GroovyPageInjectionOperation
import org.codehaus.groovy.grails.web.pages.GroovyPageMetaInfo

/**
 * Used to compile GSP files into a specified target directory.
 *
 * @author Graeme Rocher
 * @since 1.2
 */
class GroovyPageCompiler {

    private static final LOG = LogFactory.getLog(GroovyPageCompiler)

    private Map compileGSPRegistry = [:]

    File generatedGroovyPagesDirectory
    File targetDir
    CompilerConfiguration compilerConfig = new CompilerConfiguration()
    GroovyPageInjectionOperation operation = new GroovyPageInjectionOperation()
    GroovyClassLoader classLoader = new GroovyClassLoader(Thread.currentThread().contextClassLoader, compilerConfig)

    List<File> srcFiles = []
    File viewsDir
    String viewPrefix = '/'
    String packagePrefix = 'default'
    String encoding = "UTF-8"

    void setCompilerConfig(CompilerConfiguration c) {
        compilerConfig = c
        classLoader = new GroovyClassLoader(Thread.currentThread().contextClassLoader, compilerConfig)
    }

    /**
     * Compiles the given GSP pages and returns a Map of URI to classname mappings
     */
    Map compile() {
        if (srcFiles && targetDir && viewsDir) {
            LOG.debug "Compiling ${srcFiles.size()} GSP files using GroovyPageCompiler"
            for (gsp in srcFiles) {
                compileGSP(viewsDir, gsp, viewPrefix, packagePrefix)
            }
        }
        return compileGSPRegistry
    }

    /**
     * Compiles an individual GSP file
     *
     * @param viewsDir The base directory that contains the GSP view
     * @param gspfile The actual GSP file reference
     * @param viewPrefix The prefix to use for the path to the view
     * @param packagePrefix The package prefix to use which allows scoping for different applications and plugins
     *
     */
    protected void compileGSP(File viewsDir, File gspfile, String viewPrefix, String packagePrefix) {
        if (!generatedGroovyPagesDirectory) {
            generatedGroovyPagesDirectory = new File(System.getProperty("java.io.tmpdir"),"gspcompile")
            generatedGroovyPagesDirectory.mkdirs()
        }

        compilerConfig.setTargetDirectory(targetDir)
        compilerConfig.setSourceEncoding(encoding)
        def relPath = relativePath(viewsDir, gspfile)
        def viewuri = viewPrefix + relPath

        def relPackagePath = relativePath(viewsDir, gspfile.getParentFile())

        def packageDir = "gsp/${packagePrefix}"
        if (relPackagePath.length() > 0) {
            if (packageDir.length() > 0 && !packageDir.endsWith('/')) {
                packageDir += "/"
            }
            packageDir += generateJavaName(relPackagePath)
        }

        def className = packageDir.replace('/','_')
        className += generateJavaName(gspfile.name)
        // using default package because of GRAILS-5022
        packageDir = ''
        //def className = generateJavaName(gspfile.name)

        def classFile = new File(new File(targetDir, packageDir), "${className}.class")
        def packageName = packageDir.replace('/','.')
        def fullClassName
        if (packageName) {
            fullClassName = packageName + '.' + className
        }
        else {
            fullClassName = className
        }

        // compile check
        if (gspfile.exists() && (!classFile.exists() || gspfile.lastModified() > classFile.lastModified())) {
            LOG.debug("Compiling gsp ${gspfile}...")

            def gspgroovyfile = new File(new File(generatedGroovyPagesDirectory, packageDir), className + ".groovy")
            gspgroovyfile.getParentFile().mkdirs()

            gspfile.withInputStream { InputStream gspinput ->
                GroovyPageParser gpp = new GroovyPageParser(viewuri - '.gsp', viewuri, gspfile.absolutePath, gspinput)
                gpp.packageName = packageName
                gpp.className = className
                gpp.lastModified = gspfile.lastModified()
                gspgroovyfile.withWriter(encoding) { Writer gsptarget ->
                    // generate gsp groovy source
                    gpp.generateGsp(gsptarget)
                }
                // write static html parts to data file (read from classpath at runtime)
                def htmlDataFile = new File(new File(targetDir, packageDir),  className + GroovyPageMetaInfo.HTML_DATA_POSTFIX)
                htmlDataFile.parentFile.mkdirs()
                gpp.writeHtmlParts(htmlDataFile)
                // write linenumber mapping info to data file
                def lineNumbersDataFile = new File(new File(targetDir, packageDir),  className + GroovyPageMetaInfo.LINENUMBERS_DATA_POSTFIX)
                gpp.writeLineNumbers(lineNumbersDataFile)

                // register viewuri -> classname mapping
                compileGSPRegistry[viewuri] = fullClassName

                def unit = new CompilationUnit(compilerConfig, null, classLoader)
                unit.addPhaseOperation(operation, Phases.CANONICALIZATION)
                unit.addSource(gspgroovyfile)
                unit.compile()
            }
        }
        else {
            compileGSPRegistry[viewuri] = fullClassName
        }

        // write the view registry to a properties file (this is read by GroovyPagesTemplateEngine at runtime)
        File viewregistryFile = new File(targetDir, "gsp/views.properties")
        viewregistryFile.parentFile.mkdirs()
        Properties views = new Properties()
        if (viewregistryFile.exists()) {
            // only changed files are added to the mapping, read the existing mapping file
            viewregistryFile.withInputStream { stream ->
                views.load(stream)
            }
        }
        views.putAll(compileGSPRegistry)
        viewregistryFile.withOutputStream { viewsOut ->
            views.store(viewsOut, "Precompiled views for ${packagePrefix}")
        }
    }

    // find out the relative path from relbase to file
    protected String relativePath(File relbase, File file) {
        def pathParts = []
        def currentFile = file
        while (currentFile != null && currentFile != relbase) {
            pathParts += currentFile.name
            currentFile = currentFile.parentFile
        }
        pathParts.reverse().join('/')
    }

    protected generateJavaName(String str) {
        StringBuilder sb = new StringBuilder()
        int i = 0
        boolean nextMustBeStartChar = true
        char ch
        while (i < str.length()) {
            ch = str.charAt(i++)
            if (ch=='/') {
                nextMustBeStartChar = true
                sb.append(ch)
            }
            else {
                // package or class name cannot start with a number
                if (nextMustBeStartChar && !Character.isJavaIdentifierStart(ch)) {
                    sb.append('_')
                }
                nextMustBeStartChar = false
                sb.append(Character.isJavaIdentifierPart(ch) ? ch : '_')
            }
        }
        sb.toString()
    }
}

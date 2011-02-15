package org.nigajuan.taglib.composition

class CompositionTagLib {
    def version = "0.1"
    def grailsVersion = "1.1 > *"
    def author = "Nicolas Labrot"
    def authorEmail = "nicolas.labrot@gmail.com"
    def title = "Grails UI Composition"
    def description = '''UI Composition allows your Grails view to use UI composition pattern in replacement of sitemesh.'''

    static namespace = 'ui'

    def out

    def composition = { attrs, body ->
        if (!attrs.template) {
            throwTagError("Tag [composition] is missing required attribute [template]")
        }
        Composition composition = new Composition()
        body(composition)
        out << g.render(template: attrs.template, model: composition.defines)
    }

    def define = { attrs, body ->
        if (!attrs.composition) {
            throwTagError("Tag [define] is missing required attribute [composition]")
        }
        if (!attrs.name) {
            throwTagError("Tag [define] is missing required attribute [name]")
        }
        attrs.composition.defines.put(attrs.name, body)
    }
}

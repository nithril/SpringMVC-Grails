<ui:composition template="/composition">
    <ui:define composition="${it}" name="menu">
       <g:render template="/template" model="[testTemplate:foo]"/>
    </ui:define>

    <ui:define composition="${it}" name="body">
        ${account.name}
    </ui:define>
</ui:composition>

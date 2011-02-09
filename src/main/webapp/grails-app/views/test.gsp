<html>
<head>
  <meta name="layout" content="main"/>
  <title>toto</title>
</head>
<body>

<g:render template="template" model="[testTemplate:foo]"/>

${account.name}

<g:form controller="test" action="2.action" method="get">
  <input name="name"/>
</g:form>

</body>
</html>


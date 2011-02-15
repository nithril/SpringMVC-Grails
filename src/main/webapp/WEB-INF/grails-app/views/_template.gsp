
<g:set var="myHTML">
  Some re-usable code on: ${new Date()}
</g:set>


<% [1, 2, 3, 4].each { num -> %>
  <p><%="webapp  ${num}!"%></p>
<% } %>


${testTemplate}
<br/>
${myHTML}

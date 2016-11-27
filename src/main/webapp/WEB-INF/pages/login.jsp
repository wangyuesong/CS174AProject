<%--
  Created by IntelliJ IDEA.
  User: yuesongwang
  Date: 11/14/16
  Time: 9:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="include.jsp"%>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form:form id="loginForm" method="post" action="login" modelAttribute="loginBean">
    <form:label path="email">Enter your email</form:label>
    <form:input id="email" name="email" path="email" /><br>
    <form:label path="password">Please enter your password</form:label>
    <form:password id="password" name="password" path="password" /><br>
    <input type="submit" value="Submit" />
</form:form>
</body>
</html>
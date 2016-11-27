<%--
  Created by IntelliJ IDEA.
  User: yuesongwang
  Date: 11/24/16
  Time: 2:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="include.jsp"%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Users Search</h2>
<form:form method="post" modelAttribute="userSearchBean" action="/manage/searchUser">
    Email:<form:input path="email" type="text"/>
    <br/>
    TopicWords(Separate by ,): <form:input path="topicWords" type="text"/>
    <br/>
    Timestamp of Most Recent Message(yyyy-mm-dd hh:mm:ss): <form:input path="timestampOfMostRecentMessage" type="text"/>
    <br/>
    Num of Messages within 7 Days: <form:input path="numOfMessagesWithin7Days" type="text"/>
    <br/>
    <button type="submit">Search</button>
</form:form>

<h2>
    Result:
</h2>
<c:if test="${not empty resultUsers}">

    <table border="1">
        <th>
        <td>Email</td>
        <td>IsManager</td>
        <td>Name</td>
        <td>Screen Name</td>
        <td>Created time</td>
        </th>
        <c:forEach var="item" items="${resultUsers}">

            <tr>
                <td></td>
                <td>${item.email}</td>
                <td>${item.isManager}</td>
                <td>${item.name}</td>
                <td>${item.screenName}</td>
                <td>${item.createdAt}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<h2>User Creation</h2>
<form:form method="post" modelAttribute="userCreateBean" action="/manage/createUser">
    Email:<form:input path="userEmail" type="text"/>
    <br/>
    Name: <form:input path="name" type="text"/>
    <br/>
    Password: <form:input path="password" type="text"/>
    <br/>
    Screen Name: <form:input path="screenName" type="text"/>
    <br/>
    Is Manager: <form:input path="isManager" type="text"/>
    <br/>
    <button type="submit">Create</button>
</form:form>
</body>
</html>

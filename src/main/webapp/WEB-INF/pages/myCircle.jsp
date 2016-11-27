<%--
  Created by IntelliJ IDEA.
  User: yuesongwang
  Date: 11/21/16
  Time: 4:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="include.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>My Circle</h1>
My TopicWords:
<form action="/myCircle/changeTopicWords" method="post">
    <input type="text" name="topicWords" value="${topicWords}"/>
    <button type="submit">Update</button>
</form>
<table border="1">
    <th>
    <td>Content</td>
    <td>IsPublic</td>
    <td>Read Count</td>
    <td>Send by</td>
    <td>Topic Words</td>
    <td>Action</td>
    </th>
    <c:forEach var="item" items="${myCircleMessages}">
        <tr>
            <td></td>
            <td>${item.content}</td>
            <td>${item.isPublic}</td>
            <td>${item.readCount}</td>
            <td>${item.sender}</td>
            <td>${item.topicWords}</td>
            <c:if test="${item.sender == sessionScope.email}">
                <td>
                    <form action="/myCircle/deleteMessage" method="post">
                        <input type="hidden" name="messageId" value="${item.id}"/>
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </c:if>


        </tr>
    </c:forEach>
</table>

Type your message here:
<form action="/myCircle/sendMessage" method="post">
    <textarea name="message" style="width: 400px; height: 200px;" ></textarea>
    <br/>
    Tag it with topic word(Separate by ,): <input type="text" name="topicWords"/>
    <br/>
    <br/>
    IsPublic: <input type="radio" name="isPublic"/>
    <br/>
    Share This Post to Friend's Circle:
    <br/>
    <%--<select name="friendsToShare" multiple="multiple" style="width:200px">--%>
    <c:forEach var="item" items="${friends}">
        <input type="checkbox" name="friendsToShare" value="${item.email}" checked>${item.name}<br/>
    </c:forEach>
    <%--</select>--%>
    <br/>
    <button>Publish</button>
</form>

</body>
</html>

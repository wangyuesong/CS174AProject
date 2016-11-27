<%--
  Created by IntelliJ IDEA.
  User: yuesongwang
  Date: 11/22/16
  Time: 11:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="include.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>


<h1>Messages</h1>
<table border="1">
    <th>
    <td>Sender</td>
    <td>Content</td>
    <td>Action</td>
    </th>
    <c:forEach var="item" items="${privateMessages}">
        <tr>
            <td></td>
            <td>${item.sender}</td>
            <td>${item.content}</td>
            <td>
                <form action="/privateMessage/${friendEmail}/deleteMessage" method="post">
                    <input type="hidden" name="messageId" value="${item.id}"/>
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

Type your message here:
<form action="/privateMessage/${friendEmail}/sendMessage" method="post">
    <textarea name="message" style="width: 400px; height: 200px;" ></textarea>
    <br/>
    <button type="submit">Send</button>
</form>

</body>
</html>

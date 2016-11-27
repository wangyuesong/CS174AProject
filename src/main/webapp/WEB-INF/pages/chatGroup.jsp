<%--
  Created by IntelliJ IDEA.
  User: yuesongwang
  Date: 11/19/16
  Time: 2:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="include.jsp"%>
<html>
<head>
</head>
<body>
GroupName:${chatGroup.name}
<br/>
Duration: ${chatGroup.duration}

<h5>Members</h5>
<c:forEach var="item" items="${participants}">
    <c:out value="-${item.name}"/>
    <br/>
</c:forEach>
<h3>ChatGroupMessages:</h3>
<table border="1">
    <th>
    <td>Sender</td>
    <td>Content</td>
    <td>Time</td>
    </th>
    <c:forEach var="item" items="${chatGroupMessages}">

        <tr>
            <td></td>
            <td>${item.sender}</td>
            <td>${item.content}</td>
            <td>${item.createdAt}</td>
            <td>
            <c:if test="${item.sender == sessionScope.email}">
                <form action="/chatGroups/${chatGroup.id}/deleteMessage" method="post">
                    <input type="hidden" name="messageId" value="${item.id}">
                    <button type="submit">Remove</button>
                </form>
            </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
Type your message here:
<form action="/chatGroups/${chatGroup.id}/sendMessage" method="post">
    <textarea name="message" style="width: 400px; height: 200px;" ></textarea>
    <button>Send</button>
</form>
${inviteError}
<c:if test="${not empty inviteError}">
    Error: ${inviteError}
</c:if>
<h3>My Friends to invite:</h3>
<table border="1">
    <th>
    <td>ScreenName</td>
    <td>Name</td>
    <td>Action</td>
    </th>
    <c:forEach var="item" items="${inviteFriends}">

        <tr>
            <td></td>
            <td>${item.screenName}</td>
            <td>${item.name}</td>
            <td>
                <form action="/chatGroups/${chatGroup.id}/inviteFriend" method="post">
                    <button name="invite">Invite</button>
                    <input type="hidden" name="friendEmail" value="${item.email}">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<hr/>
<c:if test="${isOwner}">
    <h4>Owner Settings:</h4>
    <form action="/chatGroups/${chatGroup.id}/updateGroupSetting" method="post">
        GroupName: <input type="text" name="groupName" value="${chatGroup.name}">
        <br/>
        Duration: <input type="text" name="duration" value="${chatGroup.duration}">
        <br/>
        <button type="submit">Update</button>
    </form>

</c:if>

</body>
</html>

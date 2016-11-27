<%@include file="include.jsp"%>

<html>
<head>
    <title>Main</title>
</head>
<body>

<c:out value="Login as ${sessionScope.email}"></c:out>
<c:if test="${isManager}">
    <br/>
    <a href="/manage">Manager Console</a>
    <br/>
</c:if>
<br/>
<h3>My Circle:</h3>
<a href="/myCircle">GO</a>

<br>
<h3>Friends:</h3>
<table border="1">
    <th>
    <td>ScreenName</td>
    <td>Name</td>
    <td>Action</td>
    </th>
    <c:forEach var="item" items="${friends}">

        <tr>
            <td></td>
            <td>${item.screenName}</td>
            <td>${item.name}</td>
            <td>
                <a href="/privateMessage/${item.email}">Talk</a>
            </td>
        </tr>
    </c:forEach>
</table>
<h4>Search For a Friend:</h4>
<form action="/main/searchFriend" method="post">
    <input type="text" name="friendEmail">
    <button type="submit">Search</button>
</form>
<c:if test="${not empty searchedFriend}">
    <h4>Result:</h4>
    <table border="1">
        <th>
        <td>Email</td>
        <td>Name</td>
        <td>ScreenName</td>
        </th>
        <tr>
            <td></td>
            <td>${searchedFriend.email}</td>
            <td>${searchedFriend.name}</td>
            <td>${searchedFriend.screenName}</td>
            <td>
                <form action="/main/sendFriendRequest" method="post">
                    <input type="hidden" name="friendEmail" value="${item.email}">
                    <button>Send Request</button>
                </form>
            </td>
        </tr>
    </table>
</c:if>

<h3>Friend Requests:</h3>
<table border="1">
    <th>
    <td>From</td>
    <td>Action</td>
    </th>
    <c:forEach var="item" items="${friendsRequest}">

        <tr>
            <td></td>
            <td>${item.name}</td>
            <td>
                <form action="/main/acceptFriendRequest" method="post">
                    <input type="hidden" name="friendEmail" value="${item.email}">
                    <button name="accept">Accept</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<h3>ChatGroups:</h3>
<h5>Pending Invitations:</h5>
<table border="1">
    <th>
    <td>From</td>
    <td>ToJoin</td>
    <td>Action</td>
    </th>
    <c:forEach var="item" items="${invitationMessages}">

        <tr>
            <td></td>
            <td>${item.sender}</td>
            <td>${item.groupName}</td>
            <td>
                <form action="/main/acceptInvitation" method="post">
                    <input type="hidden" name="id" value="${item.id}"/>
                    <input type="hidden" name="groupId" value="${item.groupId}">
                    <button name="accept">Accept</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<h5>Current Groups:</h5>
<table border="1">
    <th>
    <td>ScreenName</td>
    <td>Name</td>
    </th>
    <c:forEach var="item" items="${chatGroups}">

        <tr>
            <td></td>
            <td>${item.name}</td>
            <td>${item.createdAt}</td>
            <td>
                <a href="/chatGroups/${item.id}">Enter</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

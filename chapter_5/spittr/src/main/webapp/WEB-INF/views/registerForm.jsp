<%--
  Created by IntelliJ IDEA.
  User: zjp
  Date: 17-7-15
  Time: 下午4:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />">
</head>
<body>
<h1>Register</h1>

<form method="POST">
    <table>
        <tr>
            <td>First Name:</td>
            <td><input type="text" name="firstName"/></td>
        </tr>
        <tr>
            <td>Last Name:</td>
            <td><input type="text" name="lastName"/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><input type="email" name="email"/></td>
        </tr>
        <tr>
            <td>Username:</td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td><input type="submit"></td>
        </tr>
    </table>
</form>
</body>
</html>
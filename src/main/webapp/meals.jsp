<%--
  Created by IntelliJ IDEA.
  User: Саня Z
  Date: 11.02.2021
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
    <title>Meals</title>
</head>
<body>
<h1>Meals page</h1>
<h3><a href="index.html">Home</a></h3>
<div id="meals-list-container">
    <p><a href="">Add meal</a></p>
    <c:if test="${not empty mealsTO}">
        <table border="1" cellspacing="0">
            <thead><tr><td>DateTime</td><td>Description</td><td>Calories</td><td></td><td></td></tr></thead>
            <tbody>
            <c:forEach var="meal" items="${mealsTO}">
                <tr <c:if test="${meal.excess == true}">class="excess"</c:if>>
                    <td>${meal.date} ${meal.time}</td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="">Update</a></td>
                    <td><a href="">Delete</a></td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot></tfoot>
        </table>
    </c:if>
</div>
</body>
</html>

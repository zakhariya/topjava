<%--
  Created by IntelliJ IDEA.
  User: Саня Z
  Date: 12.02.2021
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
    <title>Meal</title>
</head>
<body>
    <% String id = request.getParameter("id");
       if (id == null || id.equals("0")) { %>
    <h1>Creating new meal</h1>
    <% } else { %>
    <h1>Updating meal #<%= id%></h1>
    <% } %>
<h3><a href="index.html">Home</a></h3>
<h3><a href="meals">Meals</a></h3>

<div id="meal-form-container">
    <form method="post" action="meals" accept-charset="UTF-8">
        <label for="meal-id">ID:</label>
        <input id="meal-id" type="text" name="id" value="${meal.id}" readonly />
        <label for="meal-datetime">Date-time</label>
        <input id="meal-datetime" type="datetime-local" name="dateTime" value="${meal.dateTime}" placeholder="1970-01-01T00:00" required />
        <label for="meal-description">Description</label>
        <input id="meal-description" type="text" name="description" value="${meal.description}" required />
        <label for="meal-calories">Calories</label>
        <input id="meal-calories" type="text" name="calories" value="${meal.calories}" required />
        <c:choose>
            <c:when test="${not empty meal}">
                <input type="submit" value="Update" />
            </c:when>
            <c:otherwise>
                <input type="submit" value="Create" />
            </c:otherwise>
        </c:choose>
    </form>
</div>

</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="styles.css">

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Escape from the Ghost Town</title>
</head>

<body>
<h1>Привіт, ${sessionScope.playerName}!</h1>
<p>Ви зіграли ${sessionScope.gamesPlayed != null ? sessionScope.gamesPlayed : 0} разів.</p>

<c:choose>
    <c:when test="${not empty finale}">
        <h2>Фінал: ${finale.title}</h2>
        <p>${finale.description}</p>
        <p><strong>Мораль:</strong> ${finale.lesson}</p>
        <form method="post">
            <button type="submit" name="action" value="restart">Почати спочатку</button>
        </form>
        <form method="post">
            <button type="submit" name="action" value="exit">Зберегти і вийти</button>
        </form>
    </c:when>

    <c:otherwise>
        <h2>${currentStep.title}</h2>
        <p>${currentStep.description}</p>

        <form method="post">
            <ul>
                <c:forEach var="choice" items="${currentStep.choices}">
                    <li>
                        <button type="submit" name="choice" value="${choice.text}">${choice.text}</button>
                    </li>
                </c:forEach>
            </ul>
        </form>

        <form method="post">
            <button type="submit" name="action" value="restart">Почати спочатку</button>
        </form>
    </c:otherwise>

</c:choose>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Текстовий квест</title>
</head>

<body>
<h1>Крок гри</h1>
<p>${currentStep.text}</p>

<form method="post">
    <ul>
        <c:forEach var="choice" items="${currentStep.choices}">
            <li><button type="submit" name="choice" value="${choice.text}">${choice.text}</button>
                <span>${choice.text}</span>
            </li>
        </c:forEach>
    </ul>
</form>
</body>
</html>
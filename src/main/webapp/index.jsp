<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Escape from the Ghost Town</title>
</head>
<body>

<h1>Привіт! Це текстовий квест "Втеча з міста-привида".</h1>
<p>Щоб почати гру, введіть своє ім'я:</p>

<form action="game" method="post">
    <input type="text" name="playerName" placeholder="Ваше ім'я" required/>
    <button type="submit">Почати гру</button>
</form>


</body>
</html>

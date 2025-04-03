<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="styles.css">

<html>
<head>
    <title>Escape from the Ghost Town</title>
</head>
<body>

<h1>Привіт! Це текстовий квест "Втеча з міста-привида".</h1>
<p>Щоб почати гру, введіть своє ім'я:</p>

<form action="game" method="post" class="input-container" >
    <input type="text" name="playerName" class="input-name" placeholder="Ваше ім'я" required/>
    <button type="submit" class="start-button">Почати гру</button>
</form>


</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ERROR</title>
</head>
<body>
<a href="/">Home</a>
<h1><%= (String) request.getAttribute("errorMessage") %></h1>
</body>
</html>

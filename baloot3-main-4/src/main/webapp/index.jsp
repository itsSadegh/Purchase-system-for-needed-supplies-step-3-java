<%@ page import="ut.ie.baloot3.models.Store" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Baloot</title>
</head>
<body>
<% if (!Store.getInstance().isAnyUserLoggedIn()) { %>
<a href="login">Login</a>
<% } %>
<% if (Store.getInstance().isAnyUserLoggedIn()) { %>
<ul>
  <li>Username: <%= Store.getInstance().currentUser.getUsername() %></li>
  <li><a href="commodities">Commodities</a></li>
  <li><a href="buyList">Buy List</a></li>
  <li><a href="addCredit">Add Credit</a></li>
  <li><a href="logout">Logout</a></li>
</ul>
<% } %>
</body>
</html>
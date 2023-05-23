<%@ page import="java.util.ArrayList" %>
<%@ page import="ut.ie.baloot3.models.Commodity" %>
<%@ page import="ut.ie.baloot3.models.Store" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Commodities</title>
    <style>
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<a href="/">Home</a>
<br>
<p id="username">Username: <%= Store.getInstance().currentUser.getUsername() %> </p>
<br><br>
<form action="commodities" method="POST">
    <label>Search:</label>
    <input type="text" name="search" value="">
    <button type="submit" name="action" value="search_by_category">Search By Category</button>
    <button type="submit" name="action" value="search_by_name">Search By Name</button>
    <button type="submit" name="action" value="clear">Clear Search</button>
</form>
<br><br>
<form action="commodities" method="POST">
    <label>Sort By:</label>
    <button type="submit" name="action" value="sort_by_rate">Rate</button>
    <button type="submit" name="action" value="clear">Clear Sort</button>
</form>
<br><br>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th>Links</th>
    </tr>
    <%
        ArrayList<Commodity> commodities = (ArrayList<Commodity>) request.getAttribute("allCommoditiesArrayList");
        for (Commodity commodity : commodities) {
    %>
    <tr>
        <td><%= commodity.getId() %></td>
        <td><%= commodity.getName() %></td>
        <td><%= commodity.getProviderId() %></td>
        <td><%= commodity.getPrice() %></td>
        <td><%= commodity.getCategories() %></td>
        <td><%= commodity.getRating() %></td>
        <td><%= commodity.getInStock() %></td>
        <td><a href="/commodities/<%= commodity.getId() %>">Link</a></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>

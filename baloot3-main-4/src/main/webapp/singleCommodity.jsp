<%@ page import="ut.ie.baloot3.models.Commodity" %>
<%@ page import="ut.ie.baloot3.models.Store" %>
<%@ page import="ut.ie.baloot3.models.Comment" %>
<%@ page import="java.util.ArrayList" %>

<%
  Commodity commodity = (Commodity) request.getAttribute("requestedCommodity");
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <title>Commodity</title>
  <style>
    li {
      padding: 5px;
    }
    table {
      width: 100%;
      text-align: center;
    }
  </style>
</head>

<body>

<a href="/">Home</a>

<br>

<span>Username: <%= Store.getInstance().currentUser.getUsername() %> </span>

<br>

<ul>
  <li id="id">Id: <%= commodity.getId() %></li>
  <li id="name">Name: <%= commodity.getName() %></li>
  <li id="providerName">Provider Name: <%= Store.getInstance().getProvider(commodity.getProviderId()).getName() %></li>
  <li id="price">Price: <%= commodity.getPrice() %></li>
  <li id="categories">Categories: <%= commodity.getCategories() %></li>
  <li id="rating">Rating: <%= commodity.getRating() %></li>
  <li id="inStock">In Stock: <%= commodity.getInStock() %></li>
</ul>

<label>Add Your Comment:</label>
<form action="/comment" method="post">
  <input type="hidden" name="commodityId" value="<%= commodity.getId() %>" >
  <input type="text" name="comment" value="">
  <button type="submit">submit</button>
</form>

<br>

<form action="/rateCommodity" method="POST">
  <label>Rate(between 1 and 10):</label>
  <input type="number" name="rating" min="1" max="10">
  <input type="hidden" name="voterUsername" value="<%= Store.getInstance().currentUser.getUsername() %>">
  <input type="hidden" name="commodityId" value="<%= commodity.getId() %>">
  <button type="submit">Rate</button>
</form>

<br>

<form action="/buyList" method="POST">
  <input type="hidden" name="username" value="<%= Store.getInstance().currentUser.getUsername() %>">
  <input type="hidden" name="commodityId" value="<%= commodity.getId() %>">
  <button type="submit" name="action" value="add">Add to BuyList</button>
</form>

<br>

<table>
  <caption><h2>Comments</h2></caption>
  <tr>
    <th>username</th>
    <th>comment</th>
    <th>date</th>
    <th>likes</th>
    <th>dislikes</th>
  </tr>
  <%
    ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("commodityComments");
    for (Comment comment : comments) {
  %>
  <tr>
    <td><%= comment.username %></td>
    <td><%= comment.text %></td>
    <td><%= comment.date %></td>
    <td>
      <form action="/voteComment" method="POST">
        <label><%= comment.getLikes() %></label>
        <input type="hidden" name="commentId" value="<%= comment.id %>">
        <input type="hidden" name="voterUsername" value="<%= Store.getInstance().currentUser.getUsername() %>">
        <input type="hidden" name="commodityId" value="<%= commodity.getId() %>">
        <button type="submit" name="buttonAction" value="like">Like</button>
      </form>
    </td>
    <td>
      <form action="/voteComment" method="POST">
        <label><%= comment.getDislikes() %></label>
        <input type="hidden" name="commentId" value="<%= comment.id %>">
        <input type="hidden" name="voterUsername" value="<%= Store.getInstance().currentUser.getUsername() %>">
        <input type="hidden" name="commodityId" value="<%= commodity.getId() %>">
        <button type="submit" name="buttonAction" value="dislike">Dislike</button>
      </form>
    </td>
  </tr>
  <%
    }
  %>
</table>

<br><br>

<table>
  <caption><h2>Suggested Commodities</h2></caption>
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
    for (Commodity comm : (ArrayList<Commodity>) request.getAttribute("suggestedCommodities")) {
  %>
  <tr>
    <td><%= comm.getId() %></td>
    <td><%= comm.getName() %></td>
    <td><%= Store.getInstance().getProvider(comm.getProviderId()).getName() %></td>
    <td><%= comm.getPrice() %></td>
    <td><%= comm.getCategories() %></td>
    <td><%= comm.getRating() %></td>
    <td><%= comm.getInStock() %></td>
    <td><a href="/commodities/<%= comm.getId() %>">Link</a></td>
  </tr>
  <%
    }
  %>
</table>

</body>
</html>

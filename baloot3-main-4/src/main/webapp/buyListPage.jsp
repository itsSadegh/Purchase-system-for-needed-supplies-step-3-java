<%@ page import="ut.ie.baloot3.models.Store" %>
<%@ page import="ut.ie.baloot3.models.Commodity" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en"><head>
  <meta charset="UTF-8">
  <title><%= Store.getInstance().currentUser.getUsername() %>'s Buy List</title>
  <style>
    li {
      padding: 5px
    }
    table{
      width: 100%;
      text-align: center;
    }
  </style>
</head>
<body>
<a href="/">Home</a>
<br>
<ul>
  <li id="username">Username: <%= Store.getInstance().currentUser.getUsername() %></li>
  <li id="email">Email: <%= Store.getInstance().currentUser.getEmail() %></li>
  <li id="birthDate">Birth Date: <%= Store.getInstance().currentUser.getBirthDate() %></li>
  <li id="address">Address: <%= Store.getInstance().currentUser.getAddress() %></li>
  <li id="credit">Credit: <%= Store.getInstance().currentUser.getCredit() %></li>
  <%
    int buyListTotalPrice = 0;
    for (int commodityId : Store.getInstance().currentUser.getBuyList()) {
      buyListTotalPrice += Store.getInstance().getCommodityById(commodityId).getPrice();
    }
    buyListTotalPrice = (int) (buyListTotalPrice * ((100.0 - Store.getInstance().currentUser.getActiveDiscountPercentage()) / 100));
  %>
  <li>Current Buy List Price: <%= buyListTotalPrice %></li>
  <li>
    <a href="/addCredit">Add Credit</a>
  </li>
  <li>
    <form action="/applyDiscount" method="post">
      <label>Discount Code: </label>
      <input type="text" name="discountCode">
      <button type="submit">Apply</button>
    </form>
  </li>
  <li>
    <form action="/purchaseBuyList" method="POST">
      <label>Submit & Pay</label>
      <button type="submit">Payment</button>
    </form>
  </li>
</ul>
<table>
  <caption>
    <h2>Buy List</h2>
  </caption>
  <tbody>
  <tr>
    <th>Id</th>
    <th>Name</th>
    <th>Provider Name</th>
    <th>Price</th>
    <th>Categories</th>
    <th>Rating</th>
    <th>In Stock</th>
    <th></th>
    <th></th>
  </tr>
  <%
    double discount = (100 - Store.getInstance().currentUser.getActiveDiscountPercentage()) / 100.0;
    for (int commodityId : Store.getInstance().currentUser.getBuyList()) {
      Commodity commodity = Store.getInstance().getCommodityById(commodityId);
  %>
  <tr>
    <td><%= commodity.getId() %></td>
    <td><%= commodity.getName() %></td>
    <td><%= Store.getInstance().getProvider(commodity.getProviderId()).getName() %></td>
    <td><%= (int) (commodity.getPrice() * discount) %></td>
    <td><%= commodity.getCategories() %></td>
    <td><%= commodity.getRating() %></td>
    <td><%= commodity.getInStock() %></td>
    <td><a href="/commodities/<%= commodity.getId() %>">Link</a></td>
    <td>
      <form action="/buyList" method="POST">
        <input id="form_commodity_id" type="hidden" name="commodityId" value="<%= commodity.getId() %>">
        <button type="submit" name="action" value="remove">Remove</button>
      </form>
    </td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>

<table>
  <caption>
    <h2>Purchased Items List</h2>
  </caption>
  <tbody>
  <tr>
    <th>Id</th>
    <th>Name</th>
    <th>Provider Name</th>
    <th>Price</th>
    <th>Categories</th>
    <th>Rating</th>
    <th>In Stock</th>
    <th></th>
    <th></th>
  </tr>
  <%
    for (int commodityId : Store.getInstance().currentUser.getPurchasedList()) {
      Commodity commodity = Store.getInstance().getCommodityById(commodityId);
  %>
  <tr>
    <td><%= commodity.getId() %></td>
    <td><%= commodity.getName() %></td>
    <td><%= Store.getInstance().getProvider(commodity.getProviderId()).getName() %></td>
    <td><%= commodity.getPrice() %></td>
    <td><%= commodity.getCategories() %></td>
    <td><%= commodity.getRating() %></td>
    <td><%= commodity.getInStock() %></td>
    <td><a href="/commodities/<%= commodity.getId() %>">Link</a></td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>
</body>
</html>

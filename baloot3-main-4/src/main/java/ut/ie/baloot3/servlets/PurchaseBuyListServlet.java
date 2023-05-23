package ut.ie.baloot3.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ut.ie.baloot3.models.Comment;
import ut.ie.baloot3.models.Commodity;
import ut.ie.baloot3.models.Store;

import java.io.IOException;

@WebServlet("/purchaseBuyList")
public class PurchaseBuyListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        int buyListTotalPrice = 0;
        for (int commodityId : Store.getInstance().currentUser.getBuyList()) {
            Commodity commodity = Store.getInstance().getCommodityById(commodityId);
            buyListTotalPrice += commodity.getPrice();
        }
        buyListTotalPrice = (int) (buyListTotalPrice * ((100.0 - Store.getInstance().currentUser.getActiveDiscountPercentage()) / 100));
        if (buyListTotalPrice > Store.getInstance().currentUser.getCredit()) {
            request.setAttribute("errorMessage", "NOT ENOUGH CREDIT");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
        Store.getInstance().currentUser.addCredit(-buyListTotalPrice);
        for (int id : Store.getInstance().currentUser.getBuyList()) {
            Store.getInstance().currentUser.addToPurchasedList(id);
            Store.getInstance().getCommodityById(id).decreaseInStock();
        }
        Store.getInstance().currentUser.addToUsedDiscountCodes(Store.getInstance().currentUser.getCurrentDiscountCode());
        Store.getInstance().currentUser.setActiveDiscountPercentage(0);
        Store.getInstance().currentUser.emptyBuyList();
        response.sendRedirect("/buyList");
    }
}


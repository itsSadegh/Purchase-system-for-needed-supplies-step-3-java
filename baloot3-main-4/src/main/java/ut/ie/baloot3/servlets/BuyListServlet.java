package ut.ie.baloot3.servlets;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ut.ie.baloot3.models.Store;

import java.io.IOException;

@WebServlet("/buyList")
public class BuyListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        request.getRequestDispatcher("/buyListPage.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        int commodityId = Integer.parseInt(request.getParameter("commodityId"));
        if (request.getParameter("action").equalsIgnoreCase("add")) {
            if (Store.getInstance().getCommodityById(commodityId).getInStock() > 0) {
                Store.getInstance().currentUser.addToBuyList(commodityId);
            }
            else {
                request.setAttribute("errorMessage", "NOT ENOUGH STOCKS TO ADD THIS ITEM TO BUY LIST");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }
        }
        else if (request.getParameter("action").equalsIgnoreCase("remove")) {
            Store.getInstance().currentUser.removeFromBuyList(commodityId);
        }
        response.sendRedirect("/buyList");
    }
}

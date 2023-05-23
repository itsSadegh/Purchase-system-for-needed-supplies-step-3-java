package ut.ie.baloot3.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ut.ie.baloot3.models.Store;

import java.io.IOException;

@WebServlet("/rateCommodity")
public class RateCommodityServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        int rating = Integer.parseInt(request.getParameter("rating"));
        String voterUsername = request.getParameter("voterUsername");
        int commodityId = Integer.parseInt(request.getParameter("commodityId"));
        Store.getInstance().rateCommodity(commodityId, voterUsername, rating);
        response.sendRedirect("/commodities/" + commodityId);
    }
}

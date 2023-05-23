package ut.ie.baloot3.servlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ut.ie.baloot3.models.Store;
import ut.ie.baloot3.models.Commodity;

import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet("/commodities/*")
public class CommodityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        int commodityId = -1;
        try {
            commodityId = Integer.parseInt(request.getPathInfo().substring(1));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        Commodity requestedCommodity = Store.getInstance().getCommodityById(commodityId);
        if (requestedCommodity == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            request.setAttribute("requestedCommodity", requestedCommodity);
            request.setAttribute("commodityComments", Store.getInstance().getCommentsForCommodity(commodityId));
            request.setAttribute("suggestedCommodities", Store.getInstance().getSuggestedCommodities(commodityId));
            request.getRequestDispatcher("/singleCommodity.jsp").forward(request, response);
        }
    }
}
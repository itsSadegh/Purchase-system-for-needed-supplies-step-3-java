package ut.ie.baloot3.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import ut.ie.baloot3.models.Commodity;
import ut.ie.baloot3.models.Store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@WebServlet("/commodities")
public class CommoditiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        request.setAttribute("allCommoditiesArrayList", Store.getInstance().getCommoditiesArrayList());
        request.getRequestDispatcher("/allCommoditiesTable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        ArrayList<Commodity> resultArrayList;
        switch (request.getParameter("action")) {
            case "search_by_category":
                String searchedCategory = request.getParameter("search");
                resultArrayList = new ArrayList<Commodity>();
                for (Commodity commodity : Store.getInstance().getCommoditiesArrayList()) {
                    for (String category : commodity.getCategoriesHashset()) {
                        if (category.toLowerCase().contains(searchedCategory.toLowerCase())) {
                            resultArrayList.add(commodity);
                            break;
                        }
                    }
                }
                request.setAttribute("allCommoditiesArrayList", resultArrayList);
                request.getRequestDispatcher("/allCommoditiesTable.jsp").forward(request, response);
                break;
            case "search_by_name":
                String searchedName = request.getParameter("search");
                resultArrayList = new ArrayList<Commodity>();
                for (Commodity commodity : Store.getInstance().getCommoditiesArrayList()) {
                    if (commodity.getName().toLowerCase().contains(searchedName.toLowerCase())) {
                        resultArrayList.add(commodity);
                    }
                }
                request.setAttribute("allCommoditiesArrayList", resultArrayList);
                request.getRequestDispatcher("/allCommoditiesTable.jsp").forward(request, response);
                break;
            case "clear":
                doGet(request, response);
                break;
            case "sort_by_rate":
                ArrayList<Commodity> sortedCommodities = (ArrayList<Commodity>) Store.getInstance().getCommoditiesArrayList().clone();
                for (int i = 0; i < sortedCommodities.size(); i++) {
                    for (int j = i + 1; j < sortedCommodities.size(); j++) {
                        if (sortedCommodities.get(j).getRating() > sortedCommodities.get(i).getRating()) {
                            Collections.swap(sortedCommodities, i, j);
                        }
                    }
                }
                request.setAttribute("allCommoditiesArrayList", sortedCommodities);
                request.getRequestDispatcher("/allCommoditiesTable.jsp").forward(request, response);
                break;
        }
//        response.sendRedirect("/commodities");
    }
}

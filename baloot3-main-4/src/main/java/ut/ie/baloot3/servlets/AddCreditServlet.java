package ut.ie.baloot3.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ut.ie.baloot3.models.Store;

import java.io.IOException;

@WebServlet("/addCredit")
public class AddCreditServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        request.getRequestDispatcher("/addCreditPage.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        int toBeAddedCredit = -1;
        try {
            toBeAddedCredit = Integer.parseInt(request.getParameter("credit"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        if (toBeAddedCredit < 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        Store.getInstance().currentUser.addCredit(toBeAddedCredit);
        request.getRequestDispatcher("/200.jsp").forward(request, response);
    }
}

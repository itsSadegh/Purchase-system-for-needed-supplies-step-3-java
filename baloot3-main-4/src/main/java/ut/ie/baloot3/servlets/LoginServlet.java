package ut.ie.baloot3.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import ut.ie.baloot3.models.Store;
import ut.ie.baloot3.models.User;

import java.io.IOException;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/loginPage.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = Store.getInstance().getUserByUsername(username);
        if (user == null) {
            response.sendRedirect("/login");
        }
        else {
            if (!user.isPasswordCorrect(password)) {
                response.sendRedirect("/login");
            }
            else {
                Store.getInstance().currentUser = user;
                response.sendRedirect("/");
            }
        }
    }
}


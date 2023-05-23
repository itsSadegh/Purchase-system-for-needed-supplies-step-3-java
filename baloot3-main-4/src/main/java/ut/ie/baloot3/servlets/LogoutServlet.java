package ut.ie.baloot3.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import ut.ie.baloot3.models.Store;
import ut.ie.baloot3.models.User;

import java.io.IOException;
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Store.getInstance().currentUser = null;
        response.sendRedirect("/");
    }
}


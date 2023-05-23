package ut.ie.baloot3.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ut.ie.baloot3.models.Comment;
import ut.ie.baloot3.models.Store;

import java.io.IOException;
import java.lang.ref.SoftReference;

@WebServlet("/applyDiscount")
public class ApplyDiscountCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        String discountCode = request.getParameter("discountCode");
        Store.getInstance().currentUser.setActiveDiscountPercentage(Store.getInstance().getDiscountPercentage(discountCode));
        if (Store.getInstance().getDiscountPercentage(discountCode) != 0) {
            Store.getInstance().currentUser.setCurrentDiscountCode(discountCode);
        }
        else {
            Store.getInstance().currentUser.setCurrentDiscountCode(null);
        }
        response.sendRedirect("/buyList");
    }
}


package ut.ie.baloot3.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ut.ie.baloot3.models.Comment;
import ut.ie.baloot3.models.Store;

import java.io.IOException;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        int commodityId = Integer.parseInt(request.getParameter("commodityId"));
        String comment = request.getParameter("comment");
        Comment toBeAddedComment = new Comment();
        toBeAddedComment.username = Store.getInstance().currentUser.getUsername();
        toBeAddedComment.userEmail = Store.getInstance().currentUser.getEmail();
        toBeAddedComment.commodityId = commodityId;
        toBeAddedComment.text = comment;
        toBeAddedComment.date = java.time.LocalDate.now().toString();
        Store.getInstance().addComment(toBeAddedComment);
        response.sendRedirect("/commodities/" + commodityId);
    }
}


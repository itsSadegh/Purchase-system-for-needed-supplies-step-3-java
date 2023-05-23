package ut.ie.baloot3.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import ut.ie.baloot3.models.Store;

import java.io.IOException;

@WebServlet("/voteComment")
public class VoteCommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!Store.getInstance().isAnyUserLoggedIn()) {
            response.sendRedirect("/login");
            return;
        }
        int commentId = Integer.parseInt(request.getParameter("commentId"));
        int commodityId = Integer.parseInt(request.getParameter("commodityId"));
        String voterUsername = request.getParameter("voterUsername");
        if (request.getParameter("buttonAction").equalsIgnoreCase("like")) {
            Store.getInstance().likeOrDislikeComment(commentId, voterUsername, 1);
        }
        else {
            Store.getInstance().likeOrDislikeComment(commentId, voterUsername, -1);
        }
        response.sendRedirect("/commodities/" + commodityId);
    }
}
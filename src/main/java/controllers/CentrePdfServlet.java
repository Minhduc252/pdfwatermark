package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.beans.PdfPreview;
import models.beans.User;
import models.bo.UserBO;

@WebServlet("/centre")
public class CentrePdfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("Login.jsp");
            return;
        }
        List<PdfPreview> list = UserBO.getPdfPreviewAllByUserId(user.getId());
        req.setAttribute( "pdfList", list );
        req.getRequestDispatcher("Centre.jsp").forward(req, resp);
    }
}

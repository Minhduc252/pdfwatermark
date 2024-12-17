package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.beans.User;
import models.bo.UserBO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("Login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = UserBO.checkLogin(username, password);

        if (user != null) {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("centre"); 
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
        }
    }
}

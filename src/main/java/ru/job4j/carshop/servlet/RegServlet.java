package ru.job4j.carshop.servlet;

import ru.job4j.carshop.model.User;
import ru.job4j.carshop.store.UserStore;
import ru.job4j.carshop.store.UserStoreImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserStore store = UserStoreImpl.instOf();
        if (store.getByEmail(email).isPresent()) {
            req.setAttribute("error", "Пользователь с этой почтой уже зарегистрирован");
            doGet(req, resp);
        } else {
            store.saveOrUpdate(User.of(name, email, password));
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
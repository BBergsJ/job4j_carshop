package ru.job4j.carshop.servlet;

import ru.job4j.carshop.model.User;
import ru.job4j.carshop.store.UserStore;
import ru.job4j.carshop.store.UserStoreImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserStore store = UserStoreImpl.instOf();
        Optional<User> user = store.getByEmail(email);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user.get());
                session.setAttribute("email", user.get().getEmail());
                resp.sendRedirect(req.getContextPath() + "/index.do");
            } else {
                req.setAttribute("error", "Не верный пароль");
                doGet(req, resp);
            }
        } else {
            req.setAttribute("error", "Пользователь не зарегистрирован");
            doGet(req, resp);
        }
    }
}

package ru.job4j.carshop.servlet;

import ru.job4j.carshop.model.Brand;
import ru.job4j.carshop.model.CarType;
import ru.job4j.carshop.model.Post;
import ru.job4j.carshop.store.PostStore;
import ru.job4j.carshop.store.PostStoreImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostStore store = PostStoreImpl.instOf();

        req.setAttribute("posts", store.getAll());
        req.setAttribute("types", store.getAllCarTypes());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
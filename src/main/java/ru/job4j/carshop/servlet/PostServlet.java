package ru.job4j.carshop.servlet;

import org.apache.commons.io.FilenameUtils;
import ru.job4j.carshop.model.Image;
import ru.job4j.carshop.model.Post;
import ru.job4j.carshop.model.User;
import ru.job4j.carshop.store.PostStore;
import ru.job4j.carshop.store.PostStoreImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostStore store = PostStoreImpl.instOf();
        String id = req.getParameter("id");
        Post post = store.get(Integer.parseInt(id));
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("post", post);
        req.setAttribute("carTypes", store.getAllCarTypes());
        req.setAttribute("brands", store.getAllCarBrands());
            if (post.getUser().equals(user)) {
                req.getRequestDispatcher("post/edit.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("post.jsp").forward(req, resp);
            }
    }
}
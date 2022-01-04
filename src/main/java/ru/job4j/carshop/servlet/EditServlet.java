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
import java.util.*;
import java.util.stream.Collectors;

@MultipartConfig(
        location = "c:\\projects\\job4j_carshop\\images\\",
        fileSizeThreshold = 1024 * 1024 * 10)
public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostStore store = PostStoreImpl.instOf();
        String id = req.getParameter("id");

        Post post = id == null ? new Post() : store.get(Integer.parseInt(id));
        req.setAttribute("post", post);
        req.setAttribute("carTypes", store.getAllCarTypes());
        req.setAttribute("brands", store.getAllCarBrands());
        req.getRequestDispatcher("/post/edit.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        User user = (User) req.getSession().getAttribute("user");
        String brandId = req.getParameter("brandId");
        String carTypeId = req.getParameter("typeId");
        boolean sold = req.getParameter("sold") != null;
        String description = req.getParameter("description");
        int id = Integer.parseInt(req.getParameter("id"));

        Set<Image> images = new HashSet<>();
        List<Part> parts = req.getParts().stream()
                .filter(part -> "file".equals(part.getName()) && part.getSize() > 0)
                .collect(Collectors.toList());
        for (Part part : parts) {
            String name = getRandomFileName(part.getSubmittedFileName());
            part.write(name);
            images.add(Image.of(name));
        }

        PostStore store = PostStoreImpl.instOf();
        Post post = id == 0 ? new Post() : store.get(id);
        post.setDescription(description);
        post.setSold(sold);
        post.setUser(user);
        post.setCarType(store.getCarTypeId(Integer.valueOf(carTypeId)));
        post.setBrand(store.getCarBrandId(Integer.valueOf(brandId)));
        post.setCreated(new Date(System.currentTimeMillis()));
        post.getImages().addAll(images);

        store.saveOrUpdate(post);
        resp.sendRedirect(req.getContextPath() + "/index.do");
    }

    private String getRandomFileName(String name) {
        return java.util.UUID.randomUUID() + "." + FilenameUtils.getExtension(name);
    }
}
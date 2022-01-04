package ru.job4j.carshop.store;

import ru.job4j.carshop.model.*;

import java.util.List;
import java.util.Optional;

public class HQLMain {
    public static void main(String[] args) {
        PostStore postStore = PostStoreImpl.instOf();

        Post post = postStore.get(13);

        for (Image image : post.getImages()) {
            System.out.println(image.getName());
        }
    }
}
package ru.job4j.carshop.store;

import ru.job4j.carshop.model.*;

import java.util.List;
import java.util.Optional;

public class HQLMain {
    public static void main(String[] args) {
        PostStore postStore = PostStoreImpl.instOf();

        List<Post> rsl = postStore.getByCurrentDay();
        System.out.println(rsl.size());
        for (Post post : rsl) {
            System.out.println(post.getCreated().toString());
        }
    }
}
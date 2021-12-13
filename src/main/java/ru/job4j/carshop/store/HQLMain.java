package ru.job4j.carshop.store;

import ru.job4j.carshop.model.User;

import java.util.List;
import java.util.Optional;

public class HQLMain {
    public static void main(String[] args) {
        UserStore userStore = UserStoreImpl.instOf();

        User user = User.of("test", "test");
        userStore.save(user);
//        User user2 = User.of("test2", "test2");
//        userStore.saveOrUpdate(user2);
//        List<User> allUsers = userStore.getAllUsers();
//
//        Optional rsl = userStore.getByEmail("test");
//        System.out.println(rsl.get());
    }
}
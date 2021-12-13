package ru.job4j.carshop.store;

import ru.job4j.carshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStore {
    User save(User user);
    Optional<User> getByEmail(String email);
    List<User> getAllUsers();
    User saveOrUpdate(User user);
}

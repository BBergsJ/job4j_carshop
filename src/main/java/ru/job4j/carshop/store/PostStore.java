package ru.job4j.carshop.store;

import ru.job4j.carshop.model.Brand;
import ru.job4j.carshop.model.CarType;
import ru.job4j.carshop.model.Post;

import java.util.Date;
import java.util.List;

public interface PostStore {
    Post save(Post post);
    Post saveOrUpdate(Post post);
    Post get(int id);
    List<Post> getByCurrentDay();
    List<Post> getPostsWithPhoto();
    List<Post> getAll();
    List<Post> getPostsByBrand(Brand brand);
    Brand getCarBrandId(int id);
    CarType getCarTypeId(int id);
    List<Brand> getAllCarBrands();
    List<CarType> getAllCarTypes();
}

package ru.job4j.carshop.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.carshop.model.Brand;
import ru.job4j.carshop.model.Post;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class PostStoreImpl implements PostStore {

    private static SessionFactory sessionFactory;

    private PostStoreImpl() {
    }

    private final static class Lazy {
        private static final PostStore INST = new PostStoreImpl();
    }

    public static PostStore instOf() {
        return Lazy.INST;
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public Post get(int id) {
        return null;
    }

    @Override
    public List<Post> getByCurrentDay(Date date) {
        return null;
    }

    @Override
    public List<Post> getPostsWithPhoto() {
        return null;
    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public List<Post> getPostsByBrand(Brand brand) {
        return null;
    }

    @Override
    public Brand getCarBrand(int id) {
        return null;
    }

    @Override
    public List<Brand> getAllCarBrands() {
        return null;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static <T> T tx(final Function<Session, T> command) {
        final Session session = getSessionFactory().openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}

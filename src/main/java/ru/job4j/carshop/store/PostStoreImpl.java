package ru.job4j.carshop.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.carshop.model.Brand;
import ru.job4j.carshop.model.CarType;
import ru.job4j.carshop.model.Post;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class PostStoreImpl implements PostStore {

    private static SessionFactory sessionFactory;

    private PostStoreImpl() {
    }

    private static final class Lazy {
        private static final PostStore INST = new PostStoreImpl();
    }

    public static PostStore instOf() {
        return Lazy.INST;
    }

    @Override
    public Post save(Post post) {
        tx(session -> session.save(post));
        return post;
    }

    @Override
    public Post saveOrUpdate(Post post) {
        return tx(session -> {
                session.saveOrUpdate(post);
                return post;
            }
        );
    }

    @Override
    public Post get(int id) {
        return tx(session -> session.createQuery("from Post where id = :id", Post.class)
                .setParameter("id", id).uniqueResult()
        );
    }

    @Override
    public List<Post> getByCurrentDay() {
        return tx(session -> session.createQuery("from Post p where p.created >= current_date - 1", Post.class)
                .list()
        );
    }

    @Override
    public List<Post> getPostsWithPhoto() {
        return tx(session -> session.createQuery("from Post p where p.images is not empty", Post.class)
                .list()
        );
    }

    @Override
    public List<Post> getAll() {
        return tx(session -> session.createQuery("from Post p join fetch p.images", Post.class)
                .getResultList()
        );
    }

    @Override
    public List<Post> getPostsByBrand(Brand brand) {
        return tx(session -> session.createQuery("from Post p where p.brand = :brand", Post.class)
                .setParameter("brand", brand)
                .list()
        );
    }

    @Override
    public Brand getCarBrandId(int id) {
        return tx(session -> session.createQuery("from Brand b where b.id = :id", Brand.class)
                .setParameter("id", id)
                .uniqueResult()
        );
    }

    @Override
    public CarType getCarTypeId(int id) {
        return tx(session -> session.createQuery("from CarType ct where ct.id = :id", CarType.class)
                .setParameter("id", id)
                .uniqueResult()
        );
    }

    @Override
    public List<Brand> getAllCarBrands() {
        return tx(session -> session.createQuery("from Brand", Brand.class)
                .list()
        );
    }

    @Override
    public List<CarType> getAllCarTypes() {
        return tx(session -> session.createQuery("from CarType", CarType.class)
                .getResultList()
        );
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

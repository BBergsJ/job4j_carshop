package ru.job4j.carshop.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.carshop.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserStoreImpl implements UserStore {

    private static SessionFactory sessionFactory;

    private UserStoreImpl() {
    }

    private static final class Lazy {
        private final static UserStore INST = new UserStoreImpl();
    }

    public static UserStore instOf() {
        return Lazy.INST;
    }

    @Override
    public User save(User user) {
        tx(session -> session.save(user));
        return user;
    }

    @Override
    public User saveOrUpdate(User user) {
        return tx(session -> {
                    session.saveOrUpdate(user);
                    return user;
                }
        );
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return tx(session -> session.createQuery("from User where email = :email", User.class)
                .setParameter("email", email)
                .uniqueResultOptional());
    }

    @Override
    public List<User> getAllUsers() {
        return tx(session -> session.createQuery("from User").list());
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

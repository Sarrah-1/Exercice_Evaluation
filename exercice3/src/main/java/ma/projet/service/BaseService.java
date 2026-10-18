package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

public abstract class BaseService<T> implements IDao<T> {

    private final Class<T> type;

    protected BaseService(Class<T> type) {
        this.type = type;
    }

    @Override
    public boolean create(T o) {
        return executer(session -> session.persist(o));
    }

    @Override
    public boolean update(T o) {
        return executer(session -> session.merge(o));
    }

    @Override
    public boolean delete(T o) {
        return executer(session -> session.remove(session.contains(o) ? o : session.merge(o)));
    }

    @Override
    public T findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(type, id);
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from " + type.getName(), type).list();
        }
    }

    private boolean executer(Consumer<Session> action) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            action.accept(session);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}

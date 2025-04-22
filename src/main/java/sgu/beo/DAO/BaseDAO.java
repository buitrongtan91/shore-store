package sgu.beo.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sgu.beo.util.JdbcUtil;

public abstract class BaseDAO<T> {

    private static final Map<Class<?>, Object> instances = new HashMap<>();

    protected Connection getConnection() {
        return JdbcUtil.getInstance().getConnection();
    }

    @SuppressWarnings("unchecked")
    public static <X> X getInstance(Class<X> clazz) {
        synchronized (instances) {
            return (X) instances.computeIfAbsent(clazz, cls -> {
                try {
                    return cls.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Cannot create DAO instance for: " + cls.getName(), e);
                }
            });
        }
    }

    public abstract boolean insert(T entity);

    public abstract boolean update(T entity);

    public abstract List<T> findAll();

    public abstract T findById(int id);

    public abstract List<T> findByName(String name);

}

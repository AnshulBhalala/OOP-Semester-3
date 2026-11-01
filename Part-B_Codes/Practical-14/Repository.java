package repository;

import model.annotation.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Repository<T> {
    private final ConcurrentHashMap<String, T> store = new ConcurrentHashMap<>();

    public T save(T entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        store.put(readId(entity), entity);
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) save(item);
    }

    public T findById(String id) { return store.get(id); }
    public ArrayList<T> findAll() { return new ArrayList<>(store.values()); }
    public boolean delete(String id) { return store.remove(id) != null; }
    public int count() { return store.size(); }

    private String readId(T entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    if (value == null) throw new IllegalStateException("@Id field cannot be null");
                    return value.toString();
                } catch (ReflectiveOperationException e) {
                    throw new IllegalStateException("Unable to read @Id field", e);
                }
            }
        }
        throw new IllegalArgumentException("Entity has no field annotated with @Id");
    }
}

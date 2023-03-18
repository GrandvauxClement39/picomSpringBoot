package fr.picom.picomspring.service;

import java.util.List;

public interface GlobalService<T, ID> {
    T add(T entity);

    List<T> findAll();

    T findById(ID id);

    boolean deleteById(ID id);

    T update(T entity);
}

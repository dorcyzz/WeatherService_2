package com.seb.services.weather.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 24/07/2014.
 */
public interface GenericDao<T, ID extends Serializable> {

    ID save(T entity);

    void update(T entity);

    void delete(T entity);

    T findByPrimaryKey(ID id);

    List<T> findAll();

}

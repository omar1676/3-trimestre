package com.omar.repository;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T, ID> {

    void create(T entity) throws SQLException;

    T findById(ID id) throws SQLException;

    List<T> findAll() throws SQLException;

    boolean update(T entity) throws SQLException;

    boolean delete(ID id) throws SQLException;
}
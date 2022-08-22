package fr.eni.encheres.dal;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    public void insert(T t) throws SQLException;
    public void update(T t) throws SQLException;
    public void delete(T t) throws SQLException;
}
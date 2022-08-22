package fr.eni.encheres.dal;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    public void insert(T t);
    public void update(T t);
    public void delete(T t);
}
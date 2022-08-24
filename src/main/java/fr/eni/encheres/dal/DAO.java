package fr.eni.encheres.dal;

import fr.eni.encheres.BusinessException;

import java.sql.SQLException;

public interface DAO<T> {
    public void insert(T t) throws BusinessException;
    public void update(T t) throws BusinessException;
    public void delete(T t) throws BusinessException;
}
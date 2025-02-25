package services;

import java.sql.SQLException;
import java.util.List;
// <>
public interface IService <T>{
    void add(T t) throws SQLException;
    void edit(T t) throws SQLException;
    void delete(T t) throws SQLException;
    T get(T t) throws SQLException;
    T get(int id) throws SQLException;
    List<T> getAll() throws SQLException;
}

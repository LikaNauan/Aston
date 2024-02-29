package hw3_crud.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public interface DAO<T> {

    default Connection getConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "postgresql");
        connectionProps.put("password", "postgresql");

        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/",
                connectionProps);
    }

    T getById(int id);
    List<T> getAll();
    void save(T save);
    void update(T update);
    void delete(int id);
}

package hw3_crud.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public abstract class DAO<T> {


     protected Connection getConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "postgresql");
        connectionProps.put("password", "postgresql");

        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/",
                connectionProps);
    }

    abstract T getById(int id);

    abstract List<T> getAll();

    abstract T update(int id, T update);

    abstract void delete(int id);
}

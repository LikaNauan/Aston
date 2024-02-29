package hw3_crud.dao;

import hw3_crud.entities.School;
import hw3_crud.entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:3306/test",
            "login", "password");
    private PreparedStatement preparedStatement;

    public StudentDAO() throws SQLException {
    }
    // Конструктор для установки соединения с базой данных

    public Student getStudentById(int id) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT * FROM students WHERE id = ?");
        preparedStatement.setInt(1,id);

        ResultSet results = preparedStatement.executeQuery();
        if (!results.first())
            return null;

        Student student = new Student();
        student.setId(results.getInt(1));
        student.setId(results.getString(2));
        student.setId(results.getObject(3, School.class));
        return  student;
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        ResultSet results = connection.createStatement().executeQuery("SELECT * FROM students");

        while (results.next()) {
            Student student = new Student();
            student.setId(results.getInt(1));
            student.setId(results.getString(2));
            student.setId(results.getObject(3, School.class));

            students.add(student);
        }
        return students;
    }

    public void save(Student student) {
        preparedStatement = connection.prepareStatement("INSERT INTO students (id, name, )");
    }

    public void update(Student student) {

    }

    public void delete(int id) {

    }
}

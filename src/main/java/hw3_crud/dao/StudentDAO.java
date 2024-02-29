package hw3_crud.dao;

import hw3_crud.entities.users.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements DAO<Student> {
    private PreparedStatement preparedStatement;
    public StudentDAO() throws SQLException {
    }

    public Student getById(int id) {
        Student student = new Student();
        try {
            preparedStatement = getConnection().prepareStatement("SELECT * FROM students WHERE id = ?");
            preparedStatement.setInt(1,id);

            ResultSet results = preparedStatement.executeQuery();
            if (!results.first())
                return null;

            student.setId(results.getInt(1));
            student.setName(results.getString(2));

            results.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  student;
    }

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()){
            ResultSet results = statement.executeQuery("SELECT * FROM students");

            while (results.next()) {
                Student student = new Student();
                student.setId(results.getInt(1));
                student.setName(results.getString(2));

                students.add(student);
            }
            results.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    public void save(Student student) {
        try {
            preparedStatement = getConnection().prepareStatement("INSERT INTO students (id, name) VALUES (?, ?)");

            preparedStatement.setInt(1,student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Student student){
        Student studentOld = getById(student.getId());
        if (studentOld == null){
            save(student);
        }

        try {
            preparedStatement = getConnection().prepareStatement("update students set name = ? where id = ?");

            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2,student.getId());
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id){
        try{
            preparedStatement = getConnection().prepareStatement("delete from students where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

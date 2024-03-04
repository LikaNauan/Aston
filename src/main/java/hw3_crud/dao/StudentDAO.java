package hw3_crud.dao;

import hw3_crud.dto.StudentDTO;
import hw3_crud.entity.users.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends DAO<Student> {

    public Student getById(int id) {
        Student student = null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "SELECT * FROM students WHERE id = ?");
        ) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                student = getStudent(resultSet);
            }

            resultSet.close();

            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> getAll() {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM students")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                Student student = getStudent(resultSet);
                students.add(student);
            }

            resultSet.close();

            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StudentDTO> getAllStudentForLectureList(int lectureId) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM students WHERE lecture_id = ?")) {
            preparedStatement.setInt(1, lectureId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<StudentDTO> students = new ArrayList<>();
            while (resultSet.next()) {
                Student student = getStudent(resultSet);
                StudentDTO studentDTO = new StudentDTO(student.getName(), student.getAge());
                students.add(studentDTO);
            }

            resultSet.close();

            return students;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student save(String name, int age, int lectureId) {
        Student student = null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "INSERT INTO students (name, age, lecture_id) VALUES (?,?, ?) returning *")) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setInt(3, lectureId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                student = getStudent(resultSet);
            }

            resultSet.close();

            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Student update(int id, Student student) {
        Student studentNew = null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "update students set name = ?, age = ?, lecture_id = ? where id = ? returning *")) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getAge());
            preparedStatement.setInt(3, student.getLectureId());
            preparedStatement.setInt(4, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                studentNew = getStudent(resultSet);
            }

            resultSet.close();

            return studentNew;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("delete from students where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLecture(int lectureId) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("update students set lecture_id = 0 where lecture_id = ? ")) {
            preparedStatement.setInt(1, lectureId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Student getStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setName(resultSet.getString("name"));
        student.setAge(resultSet.getInt("age"));
        student.setId(resultSet.getInt("lecture_id"));

        return student;
    }
}

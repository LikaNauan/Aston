package hw3_crud.dao;

import hw3_crud.dto.TeacherDTO;
import hw3_crud.entity.users.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO extends DAO<Teacher> {

    @Override
    public Teacher getById(int id) {
        Teacher teacher = null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "SELECT * FROM teachers WHERE id = ?");
        ) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                teacher = getTeacher(resultSet);
            }

            resultSet.close();

            return teacher;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Teacher> getAll() {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM teachers")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Teacher> teachers = new ArrayList<>();
            while (resultSet.next()) {
                Teacher teacher = getTeacher(resultSet);
                teachers.add(teacher);
            }

            resultSet.close();

            return teachers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TeacherDTO> getAllTeacherForLectureList(int lectureId) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM teachers WHERE lecture_id = ?")) {
            preparedStatement.setInt(1, lectureId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<TeacherDTO> teachers = new ArrayList<>();
            while (resultSet.next()) {
                Teacher teacher = getTeacher(resultSet);
                TeacherDTO teacherDTO = new TeacherDTO(teacher.getName(), teacher.getAge());
                teachers.add(teacherDTO);
            }

            resultSet.close();

            return teachers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Teacher save(String name, int age, int lectureId) {
        Teacher teacher = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO teachers (name, age, lecture_id ) VALUES (?, ?, ?)  returning *");) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setInt(3, lectureId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                teacher = getTeacher(resultSet);
            }

            resultSet.close();

            return teacher;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Teacher update(int id, Teacher teacher) {
        Teacher teacherNew = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("update teachers set name = ?, age = ?, lecture_id = ? where id = ? returning *")) {

            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setInt(2, teacher.getAge());
            preparedStatement.setInt(3, teacher.getLectureId());
            preparedStatement.setInt(4, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                teacherNew = getTeacher(resultSet);
            }

            resultSet.close();

            return teacherNew;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("delete from teachers where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLecture(int lectureId) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("update teachers set lecture_id = 0 where lecture_id = ? ")) {
            preparedStatement.setInt(1, lectureId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Teacher getTeacher(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(resultSet.getInt("id"));
        teacher.setName(resultSet.getString("name"));
        teacher.setAge(resultSet.getInt("age"));
        teacher.setId(resultSet.getInt("lecture_id"));

        return teacher;
    }
}

package hw3_crud.dao;

import hw3_crud.entities.Lecture;
import hw3_crud.entities.users.Teacher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO implements DAO<Teacher>{
    private PreparedStatement preparedStatement;

    @Override
    public Teacher getById(int id) {
        Teacher teacher = new Teacher();
        try {
            preparedStatement = getConnection().prepareStatement("SELECT * FROM teachers WHERE id = ?");
            preparedStatement.setInt(1,id);

            ResultSet results = preparedStatement.executeQuery();
            if (!results.first())
                return null;

            teacher.setId(results.getInt(1));
            teacher.setName(results.getString(2));
            teacher.setLecture(results.getObject(3, Lecture.class));

            results.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  teacher;
    }

    @Override
    public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()){
            ResultSet results = statement.executeQuery("SELECT * FROM teachers");

            while (results.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(results.getInt(1));
                teacher.setName(results.getString(2));
                teacher.setLecture(results.getObject(3, Lecture.class));

                teachers.add(teacher);
            }
            results.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachers;
    }

    @Override
    public void save(Teacher teacher) {
        try {
            preparedStatement = getConnection().prepareStatement("INSERT INTO teachers (id, name, lecture ) VALUES (?, ?, ?, ?)");

            preparedStatement.setInt(1,teacher.getId());
            preparedStatement.setString(2, teacher.getName());
            preparedStatement.setObject(3,teacher.getLecture());
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Teacher teacher) {
        Teacher teacherOld = getById(teacher.getId());
        if (teacherOld == null){
            save(teacher);
        }

        try {
            preparedStatement = getConnection().prepareStatement("update teachers set name = ?, lecture = ? where id = ?");

            preparedStatement.setString(1, teacher.getName());
            preparedStatement.setObject(2,teacher.getLecture());
            preparedStatement.setInt(3,teacher.getId());
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try{
            preparedStatement = getConnection().prepareStatement("delete from teachers where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

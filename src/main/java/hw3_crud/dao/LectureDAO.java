package hw3_crud.dao;

import hw3_crud.entity.Lecture;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LectureDAO extends DAO<Lecture> {

    @Override
    public Lecture getById(int id) {
        Lecture lecture = null;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "SELECT * FROM lectures WHERE id = ?");
        ) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lecture = getLecture(resultSet);
            }

            resultSet.close();

            return lecture;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Lecture> getAll() {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT * FROM lectures")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Lecture> lectures = new ArrayList<>();
            while (resultSet.next()) {
                Lecture lecture = getLecture(resultSet);
                lectures.add(lecture);
            }

            resultSet.close();

            return lectures;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Lecture save(String topic) {
        Lecture lecture = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("INSERT INTO lectures (topic) VALUES (?)  returning *");) {

            preparedStatement.setString(1, topic);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lecture = getLecture(resultSet);
            }

            resultSet.close();

            return lecture;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Lecture update(int id, Lecture lecture) {
        Lecture lectureNew = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("update lectures set topic = ? where id = ? returning *")) {

            preparedStatement.setString(1, lecture.getTopic());
            preparedStatement.setInt(2, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lectureNew = getLecture(resultSet);
            }

            resultSet.close();

            return lectureNew;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("delete from lectures where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Lecture getLecture(ResultSet resultSet) throws SQLException {
        Lecture teacher = new Lecture();
        teacher.setId(resultSet.getInt("id"));
        teacher.setTopic(resultSet.getString("topic"));

        return teacher;
    }
}

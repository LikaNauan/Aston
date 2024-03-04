package hw3_crud.service;

import hw3_crud.dao.TeacherDAO;
import hw3_crud.entity.users.Teacher;

import java.util.List;

public class TeacherService {

    private TeacherDAO teacherDAO = new TeacherDAO();

    public Teacher getTeacher(int id) {
        return teacherDAO.getById(id);
    }

    public List<Teacher> getAllTeachers() {
        return teacherDAO.getAll();
    }

    public void createTeacher(Teacher teacher) {
        teacherDAO.save(teacher.getName(), teacher.getAge(), teacher.getLectureId());
    }

    public void updateTeacher(int id, Teacher teacher) {
        teacherDAO.update(id, teacher);
    }

    public void deleteTeacher(int id) {
        teacherDAO.delete(id);
    }
}

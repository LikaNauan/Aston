package hw3_crud.service;

import hw3_crud.dao.StudentDAO;
import hw3_crud.entity.users.Student;

import java.util.List;

public class StudentService {

    private StudentDAO studentDAO = new StudentDAO();

    public Student getStudent(int id) {
        return studentDAO.getById(id);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAll();
    }

    public void createStudent(Student student) {
        studentDAO.save(student.getName(), student.getAge(), student.getLectureId());
    }

    public void updateStudent(int id, Student student) {
        studentDAO.update(id, student);
    }

    public void deleteStudent(int id) {
        studentDAO.delete(id);
    }
}

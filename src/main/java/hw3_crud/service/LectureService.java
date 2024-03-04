package hw3_crud.service;

import hw3_crud.dao.LectureDAO;
import hw3_crud.dao.StudentDAO;
import hw3_crud.dao.TeacherDAO;
import hw3_crud.dto.LectureDTO;
import hw3_crud.dto.StudentDTO;
import hw3_crud.dto.TeacherDTO;
import hw3_crud.entity.Lecture;

import java.util.List;

public class LectureService {

    private LectureDAO lectureDAO = new LectureDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private StudentDAO studentDAO = new StudentDAO();

    public Lecture getLecture(int id) {
        return lectureDAO.getById(id);
    }

    public List<Lecture> getAllLectures() {
        return lectureDAO.getAll();
    }

    public LectureDTO getLectureWithStudentsAndTeachers(int id) {
        Lecture lecture = lectureDAO.getById(id);
        List<StudentDTO> students = studentDAO.getAllStudentForLectureList(lecture.getId());
        List<TeacherDTO> teachers = teacherDAO.getAllTeacherForLectureList(lecture.getId());

        return new LectureDTO(lecture, teachers, students);
    }

    public void createLecture(Lecture lecture) {
        lectureDAO.save(lecture.getTopic());
    }

    public void updateLecture(int id, Lecture lecture) {
        lectureDAO.update(id, lecture);
    }

    public void deleteLecture(int id) {
        lectureDAO.delete(id);
        studentDAO.deleteLecture(id);
        teacherDAO.deleteLecture(id);
    }

}

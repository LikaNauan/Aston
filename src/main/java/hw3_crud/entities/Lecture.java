package hw3_crud.entities;

import hw3_crud.entities.users.Student;
import hw3_crud.entities.users.Teacher;

import java.util.List;

public class Lecture {
    private String topic;
    private List<Teacher> teachers;
    private List<Student> students;
}

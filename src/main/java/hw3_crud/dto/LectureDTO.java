package hw3_crud.dto;

import hw3_crud.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class LectureDTO {
    private Lecture lecture;

    private List<TeacherDTO> teachers;
    private List<StudentDTO> students;
}

package hw3_crud.entities.users;

import hw3_crud.entities.Lecture;
import lombok.Data;

@Data
public class Teacher extends Person{
    private Lecture lecture;
}

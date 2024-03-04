package hw3_crud.entity.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Teacher {
    private int id;
    private String name;
    private int age;
    private int lectureId;
}

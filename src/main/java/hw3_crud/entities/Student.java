package hw3_crud.entities;

import lombok.Data;

@Data
public class Student {
    private int id;
    private String name;
    private School school;
}

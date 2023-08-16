package ua.chmil.spring.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    public void setId_person(int id_person) {
        this.id_person = id_person;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    private int id_person;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 40, message = "Name should be between 2 and 30 characters")
    private String name;

    @Min(value = 1900, message = "Year of birth should be more than 1900")
    private int birthYear;

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    // порожній конструктор для методу newPerson(Model model) з класу PeopleController
    public Person() {
    }


    public int getId_person() {
        return id_person;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }
}
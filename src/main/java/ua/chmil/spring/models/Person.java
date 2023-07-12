package ua.chmil.spring.models;

public class Person {
    private int id;
    private String name;

    // порожній конструктор для методу newPerson(Model model) з класу PeopleController
    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
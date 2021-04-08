package ru.kobzar.springcourse.models;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;

    //валидация доступна в библиотеке hibernate validator
    //вот собственно аннотации для валидации, по названию аннотаций и так все понятно
    @NotEmpty (message = "Empty name field")
    @Size (min = 2, max = 30, message = "size should be from 2 to 30")
    private String name;

    @Min(value = 0, message = "age should me from 0")
    private int age;

    @NotEmpty(message = "email empty field")
    @Email (message = "wrong email format")
    private String email;

    public Person() {
    }

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

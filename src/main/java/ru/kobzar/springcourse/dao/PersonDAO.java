package ru.kobzar.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.kobzar.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

//DAO (data access object) джава класс для взаимодействия с бд (тут мы имитируем бд с помощью листа и создаем методы)
//для создания/получения объекта
@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++PEOPLE_COUNT, "Tom", 47, "kek@mail.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Bob", 21, "tom@yandex.ru"));
        people.add(new Person(++PEOPLE_COUNT, "Mike", 99, "mike@google.com"));
        people.add(new Person(++PEOPLE_COUNT, "Katy", 18, "whore@pussy.com"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) {
        people.removeIf(p -> p.getId() == id);
    }
}
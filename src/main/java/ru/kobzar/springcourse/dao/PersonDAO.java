package ru.kobzar.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kobzar.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//DAO (data access object) джава класс для взаимодействия с бд (тут мы имитируем бд с помощью листа и создаем методы)
//для создания/получения объекта
@Component
public class PersonDAO {

    final
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //получение всего списка из бд NOW WITH JDBC TEMPLATE
    public List<Person> index() {
        //                                     SQL запрос       наш ранее созданный PersonMapper
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {

//  переписали запрос на получение конкретного пользователя WITH JDBC TEMPLATE
        //тк метод query возвращает лист, тут мы с стрим апи ищем и возвращаем объект персон
        //                                                      вместо вопроса ставим айди из new Object[]{id}
        //                                                                   туть
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
//                                                                  instead of '?' we set person data from get methods
        jdbcTemplate.update("INSERT INTO person VALUES (1,?,?,?)", person.getName(), person.getAge(), person.getEmail());

    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person set name=?, age=?, email=? WHERE id=?",
                //instead of ? we set person data from get methods (easy)
                updatedPerson.getName(), updatedPerson.getAge(),
                //              id from update method args
                updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
//для изменения данных исп метод апдейт
        //                           вместо вопроса подставляем айди из параметров метода delete
        //                                                       тут
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
//
    }
}
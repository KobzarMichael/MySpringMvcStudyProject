package ru.kobzar.springcourse.dao;


import org.springframework.jdbc.core.RowMapper;
import ru.kobzar.springcourse.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
//этот класс необходим для того, чтобы использовать jdbc template далее в запросах к скл не дублировать код
//ТАК КАК ПОЛЯ В ПЕРСОН СОВПАДАЮТ С НАЗВАНИЯМИ ПОЛЕЙ В ТАБЛИЦЕ, ТО МОЖНО НЕ СОЗДАВАТЬ СВОЙ МАППЕР, А ИСПОЛЬЗОВАТЬ ДЕФОЛТНЫЙ СПРИНГОВСКИЙ
//new BeanPropertyRowMapper<>(Person.class)
//ЭТОТ МАППЕР МЫ ИСПОЛЬЗОВАТЬ НЕ БУДЕМ - ОН СОЗДАН ТОЛЬКО ДЛЯ ПРИМЕРА
public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {

        Person person = new Person();

        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));
        return person;
    }
}

package ru.kobzar.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.kobzar.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//DAO (data access object) джава класс для взаимодействия с бд (тут мы имитируем бд с помощью листа и создаем методы)
//для создания/получения объекта
@Component
public class PersonDAO {
    //тут мы просто задаем данные для подключения к бд в качестве статических переменных, чтоб внизу не грузить код
    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "3648";

    //это коннекшн к нашей бд (соединение с jdbc)
    private static Connection connection;

    //инициализируем коннекшн в статическом блоке
    static {
        try {
            //это делаем на всякий случай, чтоб убедиться что драйвер заимпорчен
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            //собственно подключение с помощью метода DriverManager
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //получение всего списка из бд
    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            //Statement - объект, который содержит в себе запрос к бд
            Statement statement = connection.createStatement();
            //для удобства сохраняем синтаксис sql запроса в переменную
            String SQL = "SELECT * FROM Person";
            //statement.executeQuery - передаем в стейтмент sql запрос с пом. метода executeQuery
            //ResultSet - объект, который инкапсулирует результат выполнения запроса
            ResultSet resultSet = statement.executeQuery(SQL);

            //с помощью итератора проходимся по данным в бд
            while(resultSet.next()) {
                Person person = new Person();

                //вытягиваем данные и сохраняем их с помощью методов ниже в новый объект Person
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));
                //добавляем человека из бд в список
                people.add(person);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return people;
    }

    public Person show(int id) {
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
        return null;
    }

    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);

        //сохранение нового пользователя в бд
        try {
            //создаем стейтмент
            Statement statement = connection.createStatement();
            //опять сохраняем синтаксис скл запроса в переменной (в целях обучения)
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
                    "'," + person.getAge() + ",'" + person.getEmail() + "')";

            //с помощью метода executeUpdate сохраняем данные в бд
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void update(int id, Person updatedPerson) {
//        Person personToBeUpdated = show(id);
//
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) {
//        people.removeIf(p -> p.getId() == id);
    }
}
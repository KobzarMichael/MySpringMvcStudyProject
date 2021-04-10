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
            while (resultSet.next()) {
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
        Person person = null;
        try {
            //пишем скл запрос
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE id=?");
            //передаем в него айди
            preparedStatement.setInt(1, id);
            //создаем резалтсет (который инкапсулирует в себе объект из таблицы) и вызываем метод executeQuery() (выполнить запрос скл)
            ResultSet resultSet = preparedStatement.executeQuery();
            //один раз вызываем метод некст (так как сейчас у нас везде айди=1)
            resultSet.next();

            person = new Person();
            //передаем в новый объект персон данные из резалтсета
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return person;
    }

    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);

        //сохранение нового пользователя в бд
        try {
            //создаем стейтмент тут мы используем препеирдстейтмент, чтобы избежать инъекций в скл код
            //ЛУЧШЕ ВСЕГДА ЮЗАТЬ ПРЕПЕИРД СТЕЙТМЕНТ, Т.К. ОН БЫСТРЕЕ (КОД КОМПИЛИРУЕТСЯ ОДИН РАЗ, А ПОТОМ В НЕГО ПРОСТО
            //ДОБАВЛЯЮТСЯ ЗНАЧЕНИЯ) ПЛЮС НЕТ ШАНСА СДЕЛАТЬ ИНЪЕКЦИЮ В КОД
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person VALUES (1,?,?,?)");
            //тут мы добавляем нужные значения в запрос connection.prepareStatement
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            //с помощью метода executeUpdate сохраняем данные в бд
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE person set name=?, age=?, email=? WHERE id=?");

            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM person WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
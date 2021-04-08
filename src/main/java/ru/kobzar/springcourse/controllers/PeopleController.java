package ru.kobzar.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kobzar.springcourse.dao.PersonDAO;
import ru.kobzar.springcourse.models.Person;

import javax.validation.Valid;

/*
Собственно тут контроллер - на него (ну почти) приходит http запрос от клиента и этот господин решает, что в ответ ему
предоставить.
 */
@Controller
@RequestMapping("/people")
public class PeopleController {

    //внедряем объект ДАО с помощью конструктора
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    //гет запрос на отображение списка людей
    @GetMapping()
    public String index(Model model) {
        //модель нужна для передачи в неё нужного аттрибута (чтоб дальше его использовать с th)
        model.addAttribute("people", personDAO.index());
        //возвращаемое значение = наше представление html
        return "people/index";
    }

    //гет запрос на отображение списка людей (обрати внимание на @PathVariable("id") - с пом. этой аннотации мы
    //указываем, что при вводе адреса people/id мы будем отдавать клиенту список наших людей, так же эта аннотация
    //позволяет использовать айди из http запроса как переменную в джава коде (тут мы её передаем в модель)
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //в модель опять же передаем айди, чтобы далее с пом. ДАО найти человека по указанному айди
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    //гет запрос на добавление нового человека (адрес people/new)
    @GetMapping("/new")
    public String newPerson(Model model) {
        //тут в качестве атрибута в модель передаем нового человека с айди "person", чтобы далее его использовать
        // с th на странице с формой создания нового человека
        model.addAttribute("person", new Person());
        return "people/new";
    }

    //пост запрос на добавление человека в бд
    //тут мы получаем данные из формы new, чтобы дальше добавить человека в список (бд)
    @PostMapping()
    //с пом. аннотации @ModelAttribute мы можем получить данные (person) из формы
    //@Valid - проверяет в данном случае поля класса персон на валидность (аннотации валидации прописаны в классе)
    //ПОСЛЕ ОБЪЕКТА С АННОТАЦИЕЙ @Valid ОБЯЗАТЕЛЬНО НУЖЕН АРГУМЕНТ BindingResult bindingResult
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {

        //тут мы собственно проверяем есть ли ошибки валидации после проверки и если есть то по новой кидает
        //клиент на страницу создания, НО УЖЕ С УКАЗАНИЕМ ОШИБОК (за отображение ошибок отвечает уже таймлиф)
        if (bindingResult.hasErrors()) return "people/new";

        personDAO.save(person);
        //после создания пользователя просто возвращаем страницу с общим списком людей
        return "redirect:/people";
    }

    //запрос на апдейт человека
    //в маппинге передаем нужный адрес страницы
    @GetMapping("/{id}/edit")
    //с помощью @PathVariable сохраняем айди из адреса в переменную
    public String edit (Model model, @PathVariable ("id") int id) {
        //передаем человека в модель (чтоб дальше передать в представление)
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }


    // после заполнения формы на апдейт юзается патч запрос, который сохраняет обновленные данные в бд
    @PatchMapping ("/{id}")
    // с пом. аннотации @ModelAttribute мы можем получить данные (person) из формы
    // с @PathVariable мы получаем айди из адреса и сохраняем его в переменную id
    //@Valid - проверяет в данном случае поля класса персон на валидность (аннотации валидации прописаны в классе)
    //ПОСЛЕ ОБЪЕКТА С АННОТАЦИЕЙ @Valid ОБЯЗАТЕЛЬНО НУЖЕН АРГУМЕНТ BindingResult bindingResult
    public String update (@ModelAttribute ("person") @Valid Person person, BindingResult bindingResult,
                          @PathVariable("id") int id) {
        //тут мы собственно проверяем есть ли ошибки валидации после проверки и если есть то по новой кидает
        //клиент на страницу изменения человека, НО УЖЕ С УКАЗАНИЕМ ОШИБОК (за отображение ошибок отвечает уже таймлиф)
        if (bindingResult.hasErrors()) return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete (@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
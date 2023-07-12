package ua.chmil.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.chmil.spring.dao.PersonDAO;
import ua.chmil.spring.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    // Spring Framework внедряє Model. Нам треба в Model передавати об'єк, який мість в собі People, на view
    public String index(Model model) {
        // в цьому методі отримаємо всіх People з DAO і передамо на view
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    // коли ми запустимо застосунок, то такий синтаксис дозволяє помістити в id будь-яке число і це число помістить в аргументи методу
    public String show(@PathVariable("id") int id, Model model) {
        // отримаємо одну Person з DAO і передамо її на відображення у view
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) { // Можна в атрибутах передати Model model і зробити model.addAttribute("person", new Person)
        return "people/new";
    }
    @PostMapping
    public String creat(@ModelAttribute("person") Person person){
        personDAO.save(person);
        return "redirect:/people";
    }
}
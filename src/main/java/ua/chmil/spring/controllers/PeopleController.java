package ua.chmil.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.chmil.spring.dao.PersonDAO;
import ua.chmil.spring.models.Book;
import ua.chmil.spring.models.Person;
import ua.chmil.spring.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
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
    public String show(@PathVariable("id") int id_person, Model model) {
        // отримаємо одну Person з DAO і передамо її на відображення у view
        model.addAttribute("person", personDAO.show(id_person));
        model.addAttribute("books", personDAO.getBookByPersonId(id_person));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) { // Можна в атрибутах передати Model model і зробити model.addAttribute("person", new Person)
        return "people/new";
    }

    @PostMapping
    public String creat(@ModelAttribute("person") @Valid Person person,
                        BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id_person) {
        model.addAttribute("person", personDAO.show(id_person));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id_person) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id_person, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id_person) {
        personDAO.delete(id_person);
        return "redirect:/people";
    }


}
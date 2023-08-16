package ua.chmil.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.chmil.spring.dao.BookDAO;
import ua.chmil.spring.dao.PersonDAO;
import ua.chmil.spring.models.Book;
import ua.chmil.spring.models.Person;


import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    // Spring Framework внедряє Model. Нам треба в Model передавати об'єк, який мість в собі Book, на view
    public String index(Model model) {
        // в цьому методі отримаємо всіх People з DAO і передамо на view
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    // коли ми запустимо застосунок, то такий синтаксис дозволяє помістити в id будь-яке число і це число помістить в аргументи методу
    public String show(@PathVariable("id") int id_book, Model model, @ModelAttribute("person") Person person) {
        // отримаємо одну Person з DAO і передамо її на відображення у view
        model.addAttribute("book", bookDAO.show(id_book));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id_book);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else model.addAttribute("people", personDAO.index());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) { // Можна в атрибутах передати Model model і зробити model.addAttribute("person", new Person)
        return "books/new";
    }

    @PostMapping
    public String creat(@ModelAttribute("book") @Valid Book book,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id_book) {
        model.addAttribute("book", bookDAO.show(id_book));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, BindingResult bindingResult,
                         @PathVariable("id") int id_book) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id_book, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id_book) {
        bookDAO.delete(id_book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id_book){
        bookDAO.release(id_book);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id_book, @ModelAttribute("person") Person selectedPerson){
        bookDAO.assign(id_book, selectedPerson);
        return "redirect:/books/";
    }
}
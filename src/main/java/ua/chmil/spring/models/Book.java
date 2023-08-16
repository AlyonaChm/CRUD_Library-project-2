package ua.chmil.spring.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    public void setIdBook(int id_book) {
        this.id_book = id_book;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    private int id_book;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 1, max = 40, message = "Title should be between 1 and 40 characters")
    private String title;
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 1, max = 40, message = "Author  should be between 1 and 40 characters")
    private String author;
    @Min(value = 1500, message = "Year of published should not be negative")
    private int publishedYear;

    public Book(String title, String author, int publishedYear) {
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
    }
    public Book() {
    }

    public int getId_book() {
        return id_book;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }
}
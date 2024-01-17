package ru.geekbrains.springboothomework3.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.springboothomework3.model.Book;
import ru.geekbrains.springboothomework3.repository.BookRepository;

import java.util.NoSuchElementException;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getById(long id) {
        return bookRepository.getBookById(id);
    }

    public Book insertBook(Book book) {
        bookRepository.insertBook(book);
        return book;
    }

    public void delete(long id) {
        if (bookRepository.getBookById(id) == null) {
            throw new NoSuchElementException();
        }
        bookRepository.delete(id);
    }
}

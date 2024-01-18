package ru.geekbrains.springboothomework3.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.springboothomework3.api.request.BookRequest;
import ru.geekbrains.springboothomework3.model.Book;
import ru.geekbrains.springboothomework3.repository.BookRepository;

import java.util.NoSuchElementException;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getById(long id) throws NoSuchElementException{
        Book book = bookRepository.getBookById(id);
        if (book == null) {
            throw new NoSuchElementException();
        }
        return book;
    }

    public Book insertBook(BookRequest request) throws NullPointerException{
        if (request.getName() == null) {
            throw new NullPointerException();
        }
        Book book = new Book(request.getName());
        bookRepository.insertBook(book);
        return book;
    }

    public void delete(long id) throws NoSuchElementException{
        if (bookRepository.getBookById(id) == null) {
            throw new NoSuchElementException();
        }
        bookRepository.delete(id);
    }
}

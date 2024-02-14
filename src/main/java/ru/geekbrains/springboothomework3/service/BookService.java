package ru.geekbrains.springboothomework3.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.springboothomework3.api.request.BookRequest;
import ru.geekbrains.springboothomework3.model.entity.BookEntity;
import ru.geekbrains.springboothomework3.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity findById(Long id) {
        Optional<BookEntity> result = bookRepository.findById(id);
        return result.orElseThrow();
    }

    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }

    public BookEntity save(BookRequest request) {
        BookEntity book = new BookEntity(request.getName());
        bookRepository.save(book);
        return book;
    }

    public void deleteById(Long id) throws NoSuchElementException{
        try {
            findById(id);
            bookRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Книга не найдена");
        }
    }
}

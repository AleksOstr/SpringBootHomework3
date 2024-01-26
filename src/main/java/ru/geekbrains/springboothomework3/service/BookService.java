package ru.geekbrains.springboothomework3.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.springboothomework3.model.entity.BookEntity;
import ru.geekbrains.springboothomework3.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<BookEntity> findById(Long id) {
        return bookRepository.findById(id);
    }

    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }

    public BookEntity save(BookEntity entity) {
        return bookRepository.save(entity);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}

package ru.geekbrains.springboothomework3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.springboothomework3.model.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}

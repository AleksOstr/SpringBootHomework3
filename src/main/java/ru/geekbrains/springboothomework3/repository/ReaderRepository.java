package ru.geekbrains.springboothomework3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;

public interface ReaderRepository extends JpaRepository<ReaderEntity, Long> {
}

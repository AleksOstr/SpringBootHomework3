package ru.geekbrains.springboothomework3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;

@Repository
public interface ReaderRepository extends JpaRepository<ReaderEntity, Long> {
}

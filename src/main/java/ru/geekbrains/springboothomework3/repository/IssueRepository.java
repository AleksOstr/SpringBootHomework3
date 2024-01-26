package ru.geekbrains.springboothomework3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.springboothomework3.model.entity.IssueEntity;

public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
}

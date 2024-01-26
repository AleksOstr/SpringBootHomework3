package ru.geekbrains.springboothomework3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.springboothomework3.model.entity.IssueEntity;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
}

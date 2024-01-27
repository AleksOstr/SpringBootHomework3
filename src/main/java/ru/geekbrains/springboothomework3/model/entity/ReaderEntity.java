package ru.geekbrains.springboothomework3.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "reader")
@Data
public class ReaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany
    @Column(name = "readerIssues")
    private List<IssueEntity> readerIssues;
}

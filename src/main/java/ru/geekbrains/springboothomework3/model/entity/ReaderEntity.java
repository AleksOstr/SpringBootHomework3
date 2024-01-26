package ru.geekbrains.springboothomework3.model.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "reader")
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

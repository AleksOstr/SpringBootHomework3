package ru.geekbrains.springboothomework3.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "reader")
@Data
@NoArgsConstructor
public class ReaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "readerIssues")
    private List<IssueEntity> readerIssues;

    public ReaderEntity(String name) {
        this.name = name;
    }
}

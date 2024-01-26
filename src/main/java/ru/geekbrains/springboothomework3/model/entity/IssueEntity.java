package ru.geekbrains.springboothomework3.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "issue")
public class IssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "bookId")
    private Long bookId;
    @Column(name = "readerId")
    private Long readerId;
    @Column(name = "readerName")
    private String readerName;
    @Column(name = "bookName")
    private String bookName;
    @Column(name = "issuedAt")
    private LocalDateTime issuedAt;
    @Column(name = "returnedAt")
    private LocalDateTime returnedAt;

    @ManyToOne
    private ReaderEntity reader;
}

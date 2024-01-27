package ru.geekbrains.springboothomework3.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "issue")
@Data
public class IssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bookId")
    private Long bookId;

    private String bookName;

    @Column(name = "readerId")
    private Long readerId;

    private String readerName;

    @Column(name = "issuedAt")
    @CreationTimestamp
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDateTime issuedAt;

    @Column(name = "returnedAt")
    private LocalDateTime returnedAt;



}

package ru.geekbrains.springboothomework3.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
// @Entity
public class Issue {

  public static long sequence = 1L;

  private final long id;
  private final long bookId;
  private final long readerId;
  private final String bookName;
  private final String readerName;

  /**
   * Дата выдачи
   */
  private final LocalDateTime issuedAt;

  private LocalDateTime returnedAt;

  public Issue(long bookId, long readerId, String bookName, String readerName) {
    this.id = sequence++;
    this.bookId = bookId;
    this.readerId = readerId;
    this.bookName = bookName;
    this.readerName = readerName;
    this.issuedAt = LocalDateTime.now();
  }

}

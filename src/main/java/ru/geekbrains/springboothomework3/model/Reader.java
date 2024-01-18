package ru.geekbrains.springboothomework3.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Reader {

  public static long sequence = 1L;

  private final long id;
  private final String name;
  private final List<Issue> readerIssues;

  public Reader(String name) {
    this(sequence++, name, new ArrayList<>());
  }

}

package ru.geekbrains.springboothomework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.geekbrains.springboothomework3.api.request.IssueRequest;
import ru.geekbrains.springboothomework3.model.Issue;
import ru.geekbrains.springboothomework3.repository.BookRepository;
import ru.geekbrains.springboothomework3.repository.IssueRepository;
import ru.geekbrains.springboothomework3.repository.ReaderRepository;

import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IssuerService {

  // спринг это все заинжектит
  private final BookRepository bookRepository;
  private final ReaderRepository readerRepository;
  private final IssueRepository issueRepository;

  @Value("${application.max-allowed-books:1}")
  private int maxAllowedBooks;

  public Issue issue(IssueRequest request) throws OperationNotSupportedException, NoSuchElementException {
    if (bookRepository.getBookById(request.getBookId()) == null) {
      throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
    }
    if (readerRepository.getReaderById(request.getReaderId()) == null) {
      throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
    }
    if (checkReaderForIssues(request.getReaderId())) {
      throw new OperationNotSupportedException("Превышен лимит выдачи");
    }


    Issue issue = new Issue(request.getBookId(), request.getReaderId());
    readerRepository.getReaderById(request.getReaderId()).getReaderIssues().add(issue);
    issueRepository.save(issue);
    return issue;
  }

  public Issue getIssueById(long id) throws NoSuchElementException {
    Issue issue = issueRepository.getById(id);
    if (issue == null) {
      throw new NoSuchElementException();
    }
    return issue;
  }

  private boolean checkReaderForIssues(long readerId) {
    List<Issue> issues = issueRepository.getIssues();
    List<Issue> readerIssues = issues.stream()
            .filter(it -> Objects.equals(it.getReaderId(), readerId))
            .toList();
    return readerIssues.size() >= maxAllowedBooks;
  }

}

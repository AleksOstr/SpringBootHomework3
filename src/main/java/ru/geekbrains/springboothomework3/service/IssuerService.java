package ru.geekbrains.springboothomework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.geekbrains.springboothomework3.api.request.IssueRequest;
import ru.geekbrains.springboothomework3.model.Book;
import ru.geekbrains.springboothomework3.model.Issue;
import ru.geekbrains.springboothomework3.model.Reader;
import ru.geekbrains.springboothomework3.repository.BookRepository;
import ru.geekbrains.springboothomework3.repository.IssueRepository;
import ru.geekbrains.springboothomework3.repository.ReaderRepository;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
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
        Book book = bookRepository.getBookById(request.getBookId());
        if (book == null) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
        }
        Reader reader = readerRepository.getReaderById(request.getReaderId());
        if (reader == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
        }
        if (checkReaderForOpenedIssues(request.getReaderId())) {
            throw new OperationNotSupportedException("Превышен лимит выдачи");
        }


        Issue issue = new Issue(request.getBookId(), request.getReaderId(), book.getName(), reader.getName());
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

    public List<Issue> getIssues() {
        return issueRepository.getIssues();
    }

    public Issue closeIssue(long id) throws NoSuchElementException, OperationNotSupportedException {
        Issue issue = issueRepository.getById(id);
        if (issue == null) {
            throw new NoSuchElementException();
        }
        if (issue.getReturnedAt() != null) {
            throw new OperationNotSupportedException();
        }
        LocalDateTime returnTime = LocalDateTime.now();
        issue.setReturnedAt(returnTime);
        return issue;
    }

    private boolean checkReaderForOpenedIssues(long readerId) {
        List<Issue> issues = issueRepository.getIssues();
        List<Issue> readerIssues = issues.stream()
                .filter(it -> Objects.equals(it.getReaderId(), readerId))
                .filter(it -> it.getReturnedAt() == null)
                .toList();
        return readerIssues.size() >= maxAllowedBooks;
    }

}

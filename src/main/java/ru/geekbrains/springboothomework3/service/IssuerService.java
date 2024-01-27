package ru.geekbrains.springboothomework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.geekbrains.springboothomework3.model.entity.BookEntity;
import ru.geekbrains.springboothomework3.model.entity.IssueEntity;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;
import ru.geekbrains.springboothomework3.repository.BookRepository;
import ru.geekbrains.springboothomework3.repository.IssueRepository;
import ru.geekbrains.springboothomework3.repository.ReaderRepository;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssuerService {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    @Value("${application.max-allowed-books:1}")
    private int maxAllowedBooks;

    public IssueEntity save(IssueEntity entity) throws NoSuchElementException, OperationNotSupportedException {
        BookEntity book = bookRepository.findById(entity.getBookId()).orElseThrow();
        ReaderEntity reader = readerRepository.findById(entity.getReaderId()).orElseThrow();
        if (!checkReaderForOpenedIssues(entity.getReaderId())) {
            entity.setBookName(book.getName());
            entity.setReaderName(reader.getName());
            reader.getReaderIssues().add(entity);
            return issueRepository.save(entity);
        } else {
            throw new OperationNotSupportedException("Превышен лимит выдачи");
        }
    }

    public List<IssueEntity> findAll() throws NoSuchElementException{
        List<IssueEntity> result = issueRepository.findAll();
        if (result.isEmpty()) {
            throw new NoSuchElementException("Записей не найдено");
        } else {
            return result;
        }
    }

    public List<IssueEntity> findAllByReaderId(long readerId) {
        List<IssueEntity> result = issueRepository.findAllByReaderId(readerId);
        if (result.isEmpty()) {
            throw new NoSuchElementException("Записей не найдено");
        } else {
            return result;
        }
    }

    public IssueEntity findById(Long id) throws NoSuchElementException{
        Optional<IssueEntity> result = issueRepository.findById(id);
        return result.orElseThrow();
    }

    public IssueEntity closeIssue(Long id) throws NoSuchElementException{
        IssueEntity issue = findById(id);
        issue.setReturnedAt(LocalDateTime.now());
        return issue;
    }


    private boolean checkReaderForOpenedIssues(Long id) {
        List<IssueEntity> issues = issueRepository.findAllByReaderIdAndReturnedAtNull(id);
        return issues.size() >= maxAllowedBooks;
    }

}

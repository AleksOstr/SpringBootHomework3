package ru.geekbrains.springboothomework3.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.springboothomework3.model.entity.IssueEntity;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;
import ru.geekbrains.springboothomework3.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository repository) {
        this.readerRepository = repository;
    }

    public ReaderEntity findById(Long id) throws NoSuchElementException {
        Optional<ReaderEntity> result = readerRepository.findById(id);
        return result.orElseThrow();
    }

    public List<ReaderEntity> findAll() {
        return readerRepository.findAll();
    }

    public List<IssueEntity> getOpenedReaderIssues(ReaderEntity reader) {
        return reader.getReaderIssues().stream().filter(it -> it.getReturnedAt() == null).toList();
    }

    public ReaderEntity save(ReaderEntity entity) {
        return readerRepository.save(entity);
    }

    public void deleteById(Long id) throws NoSuchElementException{
        try {
            findById(id);
            readerRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Чиатель не найден");
        }
    }
}

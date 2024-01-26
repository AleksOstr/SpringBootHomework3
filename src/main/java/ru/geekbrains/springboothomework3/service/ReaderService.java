package ru.geekbrains.springboothomework3.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.springboothomework3.model.entity.ReaderEntity;
import ru.geekbrains.springboothomework3.repository.ReaderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    public ReaderService(ReaderRepository repository) {
        this.readerRepository = repository;
    }

    public Optional<ReaderEntity> findById(Long id) {
        return readerRepository.findById(id);
    }

    public List<ReaderEntity> findAll() {
        return readerRepository.findAll();
    }

    public ReaderEntity save(ReaderEntity entity) {
        return readerRepository.save(entity);
    }

    public void deleteById(Long id) {
        readerRepository.deleteById(id);
    }
}

package com.bank.ms_clients.application.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.bank.ms_clients.application.exception.DataNotFound;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractService<T, ID, DTO, R extends JpaRepository<T, ID>> {
    protected final R repository;
    protected final Logger log;
    private final String className;

    @Transactional
    public DTO save(DTO dto) {
        T entity = preSave(mapToEntity(dto));
        T savedEntity = repository.save(entity);
        return postSave(mapToDTO(savedEntity));
    }

    @Transactional
    public DTO update(ID id, DTO dto) {
        if (!repository.existsById(id)) {
            throw new DataNotFound(className + " with ID " + id + " not found");
        }
        T entity = preUpdate(mapToEntity(dto));
        T updatedEntity = repository.save(entity);
        return postUpdate(mapToDTO(updatedEntity));
    }

    public DTO read(ID id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new DataNotFound(className + " with ID " + id + " not found"));
    }

    public List<DTO> readAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(ID id) {
        if (!repository.existsById(id)) {
            throw new DataNotFound(className + " with ID " + id + " not found");
        }
        repository.deleteById(preDelete(id));
        postDelete(id);
    }

    public abstract DTO mapToDTO(T entity);

    public abstract T mapToEntity(DTO dto);

    // Hooks for customization
    public T preSave(T entity) {
        return entity;
    }

    public DTO postSave(DTO dto) {
        return dto;
    }

    public T preUpdate(T entity) {
        return entity;
    }

    public DTO postUpdate(DTO dto) {
        return dto;
    }

    public ID preDelete(ID id) {
        return id;
    }

    public void postDelete(ID id) {
    }

}

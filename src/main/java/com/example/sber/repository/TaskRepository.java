package com.example.sber.repository;
import com.example.sber.model.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository{

    Optional<Task> findById(Long id);

    List<Task> findAll();
}

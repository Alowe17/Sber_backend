package com.example.sber.repository;
import com.example.sber.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    Optional<Task> findById(Long id);

    List<Task> findAll();
}

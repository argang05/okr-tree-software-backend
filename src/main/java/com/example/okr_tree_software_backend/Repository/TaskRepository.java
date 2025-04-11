package com.example.okr_tree_software_backend.Repository;

import com.example.okr_tree_software_backend.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByObjective_Id(Long objectiveId);
}
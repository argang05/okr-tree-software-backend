package com.example.okr_tree_software_backend.Controllers;

import com.example.okr_tree_software_backend.DataTransferObjects.TaskDTO;
import com.example.okr_tree_software_backend.Entity.Task;
import com.example.okr_tree_software_backend.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/{objectiveId}")
    public ResponseEntity<TaskDTO> addTask(@PathVariable Long objectiveId, @RequestBody Task task) {
        Task savedTask = taskService.addTaskToObjective(objectiveId, task);
        TaskDTO dto = taskService.mapToTaskDTO(savedTask);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/objective/{objectiveId}")
    public List<TaskDTO> getTasks(@PathVariable Long objectiveId) {
        return taskService.getTasksForObjective(objectiveId);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(taskId, task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}

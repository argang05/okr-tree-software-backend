package com.example.okr_tree_software_backend.Services;

import com.example.okr_tree_software_backend.DataTransferObjects.ObjectiveDTO;
import com.example.okr_tree_software_backend.DataTransferObjects.TaskDTO;
import com.example.okr_tree_software_backend.Entity.Objective;
import com.example.okr_tree_software_backend.Entity.Task;
import com.example.okr_tree_software_backend.Repository.ObjectiveRepository;
import com.example.okr_tree_software_backend.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectiveRepository objectiveRepository;

    public Task addTaskToObjective(Long objectiveId, Task task) {
        Objective objective = objectiveRepository.findById(objectiveId)
                .orElseThrow(() -> new RuntimeException("Objective not found"));
        task.setObjective(objective);
        return taskRepository.save(task);
    }

    public TaskDTO mapToTaskDTOWithoutObj(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setAssumption(task.getAssumption());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        dto.setAssumption(task.getAssumption());
        dto.setAssignedTo(task.getAssignedTo());
        dto.setProgressPercentage(task.getProgressPercentage());
        return dto;
    }
    public TaskDTO mapToTaskDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        dto.setAssumption(task.getAssumption());
        dto.setAssignedTo(task.getAssignedTo());
        dto.setProgressPercentage(task.getProgressPercentage());
// Map objective using ObjectiveDTO (which shouldn't have parent/children nesting)
        if (task.getObjective() != null) {
            ObjectiveDTO objectiveDTO = new ObjectiveDTO();
            objectiveDTO.setId(task.getObjective().getId());
            objectiveDTO.setTitle(task.getObjective().getTitle());
            objectiveDTO.setDescription(task.getObjective().getDescription());
            objectiveDTO.setLevel(task.getObjective().getLevel());
            objectiveDTO.setTreeLevel(task.getObjective().getTreeLevel());
            objectiveDTO.setProgressPercentage(task.getObjective().getProgressPercentage());
            dto.setObjective(objectiveDTO);
        }

        return dto;
    }

    public List<TaskDTO> getTasksForObjective(Long objectiveId) {
        return taskRepository.findByObjective_Id(objectiveId)
                .stream()
                .map(this::mapToTaskDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO updateTask(Long taskId, Task updatedTask) {
        Task existing = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        existing.setTitle(updatedTask.getTitle());
        existing.setStatus(updatedTask.getStatus());
        existing.setDescription(updatedTask.getDescription());
        existing.setAssignedTo(updatedTask.getAssignedTo());
        existing.setAssumption(updatedTask.getAssumption());
        existing.setDueDate(updatedTask.getDueDate());
        existing.setProgressPercentage(updatedTask.getProgressPercentage());
        return mapToTaskDTO(taskRepository.save(existing));
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}

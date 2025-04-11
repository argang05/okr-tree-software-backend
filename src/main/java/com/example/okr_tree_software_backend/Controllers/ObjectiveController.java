package com.example.okr_tree_software_backend.Controllers;

import com.example.okr_tree_software_backend.DataTransferObjects.ObjectiveDTO;
import com.example.okr_tree_software_backend.Entity.Objective;
import com.example.okr_tree_software_backend.Services.ObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/objectives")
public class ObjectiveController {
    @Autowired
    private ObjectiveService objectiveService;

    @PostMapping
    public ResponseEntity<ObjectiveDTO> createObjective(
            @RequestParam(required = false) Long parentId,
            @RequestBody Objective objective) {
        return new ResponseEntity<>(objectiveService.createObjective(objective, parentId), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ObjectiveDTO> getAllObjectives() {
        return objectiveService.getAllObjectives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObjectiveDTO> getObjective(@PathVariable Long id) {
        return ResponseEntity.ok(objectiveService.getObjectiveDTO(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectiveDTO> updateObjective(
            @PathVariable Long id,
            @RequestBody Objective objective) {
        return ResponseEntity.ok(objectiveService.updateObjective(id, objective));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObjective(@PathVariable Long id) {
        objectiveService.deleteObjective(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tree/{id}")
    public ResponseEntity<ObjectiveDTO> getObjectiveTree(@PathVariable Long id) {
        return ResponseEntity.ok(objectiveService.getObjectiveTreeDTO(id));
    }
}

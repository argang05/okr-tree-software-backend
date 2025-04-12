package com.example.okr_tree_software_backend.Services;

import com.example.okr_tree_software_backend.DataTransferObjects.ObjectiveDTO;
import com.example.okr_tree_software_backend.Entity.Objective;
import com.example.okr_tree_software_backend.Repository.ObjectiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObjectiveService {
    @Autowired
    private ObjectiveRepository objectiveRepository;

    public ObjectiveDTO getObjectiveTreeDTO(Long rootId) {
        Objective root = objectiveRepository.findById(rootId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        populateChildren(root); // build the entity tree

        return convertToDTORecursive(root);
    }

    private ObjectiveDTO convertToDTORecursive(Objective objective) {
        ObjectiveDTO dto = new ObjectiveDTO(objective);
        if (objective.getChildren() != null) {
            for (Objective child : objective.getChildren()) {
                dto.getChildren().add(convertToDTORecursive(child));
            }
        }
        return dto;
    }


    public ObjectiveDTO createObjective(Objective objective, Long parentId) {
        if (parentId != null) {
            Objective parent = objectiveRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("Parent not found"));
            objective.setParent(parent);
            objective.setTreeLevel(parent.getTreeLevel() + 1);
        } else {
            objective.setTreeLevel(0); // Root level
        }
        Objective savedObjective = objectiveRepository.save(objective);
        return new ObjectiveDTO(savedObjective);
    }

    public List<ObjectiveDTO> getAllObjectives() {
        List<Objective> objectives = objectiveRepository.findAll();
        return objectives.stream() .map(this::convertToDTO) .collect(Collectors.toList());
    }

    private ObjectiveDTO convertToDTO(Objective objective) {
        ObjectiveDTO dto = new ObjectiveDTO();
        dto.setId(objective.getId());
        dto.setTitle(objective.getTitle());
        dto.setDescription(objective.getDescription());
        dto.setLevel(objective.getLevel());
        dto.setTreeLevel(objective.getTreeLevel());
        dto.setProgressPercentage(objective.getProgressPercentage());
// Optional: if you want to include shallow children (without nested subtrees)
        if (objective.getChildren() != null) {
            List<ObjectiveDTO> childDTOs = objective.getChildren().stream()
                    .map(child -> {
                        ObjectiveDTO childDTO = new ObjectiveDTO();
                        childDTO.setId(child.getId());
                        childDTO.setTitle(child.getTitle());
                        childDTO.setDescription(child.getDescription());
                        childDTO.setLevel(child.getLevel());
                        childDTO.setTreeLevel(child.getTreeLevel());
                        childDTO.setProgressPercentage(child.getProgressPercentage());
                        childDTO.setDueDate(child.getDueDate());
                        return childDTO;
                    }).collect(Collectors.toList());
            dto.setChildren(childDTOs);
        }
        return dto;
    }
    public ObjectiveDTO getObjectiveDTO(Long id) {
        return convertToDTO(getObjective(id));
    }

    public Objective getObjective(Long id) {
        return objectiveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objective not found"));
    }

    public ObjectiveDTO updateObjective(Long id, Objective updated) {
        Objective existing = getObjective(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setLevel(updated.getLevel());
        existing.setProgressPercentage(updated.getProgressPercentage());
        existing.setDueDate(updated.getDueDate());
        return convertToDTO(objectiveRepository.save(existing));
    }

    public void deleteObjective(Long id) {
        objectiveRepository.deleteById(id);
    }

    private void populateChildren(Objective node) {
        List<Objective> children = objectiveRepository.findByParent_Id(node.getId());
        node.setChildren(children);
        for (Objective child : children) {
            populateChildren(child); // DFS
        }
    }

    public List<ObjectiveDTO> getAllObjectiveTrees() {
        List<Objective> rootObjectives = objectiveRepository.findByTreeLevelAndParentIsNull(0);
        List<ObjectiveDTO> trees = new ArrayList<>();

        for (Objective root : rootObjectives) {
            populateChildren(root);
            trees.add(convertToDTORecursive(root));
        }

        return trees;
    }
}

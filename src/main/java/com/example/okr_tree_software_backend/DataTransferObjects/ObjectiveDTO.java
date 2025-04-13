package com.example.okr_tree_software_backend.DataTransferObjects;

import com.example.okr_tree_software_backend.Entity.Objective;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ObjectiveDTO {
    private Long id;
    private String title;
    private String description;
    private String level;
    private int treeLevel;
    private double progressPercentage;
    private Long parentId;
    private List<ObjectiveDTO> children = new ArrayList<>();
    private LocalDate dueDate;
    private String assumption;



    // Default constructor
    public ObjectiveDTO() {}

    // Constructor to map from entity
    public ObjectiveDTO(Objective objective) {
        this.id = objective.getId();
        this.title = objective.getTitle();
        this.description = objective.getDescription();
        this.level = objective.getLevel();
        this.treeLevel = objective.getTreeLevel();
        this.progressPercentage = objective.getProgressPercentage();
        this.parentId = objective.getParent() != null ? objective.getParent().getId() : null;
        this.dueDate = objective.getDueDate();
        this.assumption = objective.getAssumption();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLevel() {
        return level;
    }

    public int getTreeLevel() {
        return treeLevel;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public Long getParentId() {
        return parentId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssumption() {
        return assumption;
    }

    public void setAssumption(String assumption) {
        this.assumption = assumption;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setTreeLevel(int treeLevel) {
        this.treeLevel = treeLevel;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<ObjectiveDTO> getChildren() { return children; }

    public void setChildren(List<ObjectiveDTO> children) { this.children = children; }
}

package com.example.okr_tree_software_backend.Entity;

import jakarta.persistence.*;
import com.example.okr_tree_software_backend.Entity.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Objective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String level; // COMPANY, DEPARTMENT, TEAM, INDIVIDUAL
    private int treeLevel; // Depth in the tree
    private double progressPercentage;
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Objective parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Objective> children = new ArrayList<>();

    @OneToMany(mappedBy = "objective", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(int treeLevel) {
        this.treeLevel = treeLevel;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public Objective getParent() {
        return parent;
    }

    public void setParent(Objective parent) {
        this.parent = parent;
    }

    public List<Objective> getChildren() {
        return children;
    }

    public void setChildren(List<Objective> children) {
        this.children = children;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

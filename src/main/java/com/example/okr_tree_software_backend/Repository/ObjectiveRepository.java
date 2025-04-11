package com.example.okr_tree_software_backend.Repository;

import com.example.okr_tree_software_backend.Entity.Objective;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {
    List<Objective> findByParent_Id(Long parentId);
    List<Objective> findByTreeLevelAndParentIsNull(int treeLevel);
    List<Objective> findByParent(Objective parent);
}

package com.example.okr_tree_software_backend.Repository;

import com.example.okr_tree_software_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmpId(String empId);
    Optional<User> findByEmail(String email);
    List<User> findByEmpIdIn(List<String> empIds);
}


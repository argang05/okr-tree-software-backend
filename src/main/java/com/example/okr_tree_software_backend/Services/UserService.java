package com.example.okr_tree_software_backend.Services;

import com.example.okr_tree_software_backend.DataTransferObjects.TaskDTO;
import com.example.okr_tree_software_backend.Entity.Task;
import com.example.okr_tree_software_backend.Entity.User;
import com.example.okr_tree_software_backend.Repository.TaskRepository;
import com.example.okr_tree_software_backend.Repository.UserRepository;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final List<String> ADMIN_IDS = List.of("100607", "100171", "100413", "100444", "100447", "100529", "100408");
    private final String JWT_SECRET = "8f3Q$yH1%zM@rLd5t!F#9jXp*N6AoU2wVk7gCvBqWE4TYxOsZJm";

    public User registerSingleUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(ADMIN_IDS.contains(user.getEmpId()) ? "admin" : "user");
        return userRepository.save(user);
    }

    public List<User> registerMultipleUsers(List<User> users) {
        for (User user : users) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(ADMIN_IDS.contains(user.getEmpId()) ? "admin" : "user");
        }
        return userRepository.saveAll(users);
    }

    public Map<String, Object> login(String empId, String rawPassword, HttpServletResponse response) {
        User user = userRepository.findByEmpId(empId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) throw new RuntimeException("Invalid credentials");

        String jwt = Jwts.builder()
                .setSubject(user.getEmpId())
                .claim("role", user.getRole())
                .header() // Define custom header for the JWT.
                .empty() // Start with an empty header.
                .add("typ", "JWT") // Add the type as JWT to the header.
                .and() // Return to the main builder after modifying the header.
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()))
                .compact();

        Cookie cookie = new Cookie("token", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        Map<String, Object> result = new HashMap<>();
        result.put("token", jwt);
        result.put("user", user);
        return result;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByEmpIds(List<String> empIds) {
        return userRepository.findByEmpIdIn(empIds);
    }

    public User updateUser(String empId, User updatedUser) {
        User user = userRepository.findByEmpId(empId).orElseThrow();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<TaskDTO> getTasksForUser(String empId) {
        return taskRepository.
                findAll().
                stream() .
                filter(
                        task -> task.getAssignedTo() != null
                                &&
                                task.getAssignedTo().contains(empId))
                .map(taskService::mapToTaskDTOWithoutObj) .collect(Collectors.toList()); }
}

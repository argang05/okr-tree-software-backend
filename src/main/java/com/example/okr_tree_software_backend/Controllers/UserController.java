package com.example.okr_tree_software_backend.Controllers;

import com.example.okr_tree_software_backend.DataTransferObjects.LoginRequestDTO;
import com.example.okr_tree_software_backend.DataTransferObjects.TaskDTO;
import com.example.okr_tree_software_backend.Entity.Task;
import com.example.okr_tree_software_backend.Entity.User;
import com.example.okr_tree_software_backend.Services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerSingleUser(user);
    }

    @PostMapping("/register-multiple")
    public List<User> registerMultiple(@RequestBody List<User> users) {
        return userService.registerMultipleUsers(users);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
        return userService.login(loginRequest.getEmpId(), loginRequest.getPassword(), response);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/by-empids")
    public List<User> getUsersByEmpIds(@RequestBody List<String> empIds) {
        return userService.getUsersByEmpIds(empIds);
    }

    @PutMapping("/{empId}")
    public User updateUser(@PathVariable String empId, @RequestBody User updatedUser) {

        return userService.updateUser(empId, updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{empId}/tasks")
    public List<TaskDTO> getTasksForUser(@PathVariable String empId) {
        return userService.getTasksForUser(empId);
    }

    @GetMapping("/empid-name-map")
    public Map<String, String> getEmpIdNameMap() {
        return userService.getEmpIdNameMap();
    }
}

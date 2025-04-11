package com.example.okr_tree_software_backend.DataTransferObjects;

public class LoginRequestDTO {
    private String empId;
    private String password;

    // Getters & Setters
    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

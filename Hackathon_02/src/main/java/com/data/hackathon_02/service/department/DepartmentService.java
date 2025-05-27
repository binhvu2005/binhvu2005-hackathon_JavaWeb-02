package com.data.hackathon_02.service.department;

import com.data.hackathon_02.model.Department;

import java.util.List;

public interface DepartmentService {
    int getDepartmentTotalPage(int pageSize);
    List<Department> getAllDepartmentsByPage(int pageIndex, int pageSize);
    void addDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartment(int departmentId);
    Department getDepartmentById(int departmentId);
    List<Department> getAllDepartmentsByName(String departmentName);
    List<Department> getAllDepartments();
}

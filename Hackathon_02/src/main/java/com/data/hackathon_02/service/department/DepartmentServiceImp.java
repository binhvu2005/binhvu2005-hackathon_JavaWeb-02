package com.data.hackathon_02.service.department;

import com.data.hackathon_02.model.Department;
import com.data.hackathon_02.repository.department.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImp implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    public DepartmentServiceImp(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public int getDepartmentTotalPage(int pageSize) {
        return departmentRepository.getDepartmentTotalPage(pageSize);
    }

    @Override
    public List<Department> getAllDepartmentsByPage(int pageIndex, int pageSize) {
        return departmentRepository.getAllDepartmentsByPage(pageIndex, pageSize);
    }

    @Override
    public void addDepartment(Department department) {
        departmentRepository.addDepartment(department);
    }

    @Override
    public void updateDepartment(Department department) {
        departmentRepository.updateDepartment(department);
    }

    @Override
    public void deleteDepartment(int departmentId) {
        departmentRepository.deleteDepartment(departmentId);
    }

    @Override
    public Department getDepartmentById(int departmentId) {
        return departmentRepository.getDepartmentById(departmentId);
    }

    @Override
    public List<Department> getAllDepartmentsByName(String departmentName) {
        return departmentRepository.getAllDepartmentsByName(departmentName);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }
}

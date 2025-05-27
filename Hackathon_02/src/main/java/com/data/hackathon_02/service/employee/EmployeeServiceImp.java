package com.data.hackathon_02.service.employee;

import com.data.hackathon_02.Dto.EmployeeDTO;
import com.data.hackathon_02.model.Employee;
import com.data.hackathon_02.repository.employee.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService{
    private final EmployeeRepository employeeRepository;
    public EmployeeServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public int getEmployeeTotalPage(int dep_id ,int pageSize) {
        return employeeRepository.getEmployeeTotalPage(dep_id, pageSize);
    }

    @Override
    public List<Employee> getAllEmployeesByPage(int dep_id ,int pageIndex, int pageSize) {
        return employeeRepository.getAllEmployeesByPage(dep_id, pageIndex, pageSize);
    }

    @Override
    public void addEmployee(@Valid EmployeeDTO employee) {
        employeeRepository.addEmployee(employee);
    }

    @Override
    public void updateEmployee(@Valid EmployeeDTO employee) {
        employeeRepository.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteEmployee(employeeId);
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        return employeeRepository.getEmployeeById(employeeId);
    }

    @Override
    public List<Employee> getAllEmployeesByName(String employeeName) {
        return employeeRepository.getAllEmployeesByName(employeeName);
    }
}

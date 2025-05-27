package com.data.hackathon_02.repository.employee;

import com.data.hackathon_02.Dto.EmployeeDTO;
import com.data.hackathon_02.model.Employee;

import javax.validation.Valid;
import java.util.List;

public interface EmployeeRepository {
    int getEmployeeTotalPage(int dep_id ,int pageSize);
    List<Employee> getAllEmployeesByPage(int dep_id ,int pageIndex, int pageSize);
    void addEmployee(@Valid EmployeeDTO employee);
    void updateEmployee(@Valid EmployeeDTO employee);
    void deleteEmployee(int employeeId);
    Employee getEmployeeById(int employeeId);
    List<Employee> getAllEmployeesByName(String employeeName);
    boolean checkEmailExists(String email);
}

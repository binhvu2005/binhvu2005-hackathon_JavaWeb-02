package com.data.hackathon_02.repository.employee;

import com.data.hackathon_02.Dto.EmployeeDTO;
import com.data.hackathon_02.model.Employee;
import com.data.hackathon_02.utils.ConnectionDB;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Repository
public class EmployeeRepositoryImp implements EmployeeRepository{

    @Override
    public int getEmployeeTotalPage(int dep_id,int pageSize) {
        Connection connection = ConnectionDB.openConnection();
        int totalPage = 0;
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL get_employee_total_page(?,?)");
            preparedStatement.setInt(1, dep_id);
            preparedStatement.setInt(2, pageSize);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalPage = resultSet.getInt("total_page");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, null);
        }
        return totalPage;
    }

    @Override
    public List<Employee> getAllEmployeesByPage(int dep_id ,int pageIndex, int pageSize) {
        Connection connection = ConnectionDB.openConnection();
        List<Employee> employees = new ArrayList<>();
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL get_all_employees_by_page(?,?, ?)");
            preparedStatement.setInt(1, dep_id);
            preparedStatement.setInt(2, pageIndex);
            preparedStatement.setInt(3, pageSize);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Employee employee = new Employee();
                employee.setEmployee_id(resultSet.getInt("employee_id"));
                employee.setEmployee_name(resultSet.getString("full_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setPhone_number(resultSet.getString("phone_number"));
                employee.setAvatar_url(resultSet.getString("avatar_url"));
                employee.setStatus(resultSet.getBoolean("status"));
                employee.setCreated_at(resultSet.getTimestamp("created_at").toLocalDateTime());
                employee.setDepartment_id(resultSet.getInt("department_id"));
                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, null);
        }
        return employees;
    }

    @Override
    public void addEmployee(@Valid EmployeeDTO employee) {
        Connection connection = ConnectionDB.openConnection();
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL insert_employee(?, ?, ?, ?, ?)");
            preparedStatement.setString(1, employee.getEmployee_name());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getPhone_number());
            preparedStatement.setString(4, employee.getAvatar_url());
            preparedStatement.setInt(5, employee.getDepartment_id());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, null);
        }
    }

    @Override
    public void updateEmployee(@Valid EmployeeDTO employee) {
        Connection connection = ConnectionDB.openConnection();
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL update_employee(?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, employee.getEmployee_id());
            preparedStatement.setString(2, employee.getEmployee_name());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getPhone_number());
            preparedStatement.setString(5, employee.getAvatar_url());
            preparedStatement.setInt(6, employee.getDepartment_id());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, null);
        }
    }

    @Override
    public void deleteEmployee(int employeeId) {
        Connection connection = ConnectionDB.openConnection();
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL delete_employee(?)");
            preparedStatement.setInt(1, employeeId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, null);
        }
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        Connection connection = ConnectionDB.openConnection();
        Employee employee = null;
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL get_employee_by_id(?)");
            preparedStatement.setInt(1, employeeId);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee();
                employee.setEmployee_id(resultSet.getInt("employee_id"));
                employee.setEmployee_name(resultSet.getString("full_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setPhone_number(resultSet.getString("phone_number"));
                employee.setAvatar_url(resultSet.getString("avatar_url"));
                employee.setStatus(resultSet.getBoolean("status"));
                employee.setCreated_at(resultSet.getTimestamp("created_at").toLocalDateTime());
                employee.setDepartment_id(resultSet.getInt("department_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
    }
        finally {
            ConnectionDB.closeConnection(connection, null);
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployeesByName(String employeeName) {
        Connection connection = ConnectionDB.openConnection();
        List<Employee> employees = new ArrayList<>();
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL search_employee_by_name(?)");
            preparedStatement.setString(1, employeeName);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployee_id(resultSet.getInt("employee_id"));
                employee.setEmployee_name(resultSet.getString("full_name"));
                employee.setEmail(resultSet.getString("email"));
                employee.setPhone_number(resultSet.getString("phone_number"));
                employee.setAvatar_url(resultSet.getString("avatar_url"));
                employee.setStatus(resultSet.getBoolean("status"));
                employee.setCreated_at(resultSet.getTimestamp("created_at").toLocalDateTime());
                employee.setDepartment_id(resultSet.getInt("department_id"));
                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, null);
        }
        return employees;
    }

    @Override
    public boolean checkEmailExists(String email) {
        Connection connection = ConnectionDB.openConnection();
        boolean exists = false;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                    "CALL check_email_exists(?)",
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY
            );
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exists = resultSet.getBoolean("exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection,null);
        }

        return exists;
    }

}

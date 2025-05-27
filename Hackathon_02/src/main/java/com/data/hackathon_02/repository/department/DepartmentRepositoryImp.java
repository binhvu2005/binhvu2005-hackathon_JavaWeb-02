package com.data.hackathon_02.repository.department;

import com.data.hackathon_02.model.Department;
import com.data.hackathon_02.utils.ConnectionDB;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartmentRepositoryImp implements DepartmentRepository {

    @Override
    public int getDepartmentTotalPage(int pageSize) {
        Connection connection = ConnectionDB.openConnection();
        int totalPage = 0;
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL get_department_total_page(?)");
            preparedStatement.setInt(1, pageSize);
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
    public List<Department> getAllDepartmentsByPage(int pageIndex, int pageSize) {
        Connection connection = ConnectionDB.openConnection();
        List<Department> departments = new ArrayList<>();
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL get_all_departments_by_page(?, ?)");
            preparedStatement.setInt(1, pageIndex);
            preparedStatement.setInt(2, pageSize);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Department department = new Department();
                department.setDepartment_id(resultSet.getInt("department_id"));
                department.setDepartment_name(resultSet.getString("department_name"));
                department.setDescription(resultSet.getString("description"));
                department.setStatus(resultSet.getBoolean("status"));
                departments.add(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
    }
        finally {
            ConnectionDB.closeConnection(connection, null);
        }
        return departments;
    }

    @Override
    public void addDepartment(Department department) {
        Connection connection = ConnectionDB.openConnection();
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL insert_department(?, ?)");
            preparedStatement.setString(1, department.getDepartment_name());
            preparedStatement.setString(2, department.getDescription());
            int rows = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, null);
        }
    }

    @Override
    public void updateDepartment(Department department) {
        Connection connection = ConnectionDB.openConnection();
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL update_department(?, ?, ?, ?)");
            preparedStatement.setInt(1, department.getDepartment_id());
            preparedStatement.setString(2, department.getDepartment_name());
            preparedStatement.setString(3, department.getDescription());
            preparedStatement.setBoolean(4, department.isStatus());
            int rows = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, null);
        }
    }

    @Override
    public void deleteDepartment(int departmentId) {
        Connection connection = ConnectionDB.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL delete_department(?)}");
            callableStatement.setInt(1, departmentId);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            // Bắt lỗi từ SIGNAL ở stored procedure
            if (e.getErrorCode() == 1644) {  // 1644 là mã lỗi của SIGNAL SQLSTATE '45000'
                throw new RuntimeException(e.getMessage());
            }
            throw new RuntimeException(e);
        } finally {
            ConnectionDB.closeConnection(connection, null);
        }
    }


    @Override
    public Department getDepartmentById(int departmentId) {
        Connection connection = ConnectionDB.openConnection();
        Department department = null;
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL get_department_by_id(?)");
            preparedStatement.setInt(1, departmentId);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                department = new Department();
                department.setDepartment_id(resultSet.getInt("department_id"));
                department.setDepartment_name(resultSet.getString("department_name"));
                department.setDescription(resultSet.getString("description"));
                department.setStatus(resultSet.getBoolean("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, null);
        }
        return department;
    }

    @Override
    public List<Department> getAllDepartmentsByName(String departmentName) {
        Connection connection = ConnectionDB.openConnection();
        List<Department> departments = new ArrayList<>();
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL search_department_by_name(?)");
            preparedStatement.setString(1, departmentName);
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Department department = new Department();
                department.setDepartment_id(resultSet.getInt("department_id"));
                department.setDepartment_name(resultSet.getString("department_name"));
                department.setDescription(resultSet.getString("description"));
                department.setStatus(resultSet.getBoolean("status"));
                departments.add(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
    }
        finally {
            ConnectionDB.closeConnection(connection, null);
        }
        return departments;
    }

    @Override
    public List<Department> getAllDepartments() {
        Connection connection = ConnectionDB.openConnection();
        List<Department> departments = new ArrayList<>();
        try {
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement("CALL get_all_departments()");
            java.sql.ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Department department = new Department();
                department.setDepartment_id(resultSet.getInt("department_id"));
                department.setDepartment_name(resultSet.getString("department_name"));
                department.setDescription(resultSet.getString("description"));
                department.setStatus(resultSet.getBoolean("status"));
                departments.add(department);
            }
        } catch (Exception e) {
            e.printStackTrace();
    }
        finally {
            ConnectionDB.closeConnection(connection, null);
        }
        return departments;
    }
}
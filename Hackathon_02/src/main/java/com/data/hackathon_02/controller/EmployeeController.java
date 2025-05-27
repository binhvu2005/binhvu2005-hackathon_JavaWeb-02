package com.data.hackathon_02.controller;

import com.data.hackathon_02.Dto.EmployeeDTO;
import com.data.hackathon_02.model.Department;
import com.data.hackathon_02.model.Employee;
import com.data.hackathon_02.service.department.DepartmentService;
import com.data.hackathon_02.service.employee.EmployeeService;
import com.data.hackathon_02.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ServletContext context;

    @Autowired
    private EmployeeValidator employeeValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(employeeValidator);
    }


    // Hiển thị danh sách nhân viên (có phân trang, tìm kiếm)
    @GetMapping
    public String listEmployees(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(required = false) String keyword,
                                @RequestParam(defaultValue = "0") int departmentId,
                                Model model) {
        int pageSize = 5;
        List<Employee> employees;
        int totalPages;

        if (keyword != null && !keyword.trim().isEmpty()) {
            employees = employeeService.getAllEmployeesByName(keyword);
            totalPages = 1;
            if (employees.isEmpty()) model.addAttribute("message", "Không tìm thấy nhân viên !");
        } else {
            employees = employeeService.getAllEmployeesByPage(departmentId, page, pageSize);
            totalPages = employeeService.getEmployeeTotalPage(departmentId, pageSize);
        }

        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", keyword);
        model.addAttribute("departmentId", departmentId);
        return "employeeList";
    }

    // Hiển thị danh sách nhân viên theo phòng ban
    @GetMapping("/{id}")
    public String listByDepartment(@PathVariable("id") int id,
                                   @RequestParam(defaultValue = "1") int page,
                                   Model model) {
        Department dept = departmentService.getDepartmentById(id);
        if (dept == null) {
            model.addAttribute("message", "Không tìm thấy phòng ban!");
            return "employeeList";
        }

        int pageSize = 5;
        List<Employee> employees = employeeService.getAllEmployeesByPage(id, page, pageSize);
        int totalPages = employeeService.getEmployeeTotalPage(id, pageSize);

        model.addAttribute("employees", employees);
        model.addAttribute("department", dept);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "employeeList";
    }

    // Hiển thị form thêm mới nhân viên
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employeeDTO", new EmployeeDTO());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employeeForm";
    }

    // Xử lý thêm mới
    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                              BindingResult result,
                              Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employeeForm";
        }

        if (employeeDTO.getAvatar() != null && !employeeDTO.getAvatar().isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + employeeDTO.getAvatar().getOriginalFilename();
            String uploadPath = context.getRealPath("/uploads/");
            new File(uploadPath).mkdirs();
            employeeDTO.getAvatar().transferTo(new File(uploadPath + fileName));
            employeeDTO.setAvatar_url("uploads/" + fileName);
        }

        employeeService.addEmployee(employeeDTO);
        return "redirect:/departments";
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Employee employeeDTO = employeeService.getEmployeeById(id);
        if (employeeDTO == null)  return "redirect:/departments";;

        model.addAttribute("employeeDTO", employeeDTO);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employeeForm";
    }

    // Xử lý cập nhật nhân viên
    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable("id") int id,
                                 @Valid @ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                                 BindingResult result,
                                 Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employeeForm";
        }

        if (employeeDTO.getAvatar() != null && !employeeDTO.getAvatar().isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + employeeDTO.getAvatar().getOriginalFilename();
            String uploadPath = context.getRealPath("/uploads/");
            new File(uploadPath).mkdirs();
            employeeDTO.getAvatar().transferTo(new File(uploadPath + fileName));
            employeeDTO.setAvatar_url("uploads/" + fileName);
        }

        employeeDTO.setEmployee_id(id);
        employeeService.updateEmployee(employeeDTO);
        return "redirect:/employees";
    }

    // Xóa nhân viên
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}

package com.data.hackathon_02.controller;

import com.data.hackathon_02.Dto.DepartmentDTO;
import com.data.hackathon_02.model.Department;
import com.data.hackathon_02.model.Employee;
import com.data.hackathon_02.service.department.DepartmentService;
import com.data.hackathon_02.service.employee.EmployeeService;
import com.data.hackathon_02.validator.DepartmentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentValidator departmentValidator;
    @Autowired
    private EmployeeService employeeService;

    @InitBinder("departmentDTO")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(departmentValidator);
    }

    @GetMapping("")
    public String getAllDepartments(Model model,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "5") int size,
                                    @RequestParam(required = false) String search) {

        if (search != null && !search.isEmpty()) {
            List<Department> departments = departmentService.getAllDepartmentsByName(search);
            model.addAttribute("departments", departments);
            model.addAttribute("isSearch", true);
        } else {
            // Phân trang bình thường
            List<Department> departments = departmentService.getAllDepartmentsByPage(page, size);
            int totalPages = departmentService.getDepartmentTotalPage(size);
            model.addAttribute("departments", departments);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("pageSize", size);
            model.addAttribute("isSearch", false);
        }

        model.addAttribute("search", search);
        return "departmentList";
    }


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("departmentDTO", new DepartmentDTO());
        return "addDepartment";
    }

    @PostMapping("/add")
    public String addDepartment(@Valid @ModelAttribute("departmentDTO") DepartmentDTO departmentDTO, 
                                BindingResult result) {
        if (result.hasErrors()) {
            return "addDepartment";
        }

        Department department = new Department();
        department.setDepartment_name(departmentDTO.getDepartment_name());
        department.setDescription(departmentDTO.getDescription());
        departmentService.addDepartment(department);
        return "redirect:/departments";
    }

    @GetMapping("/update/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Department department = departmentService.getDepartmentById(id);
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartment_name(department.getDepartment_name());
        departmentDTO.setDescription(department.getDescription());
        departmentDTO.setStatus(department.isStatus());

        model.addAttribute("departmentDTO", departmentDTO);
        model.addAttribute("departmentId", id);
        return "updateDepartment";
    }

    @PostMapping("/update/{id}")
    public String editDepartment(@PathVariable("id") int id,
                                 @Valid @ModelAttribute("departmentDTO") DepartmentDTO departmentDTO,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "updateDepartment";
        }

        Department department = new Department();
        department.setDepartment_id(id);
        department.setDepartment_name(departmentDTO.getDepartment_name());
        department.setDescription(departmentDTO.getDescription());
        department.setStatus(departmentDTO.isStatus());

        departmentService.updateDepartment(department);
        return "redirect:/departments";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            departmentService.deleteDepartment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Phòng ban đã được xóa thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/departments";
    }

}
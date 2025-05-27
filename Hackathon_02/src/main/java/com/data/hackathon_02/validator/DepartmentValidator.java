package com.data.hackathon_02.validator;

import com.data.hackathon_02.Dto.DepartmentDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DepartmentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return DepartmentDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DepartmentDTO departmentDTO = (DepartmentDTO) target;
        
        if (departmentDTO.getDepartment_name() == null || departmentDTO.getDepartment_name().trim().isEmpty()) {
            errors.rejectValue("department_name", "department.name.empty", "Tên phòng ban không được để trống");
        } else if (departmentDTO.getDepartment_name().length() < 2 || departmentDTO.getDepartment_name().length() > 100) {
            errors.rejectValue("department_name", "department.name.length", "Tên phòng ban phải từ 2 đến 100 ký tự");
        }
        
        if (departmentDTO.getDescription() != null && departmentDTO.getDescription().length() > 500) {
            errors.rejectValue("description", "department.description.length", "Mô tả không được vượt quá 500 ký tự");
        }
    }
}
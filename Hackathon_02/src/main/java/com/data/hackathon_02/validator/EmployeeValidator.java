package com.data.hackathon_02.validator;

import com.data.hackathon_02.Dto.EmployeeDTO;
import com.data.hackathon_02.repository.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@Component
public class EmployeeValidator implements Validator {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PHONE_REGEX = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";

    @Override
    public boolean supports(Class<?> clazz) {
        return EmployeeDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDTO employeeDTO = (EmployeeDTO) target;
        
        // Kiểm tra tên
        if (employeeDTO.getEmployee_name() == null || employeeDTO.getEmployee_name().trim().isEmpty()) {
            errors.rejectValue("employee_name", "employee.name.empty", "Tên nhân viên không được để trống");
        } else if (employeeDTO.getEmployee_name().length() < 2 || employeeDTO.getEmployee_name().length() > 100) {
            errors.rejectValue("employee_name", "employee.name.length", "Tên nhân viên phải từ 2 đến 100 ký tự");
        }
        
        // Kiểm tra email
        if (employeeDTO.getEmail() == null || employeeDTO.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "employee.email.empty", "Email không được để trống");
        } else if (!Pattern.matches(EMAIL_REGEX, employeeDTO.getEmail())) {
            errors.rejectValue("email", "employee.email.invalid", "Email không hợp lệ");
        } else {
            // Kiểm tra email đã tồn tại chưa (khi thêm mới)
            if (employeeDTO.getEmployee_id() == 0 && 
                employeeRepository.checkEmailExists(employeeDTO.getEmail())) {
                errors.rejectValue("email", "employee.email.exists", "Email đã tồn tại");
            }
        }
        
        // Kiểm tra số điện thoại
        if (employeeDTO.getPhone_number() == null || employeeDTO.getPhone_number().trim().isEmpty()) {
            errors.rejectValue("phone_number", "employee.phone.empty", "Số điện thoại không được để trống");
        } else if (!Pattern.matches(PHONE_REGEX, employeeDTO.getPhone_number())) {
            errors.rejectValue("phone_number", "employee.phone.invalid", "Số điện thoại không hợp lệ");
        }
        
        // Kiểm tra ảnh (nếu có)
        MultipartFile avatarFile = employeeDTO.getAvatar();
        if (avatarFile != null && !avatarFile.isEmpty()) {
            String contentType = avatarFile.getContentType();
            if (contentType == null || !(contentType.equals("image/png") || 
                                         contentType.equals("image/jpeg") || 
                                         contentType.equals("image/jpg"))) {
                errors.rejectValue("avatar", "employee.avatar.type", "Ảnh phải có định dạng PNG, JPEG hoặc JPG");
            }
            
            if (avatarFile.getSize() > 5 * 1024 * 1024) { // 5MB
                errors.rejectValue("avatar", "employee.avatar.size", "Kích thước ảnh không được vượt quá 5MB");
            }
        }
        
        // Kiểm tra phòng ban
        if (employeeDTO.getDepartment_id() <= 0) {
            errors.rejectValue("department_id", "employee.department.required", "Vui lòng chọn phòng ban");
        }
    }
}
package com.data.hackathon_02.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DepartmentDTO {
    private int department_id;
    
    @NotBlank(message = "Tên phòng ban không được để trống")
    @Size(min = 2, max = 100, message = "Tên phòng ban phải từ 2 đến 100 ký tự")
    private String department_name;
    
    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String description;
    
    private boolean status = true;
}
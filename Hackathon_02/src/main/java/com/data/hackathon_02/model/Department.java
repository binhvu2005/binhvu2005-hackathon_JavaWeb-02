package com.data.hackathon_02.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor@NoArgsConstructor@Getter@Setter
public class Department {
    private int department_id;
    @NotBlank(message = "Tên phòng ban không được để trống")
    @Size(min = 2, max = 100, message = "Tên phòng ban phải từ 2 đến 100 ký tự")
    private String department_name;
    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String description;
    private boolean status;
}

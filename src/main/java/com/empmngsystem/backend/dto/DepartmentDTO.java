package com.empmngsystem.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DepartmentDTO {
    private Long id;
    @NotBlank(message = "name is required")
    private String name;
    private List<Long> employeeIds;
}

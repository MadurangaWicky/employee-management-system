package com.empmngsystem.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateDepartmentDTO {

    @NotBlank(message = "Department name is required")
    private String name;
}

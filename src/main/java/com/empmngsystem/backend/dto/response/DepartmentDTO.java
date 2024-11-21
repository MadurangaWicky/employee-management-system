package com.empmngsystem.backend.dto.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DepartmentDTO {
    private Long id;
    private String name;
    private List<Long> employeeIds;
}

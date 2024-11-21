package com.empmngsystem.backend.controller;

import com.empmngsystem.backend.dto.EmployeeDTO;
import com.empmngsystem.backend.entity.Employee;
import com.empmngsystem.backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees/")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
            Employee employee = employeeService.createEmployee(employeeDTO);
            return ResponseEntity.status(201).body(employee);
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDTO employeeDTO) {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(updatedEmployee);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getEmployeesByDepartment(
            @RequestParam Long departmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String searchKeyword
    ) {
            Page<Employee> employees = employeeService.getEmployeesByDepartment(departmentId, page, size, sortBy, sortOrder, searchKeyword);
            return ResponseEntity.ok(employees);
    }
}

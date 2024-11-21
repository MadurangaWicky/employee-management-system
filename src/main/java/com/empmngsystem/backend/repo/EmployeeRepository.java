package com.empmngsystem.backend.repo;

import com.empmngsystem.backend.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);
    Page<Employee> findByDepartmentIdAndFirstNameContainingOrEmailContaining(Long departmentId, String firstName, String email, Pageable pageable);
}

package com.empmngsystem.backend.service;

import com.empmngsystem.backend.dto.response.EmployeeDTO;
import com.empmngsystem.backend.entity.Department;
import com.empmngsystem.backend.entity.Employee;
import com.empmngsystem.backend.exception.CustomException;
import com.empmngsystem.backend.repo.DepartmentRepository;
import com.empmngsystem.backend.repo.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;

    @Transactional
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        try {
            Employee employee = new Employee();
            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
            employee.setEmail(employeeDTO.getEmail());
            employee.setPhone(employeeDTO.getPhone());
            // Assuming you have a method to map department from department ID
            Optional<Department> depOptional = getDepartmentById(employeeDTO.getDepartmentId());
            if(depOptional.isEmpty()){
                throw new CustomException(2000, "Department retrieve by id error");
            }
            employee.setDepartment(depOptional.get());
            return employeeRepository.save(employee);
        }catch (Exception e){
            throw new CustomException(1000, "Employee creation error :"+ e.getMessage());
        }
    }

    public Optional<Department> getDepartmentById(Long departmentId){
        try{return departmentRepository.findById(departmentId);}
        catch(Exception e){
            throw new CustomException(2000, "Department retrieve by id error :"+ e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        try{
        return employeeRepository.findAll();}
        catch(Exception e){
            throw new CustomException(1001, "Get all employees error : "+ e.getMessage());
        }
    }

    public Employee getEmployeeById(Long id) {
        try {
            Optional<Employee> empOptional = employeeRepository.findById(id);
            if(empOptional.isEmpty()){
                throw new CustomException(1003, "Employee retrieve by id error");
            }
            return empOptional.get();

        }
        catch(Exception e){
            throw new CustomException(1002, "Employee get by id error : "+e.getMessage());
        }
    }

    private void validateEmployeeDTO(EmployeeDTO employeeDTO) {
        if (employeeDTO.getFirstName() == null || employeeDTO.getFirstName().isEmpty()) {
            throw new CustomException(1005, "First name is required");
        }
        if (employeeDTO.getLastName() == null || employeeDTO.getLastName().isEmpty()) {
            throw new CustomException(1006, "Last name is required");
        }
        if (employeeDTO.getEmail() == null || employeeDTO.getEmail().isEmpty()) {
            throw new CustomException(1007, "Email is required");
        }
    }

    @Transactional
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        try {
            validateEmployeeDTO(employeeDTO);

            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new CustomException(1002, "Employee get by id error"));

            employee.setFirstName(employeeDTO.getFirstName());
            employee.setLastName(employeeDTO.getLastName());
            employee.setEmail(employeeDTO.getEmail());
            employee.setPhone(employeeDTO.getPhone());

            Department department = getDepartmentById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new CustomException(2000, "Department not found error"));

            employee.setDepartment(department);

            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new CustomException(1008, "Error updating employee : "+ e.getMessage());
        }
    }

    @Transactional
    public void deleteEmployee(Long id) {
        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new CustomException(1001, "Employee not found"));

            employeeRepository.delete(employee);
        } catch (Exception e) {
            throw new CustomException(1009, "Error deleting employee : "+ e.getMessage());
        }
    }


    public Page<Employee> getEmployeesByDepartment(Long departmentId, int page, int size, String sortBy, String sortOrder, String searchKeyword) {
        try {
            Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);

            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                return employeeRepository.findByDepartmentIdAndFirstNameContainingOrEmailContaining(departmentId, searchKeyword, searchKeyword, pageable);
            } else {
                return employeeRepository.findByDepartmentId(departmentId, pageable);
            }
        } catch (Exception e) {
            throw new CustomException(1000, "Error fetching employees : "+ e.getMessage());
        }
    }



}

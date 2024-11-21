package com.empmngsystem.backend.service;

import com.empmngsystem.backend.dto.response.DepartmentDTO;
import com.empmngsystem.backend.entity.Department;
import com.empmngsystem.backend.exception.CustomException;
import com.empmngsystem.backend.repo.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    @Transactional
    public Department createDepartment(DepartmentDTO departmentDTO) {
        try {
            Department department = new Department();
            department.setName(departmentDTO.getName());
            return departmentRepository.save(department);
        }catch(Exception e){
            throw new CustomException(2001, "Department creation error "+e.getMessage());
        }
    }

    public List<Department> getAllDepartments() {
        try {
            return departmentRepository.findAll();
        }
        catch(Exception e){
            throw new CustomException(2002, "get all department list error "+ e.getMessage());
        }
    }

    public Department getDepartmentById(Long id) {
        try {
            return departmentRepository.findById(id)
                    .orElseThrow(() -> new CustomException(2001, "Department not found"));
        }catch(Exception e){
            throw new CustomException(2003, "Get departmnt by id error : "+e.getMessage());
        }
    }

    private void validateDepartmentDTO(DepartmentDTO departmentDTO) {
        if (departmentDTO.getName() == null || departmentDTO.getName().isEmpty()) {
            throw new CustomException(2005, "Department name is required");
        }
    }

    @Transactional
    public Department updateDepartment(Long id, DepartmentDTO departmentDTO) {
        try {
            validateDepartmentDTO(departmentDTO);
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new CustomException(2001, "Department not found"));
            department.setName(departmentDTO.getName());
            return departmentRepository.save(department);

        } catch (Exception e) {
            throw new CustomException(2000, "Error updating department : " + e.getMessage());
        }
    }

    @Transactional
    public void deleteDepartment(Long id) {
        try {
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new CustomException(2001, "Department not found"));

            departmentRepository.delete(department);
        } catch (Exception e) {
            throw new CustomException(2000, "Error deleting department : "+  e.getMessage());
        }
    }


    public Page<Department> getDepartments(String searchKeyword, int page, int size, String sortBy, String sortOrder) {
        try {
            Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            Pageable pageable = PageRequest.of(page, size, sort);
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                return departmentRepository.findByNameContainingIgnoreCase(searchKeyword, pageable);
            } else {
                return departmentRepository.findAll(pageable);
            }
        } catch (Exception e) {
            throw new CustomException(2000, "Error fetching departments: " + e.getMessage());
        }
    }


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hardworker.service;

import com.hardworker.DTO.DepartmentDTO;
import com.hardworker.entity.Department;
import com.hardworker.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author Kain
 */
@Component("departmentService")
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<Department> list() {
        return repository.findAll();
    }
    
    public Department findById(UUID id){
        return repository.findById(id).orElse(null);
    }

    public Department findDepartmentByDepartmentName(String name){
        return repository.findDepartmentByDepartmentName(name);
    }

    public String deleteById(UUID id) {
        if (findById(id) != null) {
            repository.deleteById(id);
            return String.format("Department with id %s has been deleted", id);
        }
        return String.format("Department with id %s not found", id);
    }

    private Department toDepartment(DepartmentDTO dto){
        if(dto != null){
            var department = new Department();
            department.setId(dto.getId() == null ? UUID.randomUUID() : dto.getId());
            department.setDepartmentName(dto.getDepartmentName());
            return department;
        }
        return null;
    }
    
    public Department create(DepartmentDTO obj){
        return obj != null
            ? repository.save(toDepartment(obj))
            : null;
    }
    
    public Department update(UUID id, DepartmentDTO obj){
        var department = findById(id);
        return department != null && obj != null
                ? repository.save(toDepartment(obj))
                : null;
    }
}
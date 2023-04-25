/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hardworker.service;

import com.hardworker.DTO.DepartmentDTO;
import com.hardworker.entity.Department;
import com.hardworker.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<Department> listPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page, size));
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

    private Department toDepartment(DepartmentDTO dto, Department department){
        if(dto != null){
            var obj = department != null ? department : new Department();
            obj.setId(dto.getId() == null ? UUID.randomUUID() : dto.getId());
            obj.setDepartmentName(dto.getDepartmentName());
            return obj;
        }
        return null;
    }
    
    public Department create(DepartmentDTO obj){
        return obj != null
            ? repository.save(toDepartment(obj, null))
            : null;
    }
    
    public Department update(UUID id, DepartmentDTO obj){
        var department = findById(id);
        return department != null && obj != null
                ? repository.save(toDepartment(obj, department))
                : null;
    }
}

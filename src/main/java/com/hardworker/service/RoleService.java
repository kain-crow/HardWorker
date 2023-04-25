/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hardworker.service;

import com.hardworker.entity.Role;
import com.hardworker.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author Kain
 */
@Component("roleService")
public class RoleService {
    
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }



    public List<Role> list(){
        return repository.findAll();
    }

    public Page<Role> list(Integer page, Integer size){
        return repository.findAll(PageRequest.of(page, size));
    }
    
    public Role findByRole(String role){
        return repository.findByRole(role);
    }
    
    public Role findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteById(UUID id){
        if(findById(id) != null ){
            repository.deleteById(id);
            return String.format("Role with id %s has been deleted", id);
        } return String.format("Role with id %s not found", id);
    }
    
    public Role create(Role obj){
        var role = new Role();
        role.setId(obj.getId() == null ? UUID.randomUUID() : obj.getId());
        role.setRole(obj.getRole());
        return repository.save(role);
    }
    
    public Role update(Role obj) {
        var role = findById(obj.getId());
        if(role != null){
            role.setRole(obj.getRole());
            return repository.save(role);
        } return null;
    }
}

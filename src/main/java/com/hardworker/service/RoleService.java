/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hardworker.service;

import com.hardworker.entity.Role;
import com.hardworker.repository.RoleRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    
    public Role findByRole(String role){
        return repository.findByRole(role);
    }
    
    public Role findById(UUID id) {
        return repository.findById(id).orElse(null);
    }
    
    public Role create(Role obj){
        var role = new Role();
        role.setId(obj.getId() == null ? UUID.randomUUID() : obj.getId());
        role.setRole(obj.getRole());
        return repository.save(role);
    }
    
    public Role update(UUID id, Role obj) {
        var role = findById(id);
        if(role != null && obj != null){
            role.setRole(obj.getRole());
            return repository.save(role);
        } return null;
    }
}

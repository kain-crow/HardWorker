/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hardworker.service;

import com.hardworker.DTO.ProjectDTO;
import com.hardworker.entity.Project;
import com.hardworker.repository.ProjectRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kain
 */
@Component("projectService")
public class ProjectService {
    
    private final ProjectRepository repository;
    private final UserService userService;

    public ProjectService(ProjectRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<Project> list() {
        return repository.findAll();
    }
    
    public Project findById(UUID id){
        return repository.findById(id).orElse(null);
    }
    
    public String deleteById(UUID id) {
        if (findById(id) != null) {
            repository.deleteById(id);
            return String.format("Project with id %s has been deleted", id);
        }
        return String.format("Project with id %s not found", id);
    }

    public Project getProjectByName(String name) {
        return repository.getProjectByName(name);
    }
    
    public Project create(ProjectDTO obj){
        var project = new Project();
        project.setProjectId(obj.getId() == null ? UUID.randomUUID() : obj.getId());
        project.setName(obj.getName());
        project.setCustomer(obj.getCustomer());
        project.setListUsers(obj.getListUsers() != null
                ? obj.getListUsers().stream().map(userService::getUserById).collect(Collectors.toList())
                : null);
        return repository.save(project);
    }
    
    public Project update(UUID id, ProjectDTO obj){
        var project = findById(id);
        if(project != null && obj != null){
            project.setCustomer(obj.getCustomer());
            project.setName(obj.getName());
            project.setListUsers(obj.getListUsers() != null 
                ? obj.getListUsers().stream().map(userService::getUserById).collect(Collectors.toList())
                : null);
            repository.save(project);
        }
        return null;
    }
}

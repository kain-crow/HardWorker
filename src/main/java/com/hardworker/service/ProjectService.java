/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hardworker.service;

import com.hardworker.DTO.ProjectDTO;
import com.hardworker.entity.Project;
import com.hardworker.repository.ProjectRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Kain
 */
@Component("projectService")
public class ProjectService {
    
    private final ProjectRepository repository;
    private final UserService userService;

    public ProjectService(ProjectRepository repository, @Lazy UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<Project> list() {
        return repository.findAll();
    }

    public Page<Project> list(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page, size));
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
        return repository.findByName(name);
    }

    private Project toProject(ProjectDTO dto){
        if(dto != null){
            var project = new Project();
            project.setProjectId(dto.getId() == null ? UUID.randomUUID() : dto.getId());
            project.setName(dto.getName());
            project.setCustomer(dto.getCustomer());
            project.setLaidTime(dto.getLaidTime());
            project.setListUsers(dto.getListUsers() != null
                    ? dto.getListUsers().stream().map(userService::findUserById).collect(Collectors.toSet())
                    : null);
            return project;
        }
        return null;
    }
    
    public Project create(ProjectDTO obj){
        return obj != null
            ? repository.save(toProject(obj))
            : null;
    }
    
    public Project update(UUID id, ProjectDTO obj){
        var project = findById(id);
        return project != null && obj != null
                ? repository.save(toProject(obj))
                : null;
    }
}

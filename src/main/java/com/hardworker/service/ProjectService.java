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

    private Project toProject(ProjectDTO dto, Project project){
        if(dto != null){
            var obj = project != null ? project : new Project();
            obj.setProjectId(dto.getId() == null ? UUID.randomUUID() : dto.getId());
            obj.setName(dto.getName());
            obj.setCustomer(dto.getCustomer());
            obj.setLaidTime(dto.getLaidTime());
            obj.setListUsers(dto.getListUsers() != null
                    ? dto.getListUsers().stream().map(userId -> userService.findUserById(UUID.fromString(userId.toString()))).collect(Collectors.toSet())
                    : null);
            return obj;
        }
        return null;
    }
    
    public Project create(ProjectDTO obj){
        return obj != null
            ? repository.save(toProject(obj, null))
            : null;
    }
    
    public Project update(ProjectDTO obj){
        var project = findById(obj.getId());
        return project != null
                ? repository.save(toProject(obj, project))
                : null;
    }
}

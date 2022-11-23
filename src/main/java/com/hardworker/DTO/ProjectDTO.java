/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import com.hardworker.entity.Project;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.hardworker.entity.User;
import lombok.Data;

/**
 *
 * @author Kain
 */
@Data
public class ProjectDTO implements Serializable{
    private UUID id;
    private String name;
    private String customer;
    private List<UUID> listUsers = new ArrayList<>();
    
    public ProjectDTO of(Project project){
        var dto = new ProjectDTO();
        dto.setId(project.getProjectId());
        dto.setName(project.getName());
        dto.setCustomer(project.getCustomer());
        dto.setListUsers(project.getListUsers() != null
            ? project.getListUsers().stream().map(User::getUserId).collect(Collectors.toList())
            : null);
        return dto;
    }
}

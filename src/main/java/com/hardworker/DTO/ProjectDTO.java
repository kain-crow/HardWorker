/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import com.hardworker.entity.Project;
import com.hardworker.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Kain
 */
@Data
public class ProjectDTO implements Serializable{

    private UUID id;
    private String name;
    private String customer;
    private Integer laidTime;
    private Set<Object> listUsers = new HashSet<>();

    public static ProjectDTO of(Project project){
        var projectDTO = new ProjectDTO();
        projectDTO.setId(project.getProjectId());
        projectDTO.setName(project.getName());
        projectDTO.setCustomer(project.getCustomer());
        projectDTO.setLaidTime(project.getLaidTime());
        projectDTO.setListUsers(project.getListUsers().stream().map(User::getUsername).collect(Collectors.toSet()));
        return projectDTO;
    }
}

/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import com.hardworker.entity.Role;
import com.hardworker.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author Kain
 */
@Data
public class ProfileDTO implements Serializable {
    
    private UUID id;
    private String login;
    private String username;
    private Boolean active;
    private String department;
    private String role;
    private Set<String> userRoles = new HashSet<>();
    private Set<Object> listProjects = new HashSet<>();

    public static ProfileDTO of(User user) {
        var profile = new ProfileDTO();
        profile.setId(user.getUserId() == null ? UUID.randomUUID() : user.getUserId());
        profile.setLogin(user.getLogin());
        profile.setUsername(user.getUsername());
        profile.setActive(user.getActive());
        profile.setDepartment(user.getDepartment().getDepartmentName());
        profile.setRole(user.getRole().getRole());
        profile.setUserRoles(user.getUserRoles().stream().map(Role::getRole).collect(Collectors.toSet()));
        profile.setListProjects(user.getListProjects().stream().map(project -> Map.of("projectId", project.getProjectId(), "name", project.getName())).collect(Collectors.toSet()));
        return profile;
    }
}

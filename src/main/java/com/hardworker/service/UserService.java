package com.hardworker.service;

import com.hardworker.DTO.UserDTO;
import com.hardworker.entity.User;
import com.hardworker.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author kain
 */
@Component("userService")
public class UserService {
    
    private final UserRepository repository;
    private final RoleService roleService;
    private final ProjectService projectService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, RoleService roleService, ProjectService projectService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleService = roleService;
        this.projectService = projectService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> list() {
        return repository.findAll();
    }
    
    public User getUserByLogin(String login){
        return repository.getUserByLogin(login);
    }
    
    public User getUserById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    private User toUser(UserDTO dto){
        if(dto != null) {
            var user = new User();
            user.setUserId(dto.getId() == null ? UUID.randomUUID() : dto.getId());
            user.setActive(Boolean.TRUE);
            user.setPassword(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null);
            user.setDepartment(dto.getDepartment());
            user.setLogin(dto.getLogin());
            user.setUsername(dto.getUsername());
            var role = roleService.findByRole(dto.getRole());
            if (role != null) {
                user.setRole(role);
                user.setUserRoles(List.of(role));
            }
            user.setListProjects(dto.getListProjects().stream().map(projectService::findById).collect(Collectors.toSet()));
            return user;
        }
        return null;
    }
    
    public User create(UserDTO obj){
        return obj != null
            ? repository.save(toUser(obj))
            : null;
    }
    
    public User update(UUID id, UserDTO obj) {
        var user = getUserById(id);
        return user != null && obj != null
            ? repository.save(toUser(obj))
            : null;
    }
}

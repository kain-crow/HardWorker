package com.hardworker.service;

import com.hardworker.DTO.UserDTO;
import com.hardworker.entity.User;
import com.hardworker.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author kain
 */
@Component("userService")
public class UserService {
    
    private final UserRepository repository;
    private final RoleService roleService;
    private final ProjectService projectService;
    private final DepartmentService departmentService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, RoleService roleService, @Lazy ProjectService projectService, DepartmentService departmentService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleService = roleService;
        this.projectService = projectService;
        this.departmentService = departmentService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> list() {
        return repository.findAll();
    }

    public Page<User> list(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page, size));
    }
    
    public User findUserByLogin(String login){
        return repository.findByLogin(login);
    }
    
    public User findUserById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteById(UUID id){
        if(findUserById(id) != null ){
            repository.deleteById(id);
            return String.format("User with id %s has been deleted", id);
        } return String.format("User with id %s not found", id);
    }

    private User toUser(UserDTO dto, User user){
        if(dto != null) {
            var obj = user != null ? user : new User();
            obj.setUserId(dto.getId() == null ? UUID.randomUUID() : dto.getId());
            obj.setActive(Boolean.TRUE);
            obj.setPassword(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null);
            obj.setDepartment(departmentService.findDepartmentByDepartmentName(dto.getDepartment()));
            obj.setLogin(dto.getLogin());
            obj.setUsername(dto.getUsername());
            var role = roleService.findByRole(dto.getRole());
            if (role != null) {
                obj.setRole(role);
                obj.setUserRoles(List.of(role));
            }
            obj.setListProjects(dto.getListProjects().stream().map(projectService::findById).collect(Collectors.toSet()));
            return obj;
        }
        return null;
    }
    
    public User create(UserDTO obj){
        return obj != null
            ? repository.save(toUser(obj, null))
            : null;
    }
    
    public User update(UserDTO obj) {
        var user = findUserById(obj.getId());
        return user != null
            ? repository.save(toUser(obj, user))
            : null;
    }
}

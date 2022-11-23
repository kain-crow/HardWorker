package com.hardworker.service;

import com.hardworker.DTO.UserDTO;
import com.hardworker.entity.User;
import com.hardworker.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleService = roleService;
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
    
    public User create(UserDTO obj){
        var user = new User();
        var role = roleService.findByRole(obj.getRole());
        user.setUserId(obj.getId() == null ? UUID.randomUUID() : obj.getId());
        user.setActive(Boolean.TRUE);
        user.setPassword(obj.getPassword() != null ? passwordEncoder.encode(obj.getPassword()) : null);
        user.setDepartment(obj.getDepartment());
        user.setLogin(obj.getLogin());
        user.setUsername(obj.getUsername());
        if (role != null){
            user.setRole(role);
            user.setUserRoles(List.of(role));
        }
        return repository.save(user);
    }
    
    public User update(UUID id, UserDTO obj) {
        var user = getUserById(id);
        if(user != null && obj != null){
            user.setUserId(obj.getId() == null ? UUID.randomUUID() : obj.getId());
            user.setActive(Boolean.TRUE);
            user.setPassword(obj.getPassword() != null ? passwordEncoder.encode(obj.getPassword()) : null);
            user.setDepartment(obj.getDepartment());
            user.setLogin(obj.getLogin());
            user.setUsername(obj.getUsername());
            var role = roleService.findByRole(obj.getRole());
            if (role != null) {
                user.setRole(role);
                user.setUserRoles(List.of(role));
            }
            return repository.save(user);
        } return null;
    }
}

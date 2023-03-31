package com.hardworker.controller;

import com.hardworker.DTO.JobTableItemDTO;
import com.hardworker.DTO.ProfileDTO;
import com.hardworker.DTO.ProjectDTO;
import com.hardworker.DTO.UserDTO;
import com.hardworker.config.SecurityConfig;
import com.hardworker.entity.JobTable;
import com.hardworker.entity.Project;
import com.hardworker.entity.Role;
import com.hardworker.entity.User;
import com.hardworker.service.JobTableService;
import com.hardworker.service.ProjectService;
import com.hardworker.service.RoleService;
import com.hardworker.service.UserService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author kain
 */
@RestController
public class Controller {

    private static final int MIN_PAGE_VALUE = 0;
    private static final String MIN_PAGE_MESSAGE = "Value of page must be >= " + MIN_PAGE_VALUE;
    private static final String PAGE_DEFAULT_VALUE = "0";

    private static final int MIN_SIZE_VALUE = 1;
    private static final String MIN_SIZE_MESSAGE = "Value of size must be >= " + MIN_SIZE_VALUE;

    private static final int MAX_SIZE_VALUE = 100;
    private static final String MAX_SIZE_MESSAGE = "Value of size restricted to " + MAX_SIZE_VALUE;

    private static final String SIZE_DEFAULT_VALUE = "10";
    private final UserService userService;
    private final RoleService roleService;
    private final ProjectService projectService;
    private final JobTableService jobTableService;

    public Controller(@Qualifier("userService") UserService userService,
                      @Qualifier("roleService") RoleService roleService,
                      @Qualifier("projectService") ProjectService projectService,
                      @Qualifier("jobTableService") JobTableService jobTableService) {
        this.userService = userService;
        this.roleService = roleService;
        this.projectService = projectService;
        this.jobTableService = jobTableService;
    }

    /*============================================GET======================================================*/
    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(){
        var userDetails = SecurityConfig.getCustomUserDetails();
        if(userDetails == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userService.getUserByLogin(userDetails.getUsername());
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(ProfileDTO.of(user));
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(
            @Min(value = MIN_PAGE_VALUE, message = MIN_PAGE_MESSAGE)
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,

            @Max(value = MAX_SIZE_VALUE, message = MAX_SIZE_MESSAGE)
            @Min(value = MIN_SIZE_VALUE, message = MIN_SIZE_MESSAGE)
            @RequestParam(required = false, defaultValue = SIZE_DEFAULT_VALUE) int size
    ){
        return ResponseEntity.ok(userService.list());
    }
    
    @GetMapping("/roles")
    public ResponseEntity<Object> getRoles(
            @Min(value = MIN_PAGE_VALUE, message = MIN_PAGE_MESSAGE)
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,

            @Max(value = MAX_SIZE_VALUE, message = MAX_SIZE_MESSAGE)
            @Min(value = MIN_SIZE_VALUE, message = MIN_SIZE_MESSAGE)
            @RequestParam(required = false, defaultValue = SIZE_DEFAULT_VALUE) int size
    ){
        return ResponseEntity.ok(roleService.list());
    }
    
    @GetMapping("/projects")
    public ResponseEntity<Object> getProjects(
            @Min(value = MIN_PAGE_VALUE, message = MIN_PAGE_MESSAGE)
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,

            @Max(value = MAX_SIZE_VALUE, message = MAX_SIZE_MESSAGE)
            @Min(value = MIN_SIZE_VALUE, message = MIN_SIZE_MESSAGE)
            @RequestParam(required = false, defaultValue = SIZE_DEFAULT_VALUE) int size
    ) {
        return ResponseEntity.ok(projectService.list());
    }
    
    @GetMapping("/tables")
    public ResponseEntity<Object> getTables(
            @Min(value = MIN_PAGE_VALUE, message = MIN_PAGE_MESSAGE)
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,

            @Max(value = MAX_SIZE_VALUE, message = MAX_SIZE_MESSAGE)
            @Min(value = MIN_SIZE_VALUE, message = MIN_SIZE_MESSAGE)
            @RequestParam(required = false, defaultValue = SIZE_DEFAULT_VALUE) int size
    ) {
        return ResponseEntity.ok(jobTableService.listTables());
    }

    @GetMapping("/tables/data")
    public ResponseEntity<List<JobTable>> getTables(
            @RequestParam(name = "tableId") String tableId,
            @Min(value = MIN_PAGE_VALUE, message = MIN_PAGE_MESSAGE)
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,

            @Max(value = MAX_SIZE_VALUE, message = MAX_SIZE_MESSAGE)
            @Min(value = MIN_SIZE_VALUE, message = MIN_SIZE_MESSAGE)
            @RequestParam(required = false, defaultValue = SIZE_DEFAULT_VALUE) int size
    ) {
        return ResponseEntity.ok(jobTableService.findAllByTableId(tableId));
    }
    /*===========================================POST======================================================*/
    @PostMapping("/role")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        return ResponseEntity.ok(roleService.create(role));
    }
    
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
        return ResponseEntity.ok(userService.create(user));
    }
    
    @PostMapping("/project")
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO project) {
        return ResponseEntity.ok(projectService.create(project));
    }
    
    @PostMapping("/table")
    public ResponseEntity<JobTable> createTable(@RequestBody JobTableItemDTO table) {
        return ResponseEntity.ok(jobTableService.create(table));
    }

    /*============================================PUT======================================================*/
    
    @PutMapping("/role")
    public ResponseEntity<Role> updateRole(@RequestParam UUID id, @RequestBody Role role) {
        return ResponseEntity.ok(roleService.update(id, role));
    }

    @PutMapping("/user")
    public ResponseEntity<User> updateUser(@RequestParam UUID id, @RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.update(id, user));
    }
    
    @PutMapping("/project")
    public ResponseEntity<Project> updateProject(@RequestParam UUID id, @RequestBody ProjectDTO project) {
        return ResponseEntity.ok(projectService.update(id, project));
    }
    
    @PutMapping("/table")
    public ResponseEntity<JobTable> updateTable(@RequestParam UUID id, @RequestBody JobTableItemDTO table) {
        return ResponseEntity.ok(jobTableService.update(id, table));
    }
}

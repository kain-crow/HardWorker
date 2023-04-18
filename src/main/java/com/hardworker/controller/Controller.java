package com.hardworker.controller;

import com.hardworker.DTO.*;
import com.hardworker.config.SecurityConfig;
import com.hardworker.entity.*;
import com.hardworker.service.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private static final String USER_ENDPOINT = "/user";
    private static final String ROLE_ENDPOINT = "/role";
    private static final String PROJECT_ENDPOINT = "/project";
    private static final String TABLE_ENDPOINT = "/table";
    private final UserService userService;
    private final RoleService roleService;
    private final ProjectService projectService;
    private final JobTableService jobTableService;
    private final DepartmentService departmentService;

    public Controller(@Qualifier("userService") UserService userService,
                      @Qualifier("roleService") RoleService roleService,
                      @Qualifier("projectService") ProjectService projectService,
                      @Qualifier("jobTableService") JobTableService jobTableService,
                      @Qualifier("departmentService") DepartmentService departmentService) {
        this.userService = userService;
        this.roleService = roleService;
        this.projectService = projectService;
        this.jobTableService = jobTableService;
        this.departmentService = departmentService;
    }

    /*============================================GET======================================================*/
    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(){
        var userDetails = SecurityConfig.getCustomUserDetails();
        if(userDetails == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        var user = userService.findUserByLogin(userDetails.getUsername());
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
        return ResponseEntity.ok(new ResponseDTO<>(userService.list(page, size)));
    }
    
    @GetMapping("/roles")
    public ResponseEntity<Object> getRoles(
            @Min(value = MIN_PAGE_VALUE, message = MIN_PAGE_MESSAGE)
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,

            @Max(value = MAX_SIZE_VALUE, message = MAX_SIZE_MESSAGE)
            @Min(value = MIN_SIZE_VALUE, message = MIN_SIZE_MESSAGE)
            @RequestParam(required = false, defaultValue = SIZE_DEFAULT_VALUE) int size
    ){
        return ResponseEntity.ok(new ResponseDTO<>(roleService.list(page, size)));
    }
    
    @GetMapping("/projects")
    public ResponseEntity<Object> getProjects(
            @Min(value = MIN_PAGE_VALUE, message = MIN_PAGE_MESSAGE)
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,

            @Max(value = MAX_SIZE_VALUE, message = MAX_SIZE_MESSAGE)
            @Min(value = MIN_SIZE_VALUE, message = MIN_SIZE_MESSAGE)
            @RequestParam(required = false, defaultValue = SIZE_DEFAULT_VALUE) int size
    ) {
        return ResponseEntity.ok(new ResponseDTO<>(projectService.list(page, size)));
    }
    
    @GetMapping("/tables")
    public ResponseEntity<Object> getTables(
            @Min(value = MIN_PAGE_VALUE, message = MIN_PAGE_MESSAGE)
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,

            @Max(value = MAX_SIZE_VALUE, message = MAX_SIZE_MESSAGE)
            @Min(value = MIN_SIZE_VALUE, message = MIN_SIZE_MESSAGE)
            @RequestParam(required = false, defaultValue = SIZE_DEFAULT_VALUE) int size
    ) {
        var result = jobTableService.listTablesPages(page, size);
        return ResponseEntity.ok(new ResponseDTO<>(
                result.getTotalPages(),
                result.getTotalElements(),
                result.getContent().stream().map(JobTableListDTO::of).toList()));
    }

    @GetMapping("/tables/data")
    public ResponseEntity<ResponseDTO<JobTable>> getTables(
            @RequestParam(name = "tableId") String tableId,
            @Min(value = MIN_PAGE_VALUE, message = MIN_PAGE_MESSAGE)
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,

            @Max(value = MAX_SIZE_VALUE, message = MAX_SIZE_MESSAGE)
            @Min(value = MIN_SIZE_VALUE, message = MIN_SIZE_MESSAGE)
            @RequestParam(required = false, defaultValue = SIZE_DEFAULT_VALUE) int size
    ) {
        return ResponseEntity.ok(new ResponseDTO<>(jobTableService.findAllByTableIdPages(tableId, page, size)));
    }

    @GetMapping("/departments")
    public ResponseEntity<ResponseDTO<Department>> getDepartments(
            @Min(value = MIN_PAGE_VALUE, message = MIN_PAGE_MESSAGE)
            @RequestParam(required = false, defaultValue = PAGE_DEFAULT_VALUE) int page,

            @Max(value = MAX_SIZE_VALUE, message = MAX_SIZE_MESSAGE)
            @Min(value = MIN_SIZE_VALUE, message = MIN_SIZE_MESSAGE)
            @RequestParam(required = false, defaultValue = SIZE_DEFAULT_VALUE) int size
    ) {
        return ResponseEntity.ok(new ResponseDTO<>(departmentService.listPage(page, size)));
    }
    /*===========================================POST======================================================*/
    @PostMapping(ROLE_ENDPOINT)
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        return ResponseEntity.ok(roleService.create(role));
    }
    
    @PostMapping(USER_ENDPOINT)
    public ResponseEntity<User> createUser(@RequestBody UserDTO user){
        return ResponseEntity.ok(userService.create(user));
    }
    
    @PostMapping(PROJECT_ENDPOINT)
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO project) {
        return ResponseEntity.ok(projectService.create(project));
    }
    
    @PostMapping(TABLE_ENDPOINT)
    public ResponseEntity<JobTable> createTable(@RequestBody JobTableItemDTO table) {
        return ResponseEntity.ok(jobTableService.create(table));
    }

    /*============================================PUT======================================================*/
    
    @PutMapping(ROLE_ENDPOINT)
    public ResponseEntity<Role> updateRole(@RequestParam UUID id, @RequestBody Role role) {
        return ResponseEntity.ok(roleService.update(id, role));
    }

    @PutMapping(USER_ENDPOINT)
    public ResponseEntity<User> updateUser(@RequestParam UUID id, @RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.update(id, user));
    }
    
    @PutMapping(PROJECT_ENDPOINT)
    public ResponseEntity<Project> updateProject(@RequestParam UUID id, @RequestBody ProjectDTO project) {
        return ResponseEntity.ok(projectService.update(id, project));
    }
    
    @PutMapping(TABLE_ENDPOINT)
    public ResponseEntity<JobTable> updateTable(@RequestParam UUID id, @RequestBody JobTableItemDTO table) {
        return ResponseEntity.ok(jobTableService.update(id, table));
    }

    /*===========================================DELETE====================================================*/

    @DeleteMapping(ROLE_ENDPOINT)
    public String deleteRole(@RequestParam UUID id){
        return roleService.deleteById(id);
    }

    @DeleteMapping(USER_ENDPOINT)
    public String deleteUser(@RequestParam UUID id){
        return userService.deleteById(id);
    }

    @DeleteMapping(PROJECT_ENDPOINT)
    public String deleteProject(@RequestParam UUID id){
        return projectService.deleteById(id);
    }

    @DeleteMapping(TABLE_ENDPOINT)
    public String deleteTable(@RequestParam UUID id){
        return jobTableService.deleteById(id);
    }
}

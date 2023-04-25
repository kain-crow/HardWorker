package com.hardworker.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author kain
 */
@Entity(name = "sec$User")
@Table(name = "sec_user")
@Getter
@Setter
@RequiredArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
public class User implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID userId;
    
    @Column(name = "login")
    private String login;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "active")
    private Boolean active;
    
    @Column(name = "password")
    private String password;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department")
    private Department department;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "rel_user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
            )
    private List<Role> userRoles = new ArrayList<>();

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "rel_project_user",
//            joinColumns = @JoinColumn(name = "id_user"),
//            inverseJoinColumns = @JoinColumn(name = "id_project")
//    )
    @ManyToMany(mappedBy = "listUsers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Project> listProjects = new HashSet<>();

}

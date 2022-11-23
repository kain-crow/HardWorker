package com.hardworker.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author kain
 */
@Entity(name = "sec$User")
@Table(name = "sec_user")
@Getter
@Setter
@RequiredArgsConstructor
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

    @Column(name = "department")
    private String department;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "rel_user_role",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
            )
    private List<Role> userRoles = new ArrayList<>();

}

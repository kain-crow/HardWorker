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
@Entity
@javax.persistence.Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
public class Project implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID projectId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "customer")
    private String customer;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "rel_project_user",
            joinColumns = @JoinColumn(name = "id_project"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
            )
    private List<User> listUsers = new ArrayList<>();

}

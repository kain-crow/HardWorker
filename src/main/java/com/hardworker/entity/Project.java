package com.hardworker.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author kain
 */
@Entity
@javax.persistence.Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "projectId")
public class Project implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID projectId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "customer")
    private String customer;

    @ManyToMany(mappedBy = "listProjects", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> listUsers = new HashSet<>();

}

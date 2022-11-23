package com.hardworker.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author kain
 */
@Entity(name = "sec$Role")
@Table(name = "sec_role")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Role implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
     
    @Column(name = "role", unique = true, nullable = false)
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

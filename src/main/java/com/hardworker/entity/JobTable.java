/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author Kain
 */

@Entity
@javax.persistence.Table(name = "job_table")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class JobTable implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "table_id")
    private String table_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project")
    @ToString.Exclude
    private Project project;
    
    @Column(name = "project_type")
    private String projectType;
    
    @Column(name = "project_description")
    private String projectDescription;
    
    @Column(name = "monday")
    private Integer monday;
    
    @Column(name = "tuesday")
    private Integer tuesday;
    
    @Column(name = "wednesday")
    private Integer wednesday;
    
    @Column(name = "thursday")
    private Integer thursday;
    
    @Column(name = "friday")
    private Integer friday;
    
    @Column(name = "saturday")
    private Integer saturday;
    
    @Column(name = "sunday")
    private Integer sunday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JobTable jobTable = (JobTable) o;
        return table_id != null && Objects.equals(table_id, jobTable.table_id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

/**
 *
 * @author Kain
 */

@Entity
@Table(name = "job_table")
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
    private String tableId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project")
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

    @Column(name = "sum")
    private Integer sum;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

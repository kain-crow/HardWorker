/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

/**
 *
 * @author Kain
 */
@Data
public class JobTableItemDTO implements Serializable {

    private UUID id;
    private String table_id;
    private UUID user_id;
    private String userName;
    private Date date_from;
    private Date date_to;
    private UUID project_id;
    private String project_name;
    private String projectType;
    private String projectDescription;
    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;
    private Integer saturday;
    private Integer sunday;
    private Integer sum;
}

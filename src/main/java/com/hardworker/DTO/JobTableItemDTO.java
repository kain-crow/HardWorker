/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;
import lombok.Data;

/**
 *
 * @author Kain
 */
@Data
public class JobTableItemDTO implements Serializable {

    private UUID id;
    private String table_id;
    private UUID user_id;
    private Date date_from;
    private Date date_to;
    private UUID project_id;
    private String projectType;
    private String projectDescription;
    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;
    private Integer saturday;
    private Integer sunday;

}

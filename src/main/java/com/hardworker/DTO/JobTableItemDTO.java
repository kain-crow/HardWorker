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
    private String tableId;
    private UUID userId;
    private Date dateFrom;
    private Date dateTo;
    private UUID projectId;
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

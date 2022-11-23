/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import com.hardworker.entity.JobTable;
import com.hardworker.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

/**
 *
 * @author Kain
 */
@Data
public class JobTableListDTO implements Serializable {

    private String table_id;
    private User user_id;
    private Date date_from;
    private Date date_to;

    public static JobTableListDTO of(JobTable jobTable){
        var dto = new JobTableListDTO();
        dto.setTable_id(jobTable.getTable_id());
        dto.setUser_id(jobTable.getUser());
        dto.setDate_from(jobTable.getDateFrom());
        dto.setDate_to(jobTable.getDateTo());
        return dto;
    }

}

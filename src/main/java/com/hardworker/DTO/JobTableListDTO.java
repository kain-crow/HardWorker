/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import com.hardworker.entity.JobTable;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Kain
 */
@Data
public class JobTableListDTO implements Serializable {

    private String tableId;
    private String userName;
    private String departmentName;
    private Date dateFrom;
    private Date dateTo;

    public static JobTableListDTO of(JobTable jobTable){
        var dto = new JobTableListDTO();
        dto.setTableId(jobTable.getTableId());
        dto.setUserName(jobTable.getUser().getUsername());
        dto.setDepartmentName(jobTable.getUser().getDepartment().getDepartmentName());
        dto.setDateFrom(jobTable.getDateFrom());
        dto.setDateTo(jobTable.getDateTo());
        return dto;
    }

}

/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Kain
 */
@Data
public class DepartmentDTO implements Serializable{
    private UUID id;
    private String departmentName;
}

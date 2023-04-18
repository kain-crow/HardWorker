/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Kain
 */
@Data
public class ProjectDTO implements Serializable{
    private UUID id;
    private String name;
    private String customer;
    private Integer laidTime;
    private Set<UUID> listUsers = new HashSet<>();
}

/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import java.io.Serializable;
import java.util.*;

import lombok.Data;

/**
 *
 * @author Kain
 */
@Data
public class ProjectDTO implements Serializable{
    private UUID id;
    private String name;
    private String customer;
    private Set<UUID> listUsers = new HashSet<>();
}

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
public class UserDTO implements Serializable {
    
    private UUID id;
    private String login;
    private String username;
    private Boolean active;
    private String password;
    private String role;
    private String department;
    private Set<UUID> listProjects = new HashSet<>();
}

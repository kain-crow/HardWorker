/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.DTO;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

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
    
}

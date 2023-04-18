package com.hardworker.repository;

import com.hardworker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 *
 * @author kain
 */
public interface RoleRepository extends JpaRepository<Role, UUID>{
    Role findByRole(String role);
}

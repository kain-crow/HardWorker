package com.hardworker.repository;

import com.hardworker.entity.Role;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author kain
 */
public interface RoleRepository extends JpaRepository<Role, UUID>{
    
    @Query("select o from sec$Role o where o.role = ?1")
    Role findByRole(String role);
}

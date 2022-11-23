package com.hardworker.repository;

import com.hardworker.entity.Project;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author kain
 */
public interface ProjectRepository extends JpaRepository<Project, UUID>{
    
    @Query("select o from Project o where o.name = ?1")
    Project getProjectByName(String name);
}

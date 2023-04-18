package com.hardworker.repository;

import com.hardworker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 *
 * @author kain
 */
public interface ProjectRepository extends JpaRepository<Project, UUID>{
    Project findByName(String name);
}

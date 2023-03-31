package com.hardworker.repository;

import com.hardworker.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 *
 * @author kain
 */
public interface DepartmentRepository extends JpaRepository<Department, UUID>{

    Department findDepartmentByDepartmentName(String name);

}

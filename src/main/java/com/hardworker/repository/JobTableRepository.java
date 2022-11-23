package com.hardworker.repository;

import java.util.UUID;

import com.hardworker.entity.JobTable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kain
 */
public interface JobTableRepository extends JpaRepository<JobTable, UUID>{

}

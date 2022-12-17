package com.hardworker.repository;

import java.util.List;
import java.util.UUID;

import com.hardworker.entity.JobTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author kain
 */
public interface JobTableRepository extends JpaRepository<JobTable, UUID>{
    @Query("select o from JobTable o where o.table_id = ?1")
    List<JobTable> findAllByTableId(String tableId);

}

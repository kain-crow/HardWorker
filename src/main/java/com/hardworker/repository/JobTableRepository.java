package com.hardworker.repository;

import com.hardworker.entity.JobTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 *
 * @author kain
 */
public interface JobTableRepository extends JpaRepository<JobTable, UUID>{
    Page<JobTable> findAllByTableId(String tableId, Pageable pageable);
    @Query(value = "select distinct on (o.table_id) * from dbase1.public.job_table o ",
//            countQuery = "select count(*) from dbase1.public.job_table",
            nativeQuery = true)
    Page<JobTable> findDistinctJobTables(Pageable pageable);
}

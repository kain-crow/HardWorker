/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.service;

import com.hardworker.DTO.JobTableItemDTO;
import com.hardworker.DTO.JobTableListDTO;
import com.hardworker.config.SecurityConfig;
import com.hardworker.entity.JobTable;
import com.hardworker.repository.JobTableRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Kain
 */
@Log4j2
@Component("jobTableService")
public class JobTableService {
    
    private final JobTableRepository repository;
    private final ProjectService projectService;
    private final UserService userService;

    public JobTableService(JobTableRepository repository,
                           ProjectService projectService,
                           UserService userService) {
        this.repository = repository;
        this.projectService = projectService;
        this.userService = userService;
    }

    public List<JobTable> list() {
        return repository.findAll();
    }

    public List<JobTableListDTO> listTables() {
        return list().stream().distinct().map(JobTableListDTO::of).toList();
    }

    public Page<JobTable> listTablesPages(Integer page, Integer size){
        return repository.findDistinctJobTables(PageRequest.of(page, size));
    }

    public JobTable findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Page<JobTable> findAllByTableIdPages(String tableId, Integer page, Integer size) {
        return repository.findAllByTableId(tableId, PageRequest.of(page, size));
    }

    public String deleteById(UUID id){
        if(findById(id) != null ){
            repository.deleteById(id);
            return String.format("Table with id %s has been deleted", id);
        } return String.format("Table with id %s not found", id);
    }

    private JobTable toTable(JobTableItemDTO dto){
        if(dto != null) {
            var user = userService.findUserByLogin(SecurityConfig.getUserName());
            var jobTable = new JobTable();
            jobTable.setId(dto.getId() == null ? UUID.randomUUID() : dto.getId());
            jobTable.setTableId(getMd5Hash(user.getUserId().toString(), dto.getDateFrom().toString(), dto.getDateTo().toString()));
            jobTable.setUser(user);
            jobTable.setDateFrom(dto.getDateFrom());
            jobTable.setDateTo(dto.getDateTo());
            jobTable.setProject(projectService.findById(dto.getProjectId()));
            jobTable.setProjectType(dto.getProjectType());
            jobTable.setProjectDescription(dto.getProjectDescription());
            jobTable.setMonday(dto.getMonday());
            jobTable.setTuesday(dto.getTuesday());
            jobTable.setWednesday(dto.getWednesday());
            jobTable.setThursday(dto.getThursday());
            jobTable.setFriday(dto.getFriday());
            jobTable.setSaturday(dto.getSaturday());
            jobTable.setSunday(dto.getSunday());
            jobTable.setSum(dto.getMonday()
                    + dto.getTuesday()
                    + dto.getWednesday()
                    + dto.getThursday()
                    + dto.getFriday()
                    + dto.getSaturday()
                    + dto.getSunday());
            return jobTable;
        }
        return null;
    }
    
    public JobTable create(JobTableItemDTO obj){
        return obj != null
            ? repository.save(toTable(obj))
            : null;
    }
    
    public JobTable update(UUID id, JobTableItemDTO obj) {
        var jobTable = findById(id);
        return jobTable != null && obj != null
                ? toTable(obj)
                : null;
    }

    @SneakyThrows
    public static String getMd5Hash(String... vars){
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(Arrays.toString(vars)));
        return String.format("%032x", new BigInteger(1, md5.digest()));
    }
}

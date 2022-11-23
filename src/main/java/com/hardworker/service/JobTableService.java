/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.hardworker.service;

import com.hardworker.DTO.JobTableItemDTO;
import com.hardworker.DTO.JobTableListDTO;
import com.hardworker.config.SecurityConfig;
import com.hardworker.entity.JobTable;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import com.hardworker.repository.JobTableRepository;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kain
 */
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

    public JobTable findById(UUID id) {
        return repository.findById(id).orElse(null);
    }
    
    public String deleteById(UUID id){
        if(findById(id) != null ){
            repository.deleteById(id);
            return String.format("Table with id %s has been deleted", id);
        } return String.format("Table with id %s not found", id);
    }
    
    public JobTable create(JobTableItemDTO obj){
        var user = userService.getUserByLogin(SecurityConfig.getUserName());
        var jobTable = new JobTable();
            jobTable.setId(obj.getId() == null ? UUID.randomUUID() : obj.getId());
            jobTable.setTable_id(getMd5Hash(user.getUserId().toString(), obj.getDate_from().toString(), obj.getDate_to().toString()));
            jobTable.setUser(user);
            jobTable.setDateFrom(obj.getDate_from());
            jobTable.setDateTo(obj.getDate_to());
            jobTable.setProject(projectService.findById(obj.getProject_id()));
            jobTable.setProjectType(obj.getProjectType());
            jobTable.setProjectDescription(obj.getProjectDescription());
            jobTable.setMonday(obj.getMonday());
            jobTable.setTuesday(obj.getTuesday());
            jobTable.setWednesday(obj.getWednesday());
            jobTable.setThursday(obj.getThursday());
            jobTable.setFriday(obj.getFriday());
            jobTable.setSaturday(obj.getSaturday());
            jobTable.setSunday(obj.getSunday());
        return repository.save(jobTable);
    }
    
    public JobTable update(UUID id, JobTableItemDTO obj) {
        var user = userService.getUserByLogin(SecurityConfig.getUserName());
        var jobTable = findById(id);
        if(jobTable != null && obj != null){
            jobTable.setId(obj.getId() == null ? UUID.randomUUID() : obj.getId());
            jobTable.setTable_id(getMd5Hash(user.getUserId().toString(), obj.getDate_from().toString(), obj.getDate_to().toString()));
            jobTable.setUser(user);
            jobTable.setDateFrom(obj.getDate_from());
            jobTable.setDateTo(obj.getDate_to());
            jobTable.setProject(projectService.findById(obj.getProject_id()));
            jobTable.setProjectType(obj.getProjectType());
            jobTable.setProjectDescription(obj.getProjectDescription());
            jobTable.setMonday(obj.getMonday());
            jobTable.setTuesday(obj.getTuesday());
            jobTable.setWednesday(obj.getWednesday());
            jobTable.setThursday(obj.getThursday());
            jobTable.setFriday(obj.getFriday());
            jobTable.setSaturday(obj.getSaturday());
            jobTable.setSunday(obj.getSunday());
            return repository.save(jobTable);
        }
        return null;
    }

    @SneakyThrows
    public static String getMd5Hash(String... vars){
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(StandardCharsets.UTF_8.encode(Arrays.toString(vars)));
        return String.format("%032x", new BigInteger(1, md5.digest()));
    }
}

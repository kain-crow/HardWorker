package com.hardworker.DTO;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class ResponseDTO<T> {
    private Integer totalPages;
    private long totalElements;
    private List<T> data;

    public ResponseDTO(Page<T> page){
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.data = page.getContent();
    }

    public ResponseDTO(Integer totalPages, long totalElements, List<T> data){
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.data = data;
    }
}

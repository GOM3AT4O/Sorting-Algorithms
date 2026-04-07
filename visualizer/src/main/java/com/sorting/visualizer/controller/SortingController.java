package com.sorting.visualizer.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sorting.visualizer.DTO.SortRequest;
import com.sorting.visualizer.service.SortingService;

import lombok.RequiredArgsConstructor;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/sort")
@RequiredArgsConstructor
public class SortingController {
    private final SortingService sortingService;

    @PostMapping("/execute")
    public ResponseEntity<Object> executeSort(@RequestBody SortRequest request){
        try{
            
            Object result = sortingService.processRequest(request);
            return ResponseEntity.ok(result);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }


    
    
}

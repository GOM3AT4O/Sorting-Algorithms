package com.sorting.visualizer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sorting.visualizer.DTO.ComparisonResponse;
import com.sorting.visualizer.DTO.SortRequest;
import com.sorting.visualizer.model.SortMetrics;
import com.sorting.visualizer.strategy.SortingAlgorithm;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@Service
@RequiredArgsConstructor

public class SortingService {
    private final List<SortingAlgorithm> algorithms ;
    private final ArrayGenerationService arrayGenerator;

    public Object processRequest(SortRequest request){
        SortingAlgorithm algorithm = algorithms.stream()
                .filter(a -> a.getName().equalsIgnoreCase(request.getAlgorithm()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid sorting algorithm look for one of the 6 bro: " + request.getAlgorithm()));

        int[] array = (request.getInitialArray() != null && request.getInitialArray().length > 0)
        ? request.getInitialArray().clone()
        : arrayGenerator.generateArray(request.getArraySize(), request.getArrayType());
        
        if(request.isVisualizationMode()){
            return algorithm.sortForVisualization(array);
        }else{
            return runComparisons(algorithm, array,request);
        }


    }
    private ComparisonResponse runComparisons(SortingAlgorithm algorithm, int[] array, SortRequest request){
        int runs = request.getNumberOfRuns() > 0 ? request.getNumberOfRuns() : 1;

        long totalTime = 0;
        long minTime = Long.MAX_VALUE;
        long maxTime = Long.MIN_VALUE;
        long totalComparisons = 0;
        long totalInterchanges = 0;

        for (int i = 0; i < runs; i++) {
            int[] arrayCopy = array.clone();
            SortMetrics metrics = new SortMetrics();

            long start = System.nanoTime();
            algorithm.sortForComparison(arrayCopy, metrics);
            long end = System.nanoTime();
            long duration = end - start;

            totalTime += duration;
            minTime = Math.min(minTime, duration);
            maxTime = Math.max(maxTime, duration);
            totalComparisons += metrics.getComparisons();
            totalInterchanges += metrics.getInterchanges();
        }

        //build and return the response
        ComparisonResponse response = new ComparisonResponse();
        response.setAlgorithmName(algorithm.getName());
        response.setArraySize(request.getArraySize());
        response.setArrayGenerationMode(request.getArrayType());
        response.setNumberOfRuns(runs);
        response.setAverageExecutionTime(totalTime / runs);
        response.setMinRunTime(minTime);
        response.setMaxRunTime(maxTime);
        response.setNumberOfComparisons(totalComparisons / runs);
        response.setNumberOfInterchanges(totalInterchanges / runs);
        
        return response;
    }
    
}

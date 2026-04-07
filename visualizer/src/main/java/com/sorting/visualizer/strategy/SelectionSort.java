package com.sorting.visualizer.strategy;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sorting.visualizer.DTO.VisualizationStep;
import com.sorting.visualizer.model.SortMetrics;


@Component
public class SelectionSort implements SortingAlgorithm {

    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    public void sortForComparison(int[] array, SortMetrics metrics) {
        int n = array.length;
        for(int i = 0 ;i<n-1 ;i++){
            int minIndex = i ; 
            for(int j = i+1; j<n ; j++){
                metrics.incrementComparisons();
                if(array[j] < array[minIndex]){
                    minIndex = j;
                }
            }
            // swap arr[i] and arr[minIndex]
            if(minIndex != i){
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
                metrics.incrementInterchanges();
            }
        }
    }

    @Override
    public List<VisualizationStep> sortForVisualization(int[] array) {
        List<VisualizationStep> steps = new ArrayList<>();
        int n = array.length;
        long currentComparisons = 0;
        long currentInterchanges = 0;
        for(int i =0 ;i<n-1;i++){
            int minIndex = i;
            for(int j = i+1 ; j<n ; j++){
                currentComparisons++;

                // highlight yje current min we are scanning 
                steps.add(new VisualizationStep(array.clone(), currentComparisons, currentInterchanges, minIndex, j));
                if (array[j] < array[minIndex]){
                    minIndex = j;
                }
            }
            if(minIndex != i){
                
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
                currentInterchanges++;
                // highlight the swap
                steps.add(new VisualizationStep(array.clone(), currentComparisons, currentInterchanges, i, minIndex));
            }
        }
        steps.add(new VisualizationStep(array.clone(), currentComparisons, currentInterchanges, -1, -1));
        return steps;
            
    }
    
}

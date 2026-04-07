package com.sorting.visualizer.strategy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sorting.visualizer.DTO.VisualizationStep;
import com.sorting.visualizer.model.SortMetrics;

@Component
public class BubbleSort implements SortingAlgorithm {
    @Override
    public String getName() {
        return "Bubble Sort";
    }

    @Override
    public void sortForComparison(int[] array, SortMetrics metrics) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                metrics.incrementComparisons();
                if (array[j] > array[j + 1]) {
                    // swap arr[j] and arr[j+1]
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    metrics.incrementInterchanges();
                }
            }
        }
    }

    @Override
    public List<VisualizationStep> sortForVisualization(int[] array) {
        List<VisualizationStep> steps = new ArrayList<>();
        int n = array.length;

        long currentComparisons = 0;
        long currentInterchanges = 0;
        

        for (int i = 0 ; i<n-1 ;i++){
            for(int j =0 ; j<n-i-1 ; j++){
                currentComparisons++;
                steps.add(new VisualizationStep(array.clone(), currentComparisons, currentInterchanges, j, j+1));
                if (array[j] > array[j+1]){
                    // swap arr[j] and arr[j+1]
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    currentInterchanges++;
                    steps.add(new VisualizationStep(array.clone(), currentComparisons, currentInterchanges, j, j+1));
                }
            }
        }
        steps.add(new VisualizationStep(array.clone(), currentComparisons, currentInterchanges, -1, -1));
        return steps;
        }

    
}

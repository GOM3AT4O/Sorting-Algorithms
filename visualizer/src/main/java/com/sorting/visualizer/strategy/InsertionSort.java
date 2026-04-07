package com.sorting.visualizer.strategy;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sorting.visualizer.DTO.VisualizationStep;
import com.sorting.visualizer.model.SortMetrics;

@Component
public class InsertionSort implements SortingAlgorithm {

    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    public void sortForComparison(int[] array, SortMetrics metrics) {
        int n = array.length;
        for (int i =1 ; i<n; i++){
            int index = array[i];
            int j = i -1;

            //move the elements of array that are greater than the index 
            while(j>=0){
                metrics.incrementComparisons();
                if(array[j]>index){
                    array[j+1] = array[j];
                    j--;
                    metrics.incrementInterchanges();
                }else{
                    break;
                }
            }
            array[j+1] = index;
            metrics.incrementInterchanges(); //placing the index as an interchange
        }
    }

    @Override
    public List<VisualizationStep> sortForVisualization(int[] array) {
        List<VisualizationStep> steps = new ArrayList<>();
        int n = array.length;

        long currentComparisons = 0;
        long currentInterchanges = 0;

        for (int i = 1; i < n; i++) {
            int index = array[i];
            int j = i - 1;

            while (j >= 0) {
                currentComparisons++;
                steps.add(new VisualizationStep(array.clone(), currentComparisons, currentInterchanges, i,j));
                if (array[j] > index) {
                    array[j + 1] = array[j];
                    j--;
                    currentInterchanges++;
                    steps.add(new VisualizationStep(array.clone(), currentComparisons, currentInterchanges, j, i));
                } else {
                    break;
                }
            }
            array[j + 1] = index;
            currentInterchanges++;
            steps.add(new VisualizationStep(array.clone(), currentComparisons, currentInterchanges, j + 1, -1));
        }
        steps.add(new VisualizationStep(array.clone(), currentComparisons, currentInterchanges, -1, -1));
        return steps;
    }
    
}

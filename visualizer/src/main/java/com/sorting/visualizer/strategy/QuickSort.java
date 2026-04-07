package com.sorting.visualizer.strategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.sorting.visualizer.DTO.VisualizationStep;
import com.sorting.visualizer.model.SortMetrics;

@Component

public class QuickSort implements SortingAlgorithm{

    private final Random random = new Random(); 

    @Override
    public String getName(){
        return "Quick Sort";
    }

    @Override
    public void sortForComparison(int[] array, SortMetrics metrics) {
        if(array == null || array.length < 2){
            return;
        }
        quickSortComparison(array, 0, array.length -1 , metrics);
    }

    private void quickSortComparison(int[] array, int low , int high , SortMetrics metrics){
        if(low < high){
            int pi = partitionComparison(array, low, high, metrics);
            quickSortComparison(array, low, pi -1 , metrics);
            quickSortComparison(array, pi + 1 , high , metrics);
        }
    }

    private int partitionComparison(int[] array,int low ,int high, SortMetrics metrics){
        // the last element is the pivot
        int randomIndex = low + random.nextInt(high - low + 1);
        int temp = array[randomIndex];
        array[randomIndex] = array[high];
        array[high] = temp;
        metrics.incrementInterchanges();
        int pivot = array[high];
        int  i = (low-1); 

        for(int j = low ; j < high ; j++){
            metrics.incrementComparisons();
            if(array[j] < pivot){
                i++;
                // swap array[i] and array[j]
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
                metrics.incrementInterchanges();
            }
        }
        //place the pivot 
        int tmp = array[i + 1];
        array[i+1] = array[high];
        array[high] = tmp;
        metrics.incrementInterchanges();
        return i+1;
    


    }
    @Override
    public List<VisualizationStep> sortForVisualization(int[] array) {
        List<VisualizationStep> steps = new ArrayList<>();
        if(array == null || array.length < 2){
            return steps;
        }
        long[] counters = new long[]{0, 0}; // index 0 for comparisons and index 1 for interchanges
        quickSortVisualization(array, 0, array.length - 1, steps, counters);
        steps.add(new VisualizationStep(array.clone(), counters[0], counters[1], -1, -1));
        return steps;
    }
    private void quickSortVisualization(int[] array, int low, int high, List<VisualizationStep> steps, long[] counters) {
        if(low < high){
            int pi = partitionVisualization(array, low, high, steps, counters);
            quickSortVisualization(array, low, pi - 1, steps, counters);
            quickSortVisualization(array, pi + 1, high, steps, counters);
        }
    }

    private int partitionVisualization(int[] array, int low, int high, List<VisualizationStep> steps, long[] counters) {
        int randomIndex = low + random.nextInt(high - low + 1);
        int temp = array[randomIndex];
        array[randomIndex] = array[high];
        array[high] = temp;
        counters[1]++; // count the swap as an interchange
        steps.add(new VisualizationStep(array.clone(), counters[0], counters[1], randomIndex, high));
        int pivot = array[high];
        int i = (low -1);

        for(int j = low ; j<high ; j++){
            counters[0]++;

            steps.add(new VisualizationStep(array.clone(),counters[0],counters[1],j,high));
            if(array[j] < pivot){
                i++;
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
                counters[1]++;
                steps.add(new VisualizationStep(array.clone(),counters[0],counters[1],i,j));
            }
        }
    int tmp = array[i+1];
    array[i+1] = array[high];
    array[high] = tmp;
    counters[1]++;
    steps.add(new VisualizationStep(array.clone(),counters[0],counters[1],i+1,high));

    return i+1;
    
    }
        
}

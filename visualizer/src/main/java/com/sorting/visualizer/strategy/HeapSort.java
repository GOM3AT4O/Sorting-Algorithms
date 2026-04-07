package com.sorting.visualizer.strategy;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sorting.visualizer.DTO.VisualizationStep;
import com.sorting.visualizer.model.SortMetrics;
@Component
public class HeapSort implements SortingAlgorithm {

    @Override
    public String getName(){
        return "Heap Sort";

    }
    @Override
    public void sortForComparison(int[] array, SortMetrics metrics) {
        int n = array.length;

        //build the heap
        for(int i = n/2 -1 ; i>=0 ; i--){
            heapifyComparison(array, n , i , metrics);
        }

        //extract elements from the heap one by one 
        for(int i = n-1 ; i > 0 ; i--){
            //move the current root to the end the largest 
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            metrics.incrementInterchanges(); // count the swap as an interchange

            heapifyComparison(array,i,0,metrics);
        }
    }

    private void heapifyComparison(int[] array, int n , int i , SortMetrics metrics){
        int largest = i ; 
        int left = 2 *i+1;
        int right = 2 *i +2;

        //if the left is larger 
        if(left< n ){
            metrics.incrementComparisons();
            if(array[left]> array[largest]){
                largest = left;
            }
        }

        //if the right is larger
        if(right < n ){
            metrics.incrementComparisons();
            if(array[right] > array[largest]){
                largest = right;
            }
        }
        if (largest !=i){
        int swap = array[i];
        array[i] = array[largest];
        array[largest] = swap;
        metrics.incrementInterchanges(); // count the swap as an interchange

        //recursively heapify the affected sub-tree
        heapifyComparison(array, n , largest , metrics);
        }
    }

    @Override
    public List<VisualizationStep> sortForVisualization(int[] array) {
        List<VisualizationStep> steps = new ArrayList<>();
        int n = array.length;

        // counters[0] = comparisons, counters[1] = interchanges
        long[] counters = new long[]{0,0};

        for(int i = n/2 -1 ; i >=0 ; i--){
            heapifyVisualization(array, n, i, steps, counters);
        }

        for(int i = n -1; i>0 ; i--){
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp ; 
            counters[1] ++ ;

            steps.add(new VisualizationStep(array.clone(), counters[0], counters[1], 0, i));

            heapifyVisualization(array, i, 0, steps, counters);
        }


        //turn off all highlights when finishedarrayGenerationService
        steps.add(new VisualizationStep(array.clone(), counters[0], counters[1], -1, -1));
        return steps;
    }

    private void heapifyVisualization(int[] array, int n, int i, List<VisualizationStep> steps, long[] counters) {
        int largest = i ; 
        int left = 2 *i+1;
        int right = 2 *i +2;

        if(left<n){
            counters[0]++;
            steps.add(new VisualizationStep(array.clone(), counters[0], counters[1], left, largest));
            if(array[left]> array[largest]){
                largest = left;
            }
        }

        if(right<n){
            counters[0]++;
            steps.add(new VisualizationStep(array.clone(), counters[0], counters[1], right, largest));
            if(array[right]> array[largest]){
                largest = right;
            }
        }

        if(largest != i ){
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            counters[1]++;
            steps.add(new VisualizationStep(array.clone(), counters[0], counters[1], i, largest));

            heapifyVisualization(array, n , largest , steps, counters);
        }

    }


        

    
    
    


    
}

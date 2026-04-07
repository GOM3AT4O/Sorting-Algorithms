package com.sorting.visualizer.strategy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sorting.visualizer.DTO.VisualizationStep;
import com.sorting.visualizer.model.SortMetrics;
@Component
public class MergeSort implements SortingAlgorithm{

    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    public void sortForComparison(int[] array, SortMetrics metrics) {
        if(array ==null || array.length < 2){
            return;
        }
        int [] temp = new int[array.length];
        mergeSortComparison(array, temp, 0, array.length-1, metrics);
    }
    private void mergeSortComparison(int[] array, int[] temp, int left, int right, SortMetrics metrics){
        if(left >= right){
            return;
        }
        int mid = left + (right - left) / 2;
        //divide the array into two halves and sort them recursively
        mergeSortComparison(array, temp, left, mid, metrics);
        mergeSortComparison(array, temp, mid+1, right, metrics);
        //merge the sorted halves
        mergeComparison(array, temp, left, mid, right, metrics);
    }
    
    private void mergeComparison(int[] array,int[] temp,int left , int mid , int right,SortMetrics metrics){
        for (int i = left; i <= right; i++){
            temp[i] = array[i];
        }
        int i = left; // starting index for left
        int j = mid +1 ; // starting index for right
        int k = left; // starting index for merged array

        while (i <= mid && j <= right) {
            metrics.incrementComparisons();
            if (temp[i] <= temp[j]) {
                array[k] = temp[i];
                i++;
            } else {
                array[k] = temp[j];
                j++;
            }
            metrics.incrementInterchanges(); // i count writing back to the main array as interchange
            k++;
        }

        while (i <= mid) {
            
        
            array[k] = temp[i];
            metrics.incrementInterchanges();
            i++;
            k++;
        }
    
    }

    @Override
    public List<VisualizationStep> sortForVisualization(int[] array) {
        List<VisualizationStep> steps = new ArrayList<>();
        if(array ==null || array.length < 2){
            return steps;
        }
        int [] temp = new int[array.length];
        // Index 0 = comparisons, Index 1 = interchanges
        long[] counters = new long[]{0,0};
        mergeSortVisualization(array, temp, 0, array.length-1, steps, counters);
        steps.add(new VisualizationStep(array.clone(), counters[0], counters[1], -1,-1));
        return steps;
    }

    private void mergeSortVisualization(int[] array, int[] temp, int left, int right, List<VisualizationStep> steps, long[] counters){
        if(left >= right){
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSortVisualization(array, temp, left, mid, steps, counters);
        mergeSortVisualization(array, temp, mid+1, right, steps, counters);
        mergeVisualization(array, temp, left, mid, right, steps, counters);
    }

    private void mergeVisualization(int [] array,int [] temp , int left , int mid , int right ,List<VisualizationStep> steps, long[] counters){
        for (int i = left ; i<=right;i++){
            temp[i] = array[i];

        }
        int i = left; // starting index for left
        
        int j = mid +1 ; // starting index for right
        int k = left; // starting index for merged array

        while(i <=mid &&j <= right){
            counters[0]++;
            //highlight the 2 elements in the temp array we are comparing
            steps.add(new VisualizationStep(array.clone(),counters[0],counters[1],k,-1));


            if (temp[i] <= temp[j]){
                array[k] = temp[i];
                i++;
            }else{
                array[k] = temp[j];
                j++;
            }
            counters[1]++;
            //highlight the swap
            steps.add(new VisualizationStep(array.clone(),counters[0],counters[1],k,-1));
            k++;
        }
        while(i<=mid){
            array[k] = temp[i];
            counters[1]++;
            //highlight the swap
            steps.add(new VisualizationStep(array.clone(),counters[0],counters[1],k,-1));
            i++;
            k++;
        }
    }



    
}

package com.sorting.visualizer.strategy;
import  java.util.List;

import com.sorting.visualizer.DTO.VisualizationStep;
import com.sorting.visualizer.model.SortMetrics;
public interface SortingAlgorithm {
    String getName();


// modify the array in place and update th matrics counter
    void sortForComparison(int[] array, SortMetrics metrics);

    //return a list of the steps 
    List<VisualizationStep> sortForVisualization(int[] array);
}

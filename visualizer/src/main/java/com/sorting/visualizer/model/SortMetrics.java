package com.sorting.visualizer.model;

import lombok.Data;

@Data
public class SortMetrics {
    private long comparisons = 0 ;
    private long interchanges = 0 ;

    public void incrementComparisons() {
        comparisons++;
    }

    public void incrementInterchanges() {
        interchanges++;
    }

    
}

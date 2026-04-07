package com.sorting.visualizer.DTO;

import lombok.Data;


@Data


public class VisualizationStep {
    private int[] currentArrayState; // The heights of the bars at this exact moment
    private long currentComparisons; // The number of comparisons made up to this point
    private long currentInterchanges; // The number of interchanges made up to this point

    private int activeBar1;
    private int activeBar2;

    public VisualizationStep(int[] currentArrayState, long currentComparisons, long currentInterchanges , int activeBar1, int activeBar2) {
        this.currentArrayState = currentArrayState;
        this.currentComparisons = currentComparisons;
        this.currentInterchanges = currentInterchanges;
        this.activeBar1 = activeBar1; // No active bars
        this.activeBar2 = activeBar2; // No active bars
    }

}

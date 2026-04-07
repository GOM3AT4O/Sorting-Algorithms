package com.sorting.visualizer.DTO;

import lombok.Data;
@Data
public class ComparisonResponse {

    private String algorithmName;
    private int arraySize;
    private String arrayGenerationMode;
    private int numberOfRuns;

    private long averageExecutionTime;
    private long minRunTime;
    private long maxRunTime;

    private long numberOfComparisons;
    private long numberOfInterchanges;
}

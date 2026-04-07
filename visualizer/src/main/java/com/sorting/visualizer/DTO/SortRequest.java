package com.sorting.visualizer.DTO;



import java.util.List;

import lombok.Data;

@Data
public class SortRequest {
    
    private boolean visualizationMode;
    private String algorithm;
    private String arrayType;
    private int arraySize;
    private int numberOfRuns;
    private List<String> fileNames;
    private int visualizationSpeed;
    private int[] initialArray; //change

    
}

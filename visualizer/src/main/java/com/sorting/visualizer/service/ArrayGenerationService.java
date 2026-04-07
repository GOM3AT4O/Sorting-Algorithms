package com.sorting.visualizer.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class ArrayGenerationService {
    private final Random random = new Random();
    public int[] generateArray(int size, String arrayType) {
        int[] array = new int[size];

        String type = (arrayType != null) ? arrayType.toLowerCase() : "random";

        switch (type){
            case "sorted":
                for(int i=0 ; i<size ;i++){
                    array[i] = (i+1) * 10; // sorted array with values 10, 20, ..., 10*size
                }
                break;
            case "inversely sorted":
            case "reversed":
            case "reverse sorted":
                for(int i=0 ; i<size ;i++){
                    array[i] = (size - i) * 10; // reversed sorted array with values 10*size, 10*(size-1), ..., 10
                }
                break;
            case "random":
            default:
                for(int i=0 ; i<size ;i++){
                    array[i] = random.nextInt(1000) + 1; // random integers between 1 and 1000
                }
                break;
        }
        return array;
    }
    
}

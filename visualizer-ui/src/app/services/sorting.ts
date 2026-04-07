import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SortRequest {
  visualizationMode: boolean;
  algorithm: string;
  arrayType: string;
  arraySize: number;
  numberOfRuns?: number;
  initialArray?: number[];
}

export interface VisualizationStep{
  currentArrayState:number[];
  currentComparisons: number;
  currentInterchanges: number;
  activeBar1: number;
  activeBar2: number;
}

@Injectable({
  providedIn: 'root',
})
export class Sorting {

  private apiUrl = 'http://localhost:8080/api/sort/execute';

  constructor(private http: HttpClient) {}

  getMetrics(request:SortRequest): Observable<any> {

    const payload = {...request,visualizationMode:false};
    return this.http.post<any>(this.apiUrl, payload);
  }

  getVisualization(request: SortRequest): Observable<VisualizationStep[]> {

    const payload = {...request,visualizationMode:true};
    return this.http.post<VisualizationStep[]>(this.apiUrl, payload);
  }

  
}

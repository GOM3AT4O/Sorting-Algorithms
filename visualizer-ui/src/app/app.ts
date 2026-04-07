import { Component, OnInit,  ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { ControlPanel } from './components/control-panel/control-panel';
import { Visualizer } from './components/visualizer/visualizer';
import { Sorting, SortRequest, VisualizationStep } from './services/sorting';
import { ComparisonTable } from './components/comparison-table/comparison-table';

@Component({
  selector: 'app-root',
  imports: [CommonModule, ControlPanel, Visualizer ,ComparisonTable],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  mode: 'visualization' | 'comparison' = 'visualization';
  currentArray: number[] = [];
  activeBar1: number = -1;
  activeBar2: number = -1;
  isSorted: boolean = false;
  isSorting: boolean = false;
  private sortToken: number = 0;
  currentSpeed: number = 5;
  totalComparisons: number = 0;
  totalInterchanges: number = 0;
  private sortSubscription: Subscription | null = null;

  constructor(private Sorting: Sorting, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.generateDummyArray(50, 'random');
  }

  generateDummyArray(size: number, type: string) {
    const typeLowerCase = type.toLowerCase();
    this.isSorted = false;
    this.activeBar1 = -1;
    this.activeBar2 = -1;
    //this.currentArray = []; change 
    const randomValues = Array.from({ length: size }, () =>
      Math.floor(Math.random() * 1000) + 1
    );//change
     if (typeLowerCase === 'sorted') {
      //CHANGED: sort the random values ascending instead of 10,20,30...
      this.currentArray = [...randomValues].sort((a, b) => a - b);
    } else if (typeLowerCase === 'reverse sorted' || typeLowerCase === 'inversely sorted') {
      //CHANGED: sort the random values descending instead of fixed steps
      this.currentArray = [...randomValues].sort((a, b) => b - a);
    } else {
      this.currentArray = randomValues;
    }
  }

  onFileLoaded(data: { fileName: string, array: number[] }) {
  this.sortToken++;
  this.sortSubscription?.unsubscribe();
  this.sortSubscription = null;
  this.isSorting = false;
  this.isSorted = false;
  this.totalComparisons = 0;
  this.totalInterchanges = 0;
  this.currentArray = [...data.array]; // set the visualizer to show file array
  this.cdr.detectChanges();
}

  onSpeedChange(newSpeed: number) {
    this.currentSpeed = newSpeed;
  }

  toggleMode() {
    this.mode = this.mode === 'visualization' ? 'comparison' : 'visualization';
  }

  onGenerate(params: any) {
    this.sortToken++;
    this.sortSubscription?.unsubscribe();
    this.sortSubscription = null;
    this.isSorting = false;
    this.isSorted = false;
    this.totalComparisons = 0;
    this.totalInterchanges = 0;
    this.generateDummyArray(Number(params.arraySize), params.arrayType);
  }

  async onSort(params: any) {
    if (this.isSorting) return;

    if (this.isSorted) {
      const request: SortRequest = {
        visualizationMode: false,
        algorithm: params.algorithm,
        arrayType: params.arrayType,
        arraySize: Number(params.arraySize)
      };
      this.Sorting.getMetrics(request).subscribe(metrics => {
        console.log('Already sorted! Metrics:', metrics);
      });
      return;
    }

    this.isSorting = true;
    this.isSorted = false;
    const myToken = ++this.sortToken;

    const request: SortRequest = {
      visualizationMode: true,
      algorithm: params.algorithm,
      arrayType: params.arrayType,
      arraySize: Number(params.arraySize),
      initialArray: [...this.currentArray]  //change
    };

    this.sortSubscription = this.Sorting.getVisualization(request).subscribe({
      next: async (steps: VisualizationStep[]) => {
        if (myToken !== this.sortToken) return;

        this.currentArray = [...steps[0].currentArrayState];
        const delayMs = Math.max(1, Math.floor(500 / params.visualizationSpeed));

        for (let i = 0; i < steps.length; i++) {
          if (myToken !== this.sortToken) return;

          const step = steps[i];
          this.currentArray = [...step.currentArrayState];
          this.activeBar1 = step.activeBar1;
          this.activeBar2 = step.activeBar2;
          this.cdr.detectChanges(); // force Angular to re-render this exact frame
          await this.sleep(Math.max(1, Math.floor(500 / this.currentSpeed)));
        }

        if (myToken === this.sortToken) {
          const lastStep = steps[steps.length - 1];
          this.totalComparisons = lastStep.currentComparisons;
          this.totalInterchanges = lastStep.currentInterchanges;

          this.isSorted = true;
          this.isSorting = false;
          this.cdr.detectChanges();
        }
      },
      error: (err) => {
        console.error('Sort failed:', err);
        this.isSorting = false; // always reset on error so buttons re-enable
        this.cdr.detectChanges();
      }
    });
  }

  sleep(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
}
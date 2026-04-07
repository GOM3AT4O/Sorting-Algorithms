import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-visualizer',
  imports: [CommonModule],
  templateUrl: './visualizer.html',
  styleUrl: './visualizer.css',
})
export class Visualizer {

  @Input() array: number[] = [];
  @Input() activeBar1: number = -1
  @Input() activeBar2: number = -1
  @Input() isSorted: boolean = false;

  //convert array values to heights into percentage 
getHeight(value: number): number {
    return (value / 1000) * 100;
  }
}

import { Component ,EventEmitter, Output, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-control-panel',
  imports: [CommonModule, FormsModule],
  templateUrl: './control-panel.html',
  styleUrl: './control-panel.css',
})
export class ControlPanel {
  algorithms = ['Bubble Sort', 'Selection Sort', 'Insertion Sort', 'Merge Sort', 'Quick Sort','Heap Sort'];
  arrayTypes = ['Random', 'Sorted', 'Reverse Sorted'];

  //default values
  selectedAlgorithm: string='Bubble Sort';
  selectedArrayType: string='Random';
  arraySize: number=50;
  //isSorting: boolean=false;
  visualizationSpeed: number = 5;
  loadedFileName: string = '';

  @Input() isSorting: boolean = false; 
  @Output() generateEvent = new EventEmitter<any>();
  @Output() sortEvent = new EventEmitter<any>();
  @Output() speedChangeEvent = new EventEmitter<number>();
  @Output() fileLoadedEvent = new EventEmitter<{ fileName: string, array: number[] }>();

  onGenerate() {
    this.loadedFileName = ''; //clear the file name when generating a new array
    this.generateEvent.emit({
      algorithm: this.selectedAlgorithm,
      arrayType: this.selectedArrayType,
      arraySize: this.arraySize
    });
  }
  
  onSpeedChange() {
    this.speedChangeEvent.emit(this.visualizationSpeed);
  }

  onFileUpload(event: any) {
    const file: File = event.target.files[0];
    if(!file) return;

    this.loadedFileName = file.name;
    const reader = new FileReader();
    reader.onload = (e: any) => {
      const content = e.target.result as string;
      const parsed = content
        .split(',')
        .map(s => parseInt(s.trim(), 10))
        .filter(n => !isNaN(n));

      if (parsed.length === 0) {
        alert('File is empty or not valid commaseparated integers.');
        this.loadedFileName = '';
        return;
      }

      this.arraySize = parsed.length;
      this.fileLoadedEvent.emit({
        fileName: file.name,
        array: parsed
      });
    };

    reader.readAsText(file);
    event.target.value = ''; // reset file input to allow re-uploading the same file if needed
  }

  clearFile() {
    this.loadedFileName = '';
    this.generateEvent.emit({
      algorithm: this.selectedAlgorithm,
      arrayType: this.selectedArrayType,
      arraySize: this.arraySize
    });
  }


  onSort() {
    this.sortEvent.emit({
      algorithm: this.selectedAlgorithm,
      arrayType: this.selectedArrayType,
      arraySize: this.arraySize,
      visualizationSpeed: this.visualizationSpeed
    });
  }
}

import { Component ,OnInit ,ChangeDetectorRef  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Sorting, SortRequest } from '../../services/sorting';
import { concatMap, from, catchError, of } from 'rxjs';

@Component({
  selector: 'app-comparison-table',
  imports: [CommonModule , FormsModule], 
  templateUrl: './comparison-table.html',
  styleUrl: './comparison-table.css',
})
export class ComparisonTable implements OnInit {
  algorithms = ['Bubble Sort', 'Selection Sort', 'Insertion Sort', 'Merge Sort', 'Quick Sort' , 'Heap Sort'];
  arrayTypes= ['Random', 'Sorted', 'Reverse Sorted'];

  selectedAlgorithm: string[] = [...this.algorithms];
  arraySize: number = 100;
  numberOfRuns: number = 5;
  isLoading: boolean = false;
  results: any[] = [];
  progress: string = '';
  uploadedFiles: { name: string, array: number[] }[] = [];
  useFiles: boolean = false; 

  constructor(private sorting: Sorting, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {}

  toggleAlgorithm(algorithm: string) {
    if(this.selectedAlgorithm.includes(algorithm)) {
      this.selectedAlgorithm = this.selectedAlgorithm.filter(a => a !== algorithm);
    } else {
      this.selectedAlgorithm.push(algorithm);
    }
  }

  isSelected(algorithm: string): boolean {
    return this.selectedAlgorithm.includes(algorithm);
  }

  runComparison(){
    if(this.selectedAlgorithm.length === 0) return;
    this.isLoading = true;
    this.results = [];
    this.progress = '';
    this.cdr.detectChanges();

    let allRequests: { label: string, req: SortRequest }[] = [];

    if(this.useFiles && this.uploadedFiles.length > 0) {
      allRequests = this.selectedAlgorithm.flatMap(algorithm =>
        this.uploadedFiles.map(file => ({
          label: `${algorithm} - ${file.name}`,
          req: {
            visualizationMode: false,
            algorithm,
            arrayType: file.name,
            arraySize: file.array.length,
            numberOfRuns: this.numberOfRuns,
            initialArray: file.array
          } as SortRequest
        }))
      );
    } else {
      allRequests = this.selectedAlgorithm.flatMap(algorithm =>
        this.arrayTypes.map(type => ({
          label: `${algorithm} - ${type}`,
          req: {
            visualizationMode: false,
            algorithm,
            arrayType: type,
            arraySize: this.arraySize,
            numberOfRuns: this.numberOfRuns
          } as SortRequest
        }))
      );
    }

    const total = allRequests.length;
    let completed = 0;

    from(allRequests).pipe(
      concatMap(({ label, req }) => {
        this.progress = `Running ${label}... (${completed + 1}/${total})`;
        this.cdr.detectChanges();
        return this.sorting.getMetrics(req).pipe(
          catchError(err => {
            console.error(`Failed: ${label}`, err);
            return of({
              algorithmName: req.algorithm,
              arrayGenerationMode: req.arrayType,
              arraySize: req.arraySize,
              numberOfRuns: 0,
              averageExecutionTime: -1,
              minRunTime: -1,
              maxRunTime: -1,
              numberOfComparisons: -1,
              numberOfInterchanges: -1,
              failed: true
            });
          })
        );
      })
    ).subscribe({
      next: (response: any) => {
        completed++;
        this.results.push(response); // results appear one by one as they complete
        this.progress = `Completed ${completed}/${total}`;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
        this.isLoading = false;
        this.progress = 'Error occurred during comparison.';
        this.cdr.detectChanges();
      },
      complete: () => {
        this.isLoading = false;
        this.progress = '';
        this.cdr.detectChanges();
      }
    });
  }

  formatTime(time: number): string {
    if (time < 0) return 'N/A';
    if(time >=1_000_000) {
      return (time / 1_000_000).toFixed(2) + ' ms';
    }
    if(time >= 1_000) {
      return (time / 1_000).toFixed(2) + ' μs';
    }
    return time.toFixed(2) + ' ns';
  }

   onFilesUpload(event: any) {
    const files: FileList = event.target.files;
    if (!files || files.length === 0) return;

    this.uploadedFiles = [];
    let loaded = 0;
    const totalFiles = files.length;

    Array.from(files).forEach((file: File) => {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const content = e.target.result as string;
        const parsed = content
          .split(',')
          .map((s: string) => parseInt(s.trim(), 10))
          .filter((n: number) => !isNaN(n));

        if (parsed.length > 0) {
          this.uploadedFiles.push({ name: file.name, array: parsed });
        }

        loaded++;
        if (loaded === files.length) {
          this.useFiles = this.uploadedFiles.length > 0;
          event.target.value = '';
          this.cdr.detectChanges();
        }
      };
      reader.readAsText(file);
       // reset file input to allow re-uploading the same file if needed
    });
    
  }

  removeFile(index: number) {
    this.uploadedFiles.splice(index,1);
    if(this.uploadedFiles.length === 0) {
      this.useFiles = false;
    }
    this.cdr.detectChanges();
  }


  clearFiles() {
    this.uploadedFiles = [];
    this.useFiles = false;
    this.cdr.detectChanges();
  }
}






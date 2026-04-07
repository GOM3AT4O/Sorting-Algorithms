# 📊 Full-Stack Sorting Algorithm Visualizer

A full-stack web application that dynamically visualizes how different sorting algorithms operate in real-time. Built with a **Spring Boot (Java)** REST API engine to handle complex algorithmic math and an **Angular** frontend to render smooth, step-by-step CSS animations.

![Determined to Code](https://paulvanderlaken.com/wp-content/uploads/2017/10/qvrpgnc.gif)


## ✨ Features

* **Real-Time Visualization:** Watch arrays sort themselves frame-by-frame.
* **Algorithm Engine:** Supports 6 foundational and advanced sorting algorithms:
  * Bubble Sort $O(n^2)$
  * Selection Sort $O(n^2)$
  * Insertion Sort $O(n^2)$
  * Merge Sort $O(n \log n)$
  * Quick Sort $O(n \log n)$
  * Heap Sort $O(n \log n)$
* **Dynamic Data Generation:** Test edge cases by generating **Random**, **Already Sorted**, or **Inversely Sorted** arrays.
* **Variable Array Sizes:** Use the UI slider to seamlessly scale the array from 10 to 200 elements.
* **Performance Metrics:** The backend tracks mathematical comparisons and array interchanges for every single execution.

## 🛠️ Tech Stack

**Frontend:**
* Angular (Standalone Components)
* TypeScript
* HTML5 / CSS3 (Flexbox for dynamic graph rendering)

**Backend:**
* Java
* Spring Boot
* Maven
* REST API Architecture (Strategy Design Pattern)

## 🏗️ Architecture & How It Works

Instead of sorting the array in the browser, this application uses a true Client-Server architecture. 
1. The **Angular Client** requests a specific algorithm and array configuration.
2. The **Spring Boot Server** executes the algorithm. Instead of just returning the final sorted array, it records a snapshot of the array's state, the mathematical metrics, and the currently active indexes at every single step of the sort. 
3. The Server packages these thousands of steps into a large JSON `VisualizationStep` array.
4. The Angular Client receives the payload and runs an asynchronous playback loop, rendering the data frame-by-frame to create a smooth animation. 

## 🚀 Getting Started

To run this project locally, you will need two separate terminal windows (one for the backend, one for the frontend).

### Prerequisites
* Java JDK 17+
* Maven
* Node.js & npm
* Angular CLI (`npm install -g @angular/cli`)

### 1. Start the Backend (Spring Boot)
Open a terminal, navigate to the `visualizer` folder, and run:
```bash
cd visualizer
mvn clean spring-boot:run
```
### 1. Start the FrontEnd (Angular)
Open a terminal, navigate to the `visualizer-ui` folder, and run:
```bash
cd visualizer-ui
install npm 
ng serve

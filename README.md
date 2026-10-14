# 📚 Smart Library Management System

[![Java Version](https://img.shields.io/badge/Java-21%2B%20%2F%2025%2B-ED8B00?logo=openjdk&logoColor=white)](#)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen)](#)
[![Tests](https://img.shields.io/badge/Tests-32%2F32%20Passed-success)](#)
[![Dependencies](https://img.shields.io/badge/Dependencies-Zero%20(Pure%20Java%20SE)-blue)](#)
[![License](https://img.shields.io/badge/License-MIT-lightgrey)](#)

A high-performance in-memory library management engine and catalog processor built in pure Java. Designed with zero external dependencies, this system provides optimized algorithms for catalog de-duplication, case-insensitive title querying, price-indexed sorting, logarithmic price search, and contiguous shelf resource allocation.

---

## 🌟 Key Features

- **⚡ In-Place Catalog De-duplication**: Scans and strips duplicate records in-place without heap re-allocation ($\mathcal{O}(1)$ auxiliary space) using a two-pointer technique.
- **🔍 Case-Insensitive Catalog Search**: Substring matching engine that searches across multi-word book titles irrespective of character casing.
- **📊 Price-Based Catalog Sorting**: Selection-based sorting pipeline with active swap telemetry to organize inventory by affordability.
- **🎯 Logarithmic Price Lookup**: Sub-millisecond $\mathcal{O}(\log N)$ binary search engine to locate inventory by exact price points.
- **📐 Contiguous Allocation Engine**: Linear-time ($\mathcal{O}(N)$) two-pointer sliding window algorithm to compute the minimal contiguous book sequence on a shelf meeting a target budget.
- **🖥️ Dual Execution Modes**: Includes both an automated end-to-end demonstration pipeline and an interactive terminal menu for custom operations.

---

## 🏗️ Architecture & Modules

The system is organized into modular processing units operating on cataloged `Book` entities:

```text
┌────────────────────────────────────────────────────────────────────────┐
│                          Raw Scanned Books                             │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
                                    ▼ [Module 1: In-Place De-duplication]
┌────────────────────────────────────────────────────────────────────────┐
│                        De-duplicated Catalog                           │
└─────────────┬────────────────────────────────────────────┬─────────────┘
              │                                            │
 [Module 2]   │                                            ▼ [Module 3: Price Sort]
 Partial      │                               ┌──────────────────────────┐
 Title Search │                               │   Price-Sorted Catalog   │
              │                               └──────┬────────────┬──────┘
              │                                      │            │
              ▼                                      ▼            ▼
   ┌──────────────────────┐               ┌────────────┐┌──────────────────────────┐
   │ Substring Matches    │               │ Binary     ││ Sliding Window           │
   │ (Case-Insensitive)   │               │ Search     ││ Consecutive Allocation   │
   └──────────────────────┘               │ (O(log N)) ││ (Target Grant S)         │
                                          └────────────┘└──────────────────────────┘
```

### Module Specifications & Algorithmic Complexity

| Module | Core Functionality | Algorithm / Pattern | Time Complexity | Auxiliary Space |
| :---: | :--- | :--- | :---: | :---: |
| **Module 1** | In-Place De-duplication | Two-Pointer Array Filtering | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ |
| **Module 2** | Substring Catalog Search | Case-Insensitive Linear Scan | $\mathcal{O}(N \times M)$ | $\mathcal{O}(1)$ |
| **Module 3** | Price-Based Sorter | Selection Sort with Swap Tracking | $\mathcal{O}(N^2)$ | $\mathcal{O}(1)$ |
| **Module 4** | Exact Price Lookup | Binary Search | $\mathcal{O}(\log N)$ | $\mathcal{O}(1)$ |
| **Module 5** | Contiguous Shelf Allocator | Dynamic Sliding Window | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ |

---

## 💻 Technical Deep-Dive

### 1. In-Place De-duplication
Duplicate barcode scans are purged in-place along an array ordered by `bookId`. By keeping a write pointer `uniqueIdx` and advancing a read pointer $i$, elements are shifted without creating auxiliary arrays, guaranteeing $\mathcal{O}(1)$ additional memory overhead.

### 2. Case-Insensitive Search
Titles and queries are evaluated through a normalized case-insensitive containment check, enabling partial keywords (e.g., `"data"` or `"JAVA"`) to retrieve relevant catalog entries regardless of input casing.

### 3. Selection Sort & Telemetry
Organizes books in ascending order of price ($lowest \rightarrow highest$). Scans the unsorted partition `[i ... count-1]` to identify the minimum price element, swaps it into position `i`, and increments a telemetry counter to monitor sorting efficiency.

### 4. Binary Search by Price
Once sorted by price, queries for exact price values are resolved in logarithmic time $\mathcal{O}(\log N)$. Searches cut the candidate space in half at each step, returning the exact zero-based index or `-1` if unavailable.

### 5. Optimal Shelf Allocation (Sliding Window)
To find the smallest sequence of contiguous books on a shelf whose combined price meets or exceeds a target budget $S$:
1. Expands window boundary `right` while accumulating `currentSum`.
2. As soon as `currentSum >= targetCost`, records window length (`right - left + 1`) and contracts from `left` to locate the minimal viable sequence.
3. Yields $\mathcal{O}(N)$ amortized runtime with $\mathcal{O}(1)$ auxiliary space.

---

## 🧪 System Demonstration Pipeline Output

Running the system produces the following end-to-end execution pipeline:

```text
===============================================================
          SMART LIBRARY MANAGEMENT SYSTEM - PIPELINE           
===============================================================

Initial Input Array (6 Books with 1 Duplicate):
Book 1: ID = 101, Title = "Data Structures", Author = "Mark", Price = 400.0
Book 2: ID = 101, Title = "Data Structures", Author = "Mark", Price = 400.0 <-- (Duplicate)
Book 3: ID = 102, Title = "Java Basics", Author = "James", Price = 300.0
Book 4: ID = 103, Title = "Python Guide", Author = "Guido", Price = 600.0
Book 5: ID = 104, Title = "Database Systems", Author = "Raghu", Price = 500.0
Book 6: ID = 105, Title = "Computer Networks", Author = "Andrew", Price = 700.0

---------------------------------------------------------------
1. After Task 1 (Remove Duplicates):
Unique Books Count: 5
Book List:
[101] Data Structures - Rs. 400.0
[102] Java Basics - Rs. 300.0
[103] Python Guide - Rs. 600.0
[104] Database Systems - Rs. 500.0
[105] Computer Networks - Rs. 700.0

---------------------------------------------------------------
2. After Task 2 (Search Query: "data"):
Search Results for 'data':
- Found: [101] Data Structures (Rs. 400.0)
- Found: [104] Database Systems (Rs. 500.0)

---------------------------------------------------------------
3. After Task 3 (Sort by Price):
Books Sorted by Price:
1. [102] Java Basics - Rs. 300.0
2. [101] Data Structures - Rs. 400.0
3. [104] Database Systems - Rs. 500.0
4. [103] Python Guide - Rs. 600.0
5. [105] Computer Networks - Rs. 700.0
Total Swaps: 2

---------------------------------------------------------------
4. After Task 4 (Search for Price: 500.0):
Searching for Price Rs. 500.0...
Result: Book found at index 2: [104] Database Systems (Rs. 500.0)

---------------------------------------------------------------
5. After Task 5 (Sliding Window for Target Cost S = Rs. 1000.0):
Finding minimum consecutive books whose total price >= Rs. 1000.0...
Minimum Consecutive Books Needed: 2

Explanation:
- Window 1 (Index 2 to 3):
[104] Database Systems (Rs. 500.0) + [103] Python Guide (Rs. 600.0)
Total = Rs. 1100.0 (>= 1000.0) -> Length: 2 books

- Window 2 (Index 3 to 4):
[103] Python Guide (Rs. 600.0) + [105] Computer Networks (Rs. 700.0)
Total = Rs. 1300.0 (>= 1000.0) -> Length: 2 books
```

---

## 📁 Repository Structure

```
├── src/
│   ├── Book.java                     # Catalog Book entity model
│   ├── LibraryManagementSystem.java  # Core engine, algorithmic modules & CLI
│   └── LibraryManagementTest.java    # Automated unit test suite (32 assertions)
├── run.bat                           # Compile & run pipeline (Windows)
├── test.bat                          # Automated test runner (Windows)
├── .gitignore                        # Git ignore patterns for Java & IDE files
└── README.md                         # Documentation
```

---

## 🚀 Getting Started

### Prerequisites
- **Java SE Development Kit (JDK)**: Version 8 or higher (Tested on JDK 21 and 25).

### 1. Run Automated Demonstration Pipeline
**Windows Batch:**
```cmd
run.bat
```
**Standard Java Shell:**
```bash
javac -d bin src/*.java
java -cp bin LibraryManagementSystem
```

### 2. Launch Interactive Terminal Mode
```bash
java -cp bin LibraryManagementSystem --interactive
```
*Provides a menu-driven interface to add books, perform real-time searches, test custom price sorts, and evaluate custom budget thresholds.*

### 3. Run Test Suite
**Windows Batch:**
```cmd
test.bat
```
**Standard Java Shell:**
```bash
javac -d bin src/*.java
java -cp bin LibraryManagementTest
```

---

## 📄 License
This project is licensed under the [MIT License](LICENSE).

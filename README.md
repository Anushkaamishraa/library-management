# 📚 Smart Library Management System (DSA Practical Assignment)

[![Java Version](https://img.shields.io/badge/Java-21%2B%20%2F%2025%2B-ED8B00?logo=openjdk&logoColor=white)](#)
[![DSA](https://img.shields.io/badge/Course-Data%20Structures%20%26%20Algorithms-blue)](#)
[![Tests](https://img.shields.io/badge/Tests-32%2F32%20Passed-brightgreen)](#)
[![License](https://img.shields.io/badge/License-MIT-lightgrey)](#)

A high-performance Java implementation of the **Smart Library Management System** designed for Computer Science & Engineering students as part of the Data Structures & Algorithms (DSA) Practical Curriculum.

---

## 📖 Real-World Scenario

You are hired by your college library to build a **Smart Library Management System**. The library has hundreds of books arranged on shelves. Students use a computer terminal to:

1. **Clean up duplicate book records** scanned inadvertently by barcode readers.
2. **Search for books** by typing part of the book title (e.g., typing `"java"` to match `"Java Programming"` or `"ADVANCED JAVA"`).
3. **Sort books by price** in ascending order so students can browse affordable books first.
4. **Quickly verify** if a book with an exact price exists using logarithmic search.
5. **Help research students** find the minimum number of consecutive books on a shelf whose combined value meets or exceeds a target research grant amount $S$.

---

## 🏗️ Data Model

The system operates on an array of `Book` objects:

```java
public class Book {
    public int bookId;
    public String title;
    public String author;
    public double price;

    public Book(int bookId, String title, String author, double price) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
    }
}
```

---

## 📋 Task Breakdown & Algorithmic Analysis

| Task # | Task Name | Technique | Time Complexity | Auxiliary Space | Method Signature |
| :---: | :--- | :--- | :---: | :---: | :--- |
| **1** | Remove Duplicates In-Place | Two-Pointer Array Manipulation | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | `int removeDuplicates(Book[] books, int n)` |
| **2** | Partial Title Search | Case-Insensitive Linear Scan | $\mathcal{O}(N \times M)$ | $\mathcal{O}(1)$ | `void searchByTitle(Book[] books, int count, String query)` |
| **3** | Sort by Price | Selection Sort (Tracks Swaps) | $\mathcal{O}(N^2)$ | $\mathcal{O}(1)$ | `void sortByPrice(Book[] books, int count)` |
| **4** | Search Book by Price | Binary Search | $\mathcal{O}(\log N)$ | $\mathcal{O}(1)$ | `int searchByPrice(Book[] books, int count, double targetPrice)` |
| **5** | Minimum Consecutive Books | Dynamic Sliding Window | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | `int minBooksForTargetCost(Book[] books, int count, double targetCost)` |

---

### Task Details

#### Task 1: Remove Duplicate Books In-Place
- **Problem**: When books are scanned into the system, the scanner occasionally duplicates records. Given an array of books sorted by `bookId`, remove duplicates **in-place** so each book appears only once.
- **Constraints**: No new array allocation ($\mathcal{O}(1)$ extra memory). Returns the new unique count $M$.
- **Mechanism**: Slow-fast pointer approach. `uniqueIdx` tracks the boundary of verified unique elements; iterates $i$ from $1$ to $n-1$, updating `books[++uniqueIdx] = books[i]` when a distinct `bookId` is encountered.

#### Task 2: Case-Insensitive Partial Title Search
- **Problem**: Students often recall only a fragment of a book title. Search through the array and print all books containing the search substring regardless of letter case.
- **Mechanism**: Normalizes both search query and titles with `.toLowerCase()` and evaluates substring containment.

#### Task 3: Sort by Price (Selection Sort)
- **Problem**: Sort the unique books in ascending order of price (cheapest book first).
- **Mechanism**: Selection sort repeatedly finds the minimum element in the unsorted subarray `[i ... count-1]` and swaps it into position `i`. The algorithm tracks and prints the exact number of swaps performed (`Total Swaps: X`).

#### Task 4: Fast Search by Price (Binary Search)
- **Problem**: Efficiently query if a book with exact price `targetPrice` exists in the price-sorted array.
- **Mechanism**: Classic binary search with `low`, `high`, and `mid` pointers operating in logarithmic time $\mathcal{O}(\log N)$. Returns the 0-based index if located, or `-1` if absent.

#### Task 5: Minimum Consecutive Books for Research Grant (Sliding Window)
- **Problem**: A research student needs to borrow a sequence of consecutive books from a library shelf whose total value meets or exceeds research grant amount $S$. Find the **minimum length** of such a contiguous subarray.
- **Mechanism**:
  - Two-pointer sliding window (`left` and `right`).
  - Expand window rightward by adding `books[right].price` to `currentSum`.
  - While `currentSum >= targetCost`, record minimum length (`right - left + 1`) and contract window from `left`.
  - Returns `0` if no contiguous subarray reaches $S$. Runs in optimal $\mathcal{O}(N)$ time and $\mathcal{O}(1)$ auxiliary space.

---

## 🧪 Verified Sample Test Case

### Initial Input Array (6 Books with 1 Duplicate):
```text
Book 1: ID = 101, Title = "Data Structures", Author = "Mark", Price = 400.0
Book 2: ID = 101, Title = "Data Structures", Author = "Mark", Price = 400.0 <-- (Duplicate)
Book 3: ID = 102, Title = "Java Basics", Author = "James", Price = 300.0
Book 4: ID = 103, Title = "Python Guide", Author = "Guido", Price = 600.0
Book 5: ID = 104, Title = "Database Systems", Author = "Raghu", Price = 500.0
Book 6: ID = 105, Title = "Computer Networks", Author = "Andrew", Price = 700.0
```

### Execution Output:

```text
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
│   ├── Book.java                     # Book entity data model
│   ├── LibraryManagementSystem.java  # Main algorithm implementation & CLI
│   └── LibraryManagementTest.java    # Automated test suite (32 assertions)
├── run.bat                           # Compile & run script (Windows)
├── test.bat                          # Automated test execution script (Windows)
├── .gitignore                        # Git ignore rules for Java & IDE files
├── Library_Management_Scenario_Questions.pdf # Assignment specification document
└── README.md                         # Project documentation
```

---

## 🚀 How to Compile & Run

### Prerequisites
- JDK 8 or higher (Tested on Java 21 / 25)

### 1. Run Automated Test Case (Default)
**Using the batch file:**
```cmd
run.bat
```
**Or using standard Java commands:**
```bash
javac -d bin src/*.java
java -cp bin LibraryManagementSystem
```

### 2. Run Interactive Console Menu
```bash
java -cp bin LibraryManagementSystem --interactive
```
*Allows you to add custom books, search, sort, and test custom grant thresholds.*

### 3. Run Comprehensive Test Suite
**Using the batch file:**
```cmd
test.bat
```
**Or using standard Java commands:**
```bash
javac -d bin src/*.java
java -cp bin LibraryManagementTest
```

---

## 📤 Upload to GitHub Guide

Follow these commands to push this repository to your GitHub account:

1. **Create an empty repository** on GitHub (e.g., named `smart-library-management-system`).
2. Open terminal in this project folder and run:

```bash
# 1. Initialize git (if not already done)
git init -b main

# 2. Stage all project files
git add .

# 3. Commit the changes
git commit -m "feat: complete DSA library management system with all 5 tasks and unit tests"

# 4. Link your GitHub remote repository (replace with your repo URL)
git remote add origin https://github.com/<YOUR-USERNAME>/<YOUR-REPO-NAME>.git

# 5. Push to GitHub
git push -u origin main
```

---

## 👩‍💻 Author & Acknowledgments
- **Assignment**: DSA Practical Assignment - Library Management System
- **Topic**: In-place manipulation, two pointers, selection sort, binary search, and sliding window algorithms.

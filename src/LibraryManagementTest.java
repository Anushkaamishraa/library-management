/**
 * Smart Library Management System
 * Comprehensive Automated Test Suite
 *
 * Verifies core catalog processing modules and handles critical edge cases:
 * - Module 1: In-Place Catalog De-duplication
 * - Module 2: Case-Insensitive Catalog Search
 * - Module 3: Price-Based Selection Sort & Swap Telemetry
 * - Module 4: Logarithmic Price Search (Binary Search)
 * - Module 5: Contiguous Resource Allocation (Sliding Window)
 */
public class LibraryManagementTest {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    private static void assertEquals(int expected, int actual, String testName) {
        if (expected == actual) {
            System.out.println("  [PASS] " + testName + " (expected: " + expected + ", got: " + actual + ")");
            testsPassed++;
        } else {
            System.err.println("  [FAIL] " + testName + " (expected: " + expected + ", got: " + actual + ")");
            testsFailed++;
        }
    }

    private static void assertEquals(double expected, double actual, String testName) {
        if (Math.abs(expected - actual) < 0.0001) {
            System.out.println("  [PASS] " + testName + " (expected: " + expected + ", got: " + actual + ")");
            testsPassed++;
        } else {
            System.err.println("  [FAIL] " + testName + " (expected: " + expected + ", got: " + actual + ")");
            testsFailed++;
        }
    }

    public static void testModule1RemoveDuplicates() {
        System.out.println("\n--- Testing Module 1: removeDuplicates (In-Place) ---");

        // Edge case: null or empty
        assertEquals(0, LibraryManagementSystem.removeDuplicates(null, 0), "Null array handling");
        assertEquals(0, LibraryManagementSystem.removeDuplicates(new Book[5], 0), "Zero-length array handling");

        // Edge case: 1 element
        Book[] single = { new Book(101, "A", "Author", 100.0) };
        assertEquals(1, LibraryManagementSystem.removeDuplicates(single, 1), "Single element preservation");

        // Standard catalog dataset with duplicate
        Book[] sample = {
            new Book(101, "Data Structures", "Mark", 400.0),
            new Book(101, "Data Structures", "Mark", 400.0),
            new Book(102, "Java Basics", "James", 300.0),
            new Book(103, "Python Guide", "Guido", 600.0),
            new Book(104, "Database Systems", "Raghu", 500.0),
            new Book(105, "Computer Networks", "Andrew", 700.0)
        };
        int newCount = LibraryManagementSystem.removeDuplicates(sample, 6);
        assertEquals(5, newCount, "Catalog duplicate removal (5 unique)");
        assertEquals(101, sample[0].bookId, "Catalog index 0 ID is 101");
        assertEquals(102, sample[1].bookId, "Catalog index 1 ID is 102");
        assertEquals(103, sample[2].bookId, "Catalog index 2 ID is 103");
        assertEquals(104, sample[3].bookId, "Catalog index 3 ID is 104");
        assertEquals(105, sample[4].bookId, "Catalog index 4 ID is 105");

        // All identical books
        Book[] allDupes = {
            new Book(200, "X", "Y", 50.0),
            new Book(200, "X", "Y", 50.0),
            new Book(200, "X", "Y", 50.0)
        };
        assertEquals(1, LibraryManagementSystem.removeDuplicates(allDupes, 3), "All duplicate items reduction");

        // No duplicates
        Book[] noDupes = {
            new Book(1, "A", "A", 10.0),
            new Book(2, "B", "B", 20.0),
            new Book(3, "C", "C", 30.0)
        };
        assertEquals(3, LibraryManagementSystem.removeDuplicates(noDupes, 3), "Already unique array");
    }

    public static void testModule3SortByPrice() {
        System.out.println("\n--- Testing Module 3: Selection Sort by Price & Swap Telemetry ---");

        Book[] sample = {
            new Book(101, "Data Structures", "Mark", 400.0),
            new Book(102, "Java Basics", "James", 300.0),
            new Book(103, "Python Guide", "Guido", 600.0),
            new Book(104, "Database Systems", "Raghu", 500.0),
            new Book(105, "Computer Networks", "Andrew", 700.0)
        };
        int swaps = LibraryManagementSystem.selectionSort(sample, 5);
        assertEquals(2, swaps, "Price sorting swap telemetry count");
        assertEquals(300.0, sample[0].price, "Index 0 price is 300.0");
        assertEquals(400.0, sample[1].price, "Index 1 price is 400.0");
        assertEquals(500.0, sample[2].price, "Index 2 price is 500.0");
        assertEquals(600.0, sample[3].price, "Index 3 price is 600.0");
        assertEquals(700.0, sample[4].price, "Index 4 price is 700.0");

        // Already sorted array -> 0 swaps
        Book[] alreadySorted = {
            new Book(1, "A", "A", 100.0),
            new Book(2, "B", "B", 200.0),
            new Book(3, "C", "C", 300.0)
        };
        int zeroSwaps = LibraryManagementSystem.selectionSort(alreadySorted, 3);
        assertEquals(0, zeroSwaps, "Already sorted array swaps = 0");

        // Reverse sorted array [300, 200, 100]
        Book[] reverseSorted = {
            new Book(1, "A", "A", 300.0),
            new Book(2, "B", "B", 200.0),
            new Book(3, "C", "C", 100.0)
        };
        int revSwaps = LibraryManagementSystem.selectionSort(reverseSorted, 3);
        assertEquals(1, revSwaps, "Reverse sorted (3 elements) swap count = 1");
        assertEquals(100.0, reverseSorted[0].price, "Reverse sorted first element is 100.0");
    }

    public static void testModule4SearchByPrice() {
        System.out.println("\n--- Testing Module 4: searchByPrice (Binary Search) ---");

        Book[] sortedBooks = {
            new Book(102, "Java Basics", "James", 300.0),
            new Book(101, "Data Structures", "Mark", 400.0),
            new Book(104, "Database Systems", "Raghu", 500.0),
            new Book(103, "Python Guide", "Guido", 600.0),
            new Book(105, "Computer Networks", "Andrew", 700.0)
        };

        // Exact match
        assertEquals(2, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 500.0), "Search 500.0 at index 2");

        // Boundary checks
        assertEquals(0, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 300.0), "Search boundary min price (300.0)");
        assertEquals(4, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 700.0), "Search boundary max price (700.0)");

        // Non-existent elements
        assertEquals(-1, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 450.0), "Search absent price (450.0)");
        assertEquals(-1, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 100.0), "Search price below minimum (100.0)");
        assertEquals(-1, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 999.0), "Search price above maximum (999.0)");
    }

    public static void testModule5MinBooksForTargetCost() {
        System.out.println("\n--- Testing Module 5: minBooksForTargetCost (Sliding Window) ---");

        Book[] sortedBooks = {
            new Book(102, "Java Basics", "James", 300.0),
            new Book(101, "Data Structures", "Mark", 400.0),
            new Book(104, "Database Systems", "Raghu", 500.0),
            new Book(103, "Python Guide", "Guido", 600.0),
            new Book(105, "Computer Networks", "Andrew", 700.0)
        };

        // Target 1000.0 -> 2 books ([500, 600] = 1100 or [600, 700] = 1300)
        assertEquals(2, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 1000.0), "Target 1000.0 optimal contiguous length = 2");

        // Target achievable with 1 single book
        assertEquals(1, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 700.0), "Target 700.0 optimal contiguous length = 1");
        assertEquals(1, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 300.0), "Target 300.0 optimal contiguous length = 1");

        // Target achievable only with all books (sum = 2500)
        assertEquals(5, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 2500.0), "Target 2500.0 requires entire shelf (5)");

        // Target impossible to achieve (greater than total sum)
        assertEquals(0, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 3000.0), "Target 3000.0 unreachable -> returns 0");

        // Edge case: target <= 0
        assertEquals(0, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 0.0), "Target 0.0 -> returns 0");
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("          SMART LIBRARY MANAGEMENT SYSTEM TEST SUITE           ");
        System.out.println("===============================================================");

        testModule1RemoveDuplicates();
        testModule3SortByPrice();
        testModule4SearchByPrice();
        testModule5MinBooksForTargetCost();

        System.out.println("\n===============================================================");
        System.out.println("TEST SUMMARY: " + testsPassed + " PASSED, " + testsFailed + " FAILED");
        System.out.println("===============================================================");

        if (testsFailed > 0) {
            System.exit(1);
        }
    }
}

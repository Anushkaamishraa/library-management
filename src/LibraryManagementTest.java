/**
 * Comprehensive Test Suite for LibraryManagementSystem
 * Verifies all 5 tasks and handles various edge cases.
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

    public static void testTask1RemoveDuplicates() {
        System.out.println("\nTesting Task 1: removeDuplicates");

        // Edge case: null or empty
        assertEquals(0, LibraryManagementSystem.removeDuplicates(null, 0), "Null array");
        assertEquals(0, LibraryManagementSystem.removeDuplicates(new Book[5], 0), "Zero count");

        // Edge case: 1 element
        Book[] single = { new Book(101, "A", "Author", 100.0) };
        assertEquals(1, LibraryManagementSystem.removeDuplicates(single, 1), "Single element");

        // Assignment sample case: 6 books with 1 duplicate
        Book[] sample = {
            new Book(101, "Data Structures", "Mark", 400.0),
            new Book(101, "Data Structures", "Mark", 400.0),
            new Book(102, "Java Basics", "James", 300.0),
            new Book(103, "Python Guide", "Guido", 600.0),
            new Book(104, "Database Systems", "Raghu", 500.0),
            new Book(105, "Computer Networks", "Andrew", 700.0)
        };
        int newCount = LibraryManagementSystem.removeDuplicates(sample, 6);
        assertEquals(5, newCount, "Sample case duplicate removal (5 unique)");
        assertEquals(101, sample[0].bookId, "First book ID is 101");
        assertEquals(102, sample[1].bookId, "Second book ID is 102");
        assertEquals(103, sample[2].bookId, "Third book ID is 103");
        assertEquals(104, sample[3].bookId, "Fourth book ID is 104");
        assertEquals(105, sample[4].bookId, "Fifth book ID is 105");

        // All identical books
        Book[] allDupes = {
            new Book(200, "X", "Y", 50.0),
            new Book(200, "X", "Y", 50.0),
            new Book(200, "X", "Y", 50.0)
        };
        assertEquals(1, LibraryManagementSystem.removeDuplicates(allDupes, 3), "All duplicate books");

        // No duplicates
        Book[] noDupes = {
            new Book(1, "A", "A", 10.0),
            new Book(2, "B", "B", 20.0),
            new Book(3, "C", "C", 30.0)
        };
        assertEquals(3, LibraryManagementSystem.removeDuplicates(noDupes, 3), "No duplicate books");
    }

    public static void testTask3SortByPrice() {
        System.out.println("\nTesting Task 3: Selection Sort by Price & Swap Count");

        Book[] sample = {
            new Book(101, "Data Structures", "Mark", 400.0),
            new Book(102, "Java Basics", "James", 300.0),
            new Book(103, "Python Guide", "Guido", 600.0),
            new Book(104, "Database Systems", "Raghu", 500.0),
            new Book(105, "Computer Networks", "Andrew", 700.0)
        };
        // Expected swaps from PDF: 2
        int swaps = LibraryManagementSystem.selectionSort(sample, 5);
        assertEquals(2, swaps, "Sample case selection sort swaps");
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
        assertEquals(1, revSwaps, "Reverse sorted (3 elements) swap min with first -> 1 swap");
        assertEquals(100.0, reverseSorted[0].price, "Reverse sorted first element is 100.0");
    }

    public static void testTask4SearchByPrice() {
        System.out.println("\nTesting Task 4: searchByPrice (Binary Search)");

        Book[] sortedBooks = {
            new Book(102, "Java Basics", "James", 300.0),
            new Book(101, "Data Structures", "Mark", 400.0),
            new Book(104, "Database Systems", "Raghu", 500.0),
            new Book(103, "Python Guide", "Guido", 600.0),
            new Book(105, "Computer Networks", "Andrew", 700.0)
        };

        // Target 500.0 exists at index 2 (from PDF)
        assertEquals(2, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 500.0), "Search 500.0 at index 2");

        // First element
        assertEquals(0, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 300.0), "Search first element (300.0)");

        // Last element
        assertEquals(4, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 700.0), "Search last element (700.0)");

        // Element not in array
        assertEquals(-1, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 450.0), "Search absent price (450.0)");
        assertEquals(-1, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 100.0), "Search price below min (100.0)");
        assertEquals(-1, LibraryManagementSystem.searchByPrice(sortedBooks, 5, 999.0), "Search price above max (999.0)");
    }

    public static void testTask5MinBooksForTargetCost() {
        System.out.println("\nTesting Task 5: minBooksForTargetCost (Sliding Window)");

        Book[] sortedBooks = {
            new Book(102, "Java Basics", "James", 300.0),
            new Book(101, "Data Structures", "Mark", 400.0),
            new Book(104, "Database Systems", "Raghu", 500.0),
            new Book(103, "Python Guide", "Guido", 600.0),
            new Book(105, "Computer Networks", "Andrew", 700.0)
        };

        // Sample case from PDF: target 1000.0 -> 2 books ([500, 600] = 1100 or [600, 700] = 1300)
        assertEquals(2, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 1000.0), "Target 1000.0 requires min 2 books");

        // Target achievable with 1 book
        assertEquals(1, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 700.0), "Target 700.0 requires min 1 book");
        assertEquals(1, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 300.0), "Target 300.0 requires min 1 book");

        // Target achievable only with all books (sum = 2500)
        assertEquals(5, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 2500.0), "Target 2500.0 requires all 5 books");

        // Target impossible to achieve (greater than total sum)
        assertEquals(0, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 3000.0), "Target 3000.0 impossible -> 0");

        // Edge case: target <= 0
        assertEquals(0, LibraryManagementSystem.minBooksForTargetCost(sortedBooks, 5, 0.0), "Target 0.0 -> 0");
    }

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("       RUNNING COMPREHENSIVE AUTOMATED TEST SUITE              ");
        System.out.println("===============================================================");

        testTask1RemoveDuplicates();
        testTask3SortByPrice();
        testTask4SearchByPrice();
        testTask5MinBooksForTargetCost();

        System.out.println("\n===============================================================");
        System.out.println("TEST SUMMARY: " + testsPassed + " PASSED, " + testsFailed + " FAILED");
        System.out.println("===============================================================");

        if (testsFailed > 0) {
            System.exit(1);
        }
    }
}

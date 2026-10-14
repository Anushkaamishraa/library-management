import java.util.Scanner;

/**
 * Smart Library Management System
 *
 * A high-performance, in-memory library management engine and catalog processor.
 *
 * Core Processing Modules:
 * 1. In-Place Catalog De-duplication (Two-Pointer Technique - O(N) time, O(1) space)
 * 2. Case-Insensitive Catalog Search Engine (O(N * M) time, O(1) space)
 * 3. Price-Based Sorting Engine (Selection Sort with swap telemetry)
 * 4. High-Performance Price Query (Binary Search - O(log N) time)
 * 5. Optimal Consequential Resource Allocation (Sliding Window - O(N) time, O(1) space)
 */
public class LibraryManagementSystem {

    // =========================================================================
    // MODULE 1: In-Place Catalog De-duplication
    // =========================================================================
    /**
     * Cleans up duplicate catalog entries scanned into the system.
     * Given an array of books sorted by bookId, removes duplicates in-place
     * so that each unique book appears only once.
     *
     * @param books Array of Book objects sorted by bookId
     * @param n     Initial number of elements in books array
     * @return New count of unique books M
     *
     * Time Complexity: O(N)
     * Space Complexity: O(1) auxiliary space
     */
    public static int removeDuplicates(Book[] books, int n) {
        if (books == null || n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        int uniqueIdx = 0;
        for (int i = 1; i < n; i++) {
            if (books[i].bookId != books[uniqueIdx].bookId) {
                uniqueIdx++;
                books[uniqueIdx] = books[i];
            }
        }
        return uniqueIdx + 1;
    }

    // =========================================================================
    // MODULE 2: Case-Insensitive Partial Title Search
    // =========================================================================
    /**
     * Searches through the catalog and locates all books whose titles contain
     * the specified query substring, regardless of letter casing.
     *
     * @param books Array of Book objects
     * @param count Number of active books in array
     * @param query Search keyword/substring
     *
     * Time Complexity: O(count * length of title)
     * Space Complexity: O(1) auxiliary space
     */
    public static void searchByTitle(Book[] books, int count, String query) {
        System.out.println("Search Results for '" + query + "':");
        if (books == null || count <= 0 || query == null || query.trim().isEmpty()) {
            System.out.println("  (No books found matching '" + query + "')");
            return;
        }

        String lowerQuery = query.toLowerCase();
        boolean found = false;

        for (int i = 0; i < count; i++) {
            if (books[i] != null && books[i].title != null) {
                if (books[i].title.toLowerCase().contains(lowerQuery)) {
                    System.out.println("- Found: [" + books[i].bookId + "] " 
                            + books[i].title + " (Rs. " + books[i].price + ")");
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("  (No books found matching '" + query + "')");
        }
    }

    // =========================================================================
    // MODULE 3: Price-Based Catalog Sorting
    // =========================================================================
    /**
     * Sorts the catalog in ascending order of price (most affordable books first)
     * using Selection Sort and tracks total swap operations performed.
     *
     * @param books Array of Book objects
     * @param count Number of active books in array
     *
     * Time Complexity: O(N^2)
     * Space Complexity: O(1) auxiliary space
     */
    public static void sortByPrice(Book[] books, int count) {
        if (books == null || count <= 1) {
            System.out.println("Books Sorted by Price:");
            if (books != null && count == 1) {
                System.out.println("1. " + books[0]);
            }
            System.out.println("Total Swaps: 0");
            return;
        }

        int swaps = 0;
        for (int i = 0; i < count - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < count; j++) {
                if (books[j].price < books[minIdx].price) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                Book temp = books[i];
                books[i] = books[minIdx];
                books[minIdx] = temp;
                swaps++;
            }
        }

        System.out.println("Books Sorted by Price:");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + books[i]);
        }
        System.out.println("Total Swaps: " + swaps);
    }

    /**
     * Internal helper to perform selection sort and return swap telemetry count.
     */
    public static int selectionSort(Book[] books, int count) {
        if (books == null || count <= 1) return 0;
        int swaps = 0;
        for (int i = 0; i < count - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < count; j++) {
                if (books[j].price < books[minIdx].price) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                Book temp = books[i];
                books[i] = books[minIdx];
                books[minIdx] = temp;
                swaps++;
            }
        }
        return swaps;
    }

    // =========================================================================
    // MODULE 4: High-Performance Exact Price Query (Binary Search)
    // =========================================================================
    /**
     * Performs a logarithmic time search for a book with an exact target price
     * on an array pre-sorted by price.
     *
     * @param books       Array of Book objects sorted ascending by price
     * @param count       Number of active books in array
     * @param targetPrice Exact price to search for
     * @return 0-based index if located, or -1 if not found
     *
     * Time Complexity: O(log N)
     * Space Complexity: O(1) auxiliary space
     */
    public static int searchByPrice(Book[] books, int count, double targetPrice) {
        if (books == null || count <= 0) {
            return -1;
        }

        int low = 0;
        int high = count - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (Double.compare(books[mid].price, targetPrice) == 0) {
                return mid;
            } else if (books[mid].price < targetPrice) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    // =========================================================================
    // MODULE 5: Optimal Consecutive Allocation Engine (Sliding Window)
    // =========================================================================
    /**
     * Determines the minimum number of consecutive shelf books whose combined
     * monetary value meets or exceeds the required target budget/cost.
     *
     * Employs an optimal Sliding Window (Two Pointers) algorithm:
     * - Expands the search window by advancing the right pointer.
     * - Once currentSum >= targetCost, records the minimal span length and contracts
     *   from the left pointer to find the most compact contiguous set.
     *
     * @param books      Array of Book objects on shelf
     * @param count      Number of active books in array
     * @param targetCost Minimum required combined monetary value
     * @return Minimum length of contiguous books meeting targetCost, or 0 if unreachable.
     *
     * Time Complexity: O(N)
     * Space Complexity: O(1) auxiliary space
     */
    public static int minBooksForTargetCost(Book[] books, int count, double targetCost) {
        if (books == null || count <= 0 || targetCost <= 0) {
            return 0;
        }

        int minLen = Integer.MAX_VALUE;
        double currentSum = 0.0;
        int left = 0;

        for (int right = 0; right < count; right++) {
            currentSum += books[right].price;

            while (currentSum >= targetCost) {
                int currentLen = right - left + 1;
                if (currentLen < minLen) {
                    minLen = currentLen;
                }
                currentSum -= books[left].price;
                left++;
            }
        }

        return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
    }

    /**
     * Helper to display detailed breakdown of eligible contiguous shelf windows.
     */
    public static void printSlidingWindowExplanation(Book[] books, int count, double targetCost, int minLength) {
        if (minLength <= 0 || books == null || count < minLength) {
            System.out.println("No contiguous shelf sequence meets the target budget of Rs. " + targetCost);
            return;
        }

        System.out.println("\nExplanation:");
        int windowCount = 1;

        for (int i = 0; i <= count - minLength; i++) {
            double sum = 0.0;
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < i + minLength; j++) {
                sum += books[j].price;
                if (sb.length() > 0) sb.append(" + ");
                sb.append("[").append(books[j].bookId).append("] ")
                  .append(books[j].title).append(" (Rs. ").append(books[j].price).append(")");
            }

            if (sum >= targetCost) {
                System.out.println("- Window " + windowCount + " (Index " + i + " to " + (i + minLength - 1) + "):");
                System.out.println(sb.toString());
                System.out.println("Total = Rs. " + sum + " (>= " + targetCost + ") -> Length: " + minLength + " books\n");
                windowCount++;
            }
        }
    }

    /**
     * Runs the standard system demonstration pipeline.
     */
    public static void runSampleTestCase() {
        System.out.println("===============================================================");
        System.out.println("          SMART LIBRARY MANAGEMENT SYSTEM - PIPELINE           ");
        System.out.println("===============================================================\n");

        // Initial Input Array (6 Books with 1 Duplicate)
        Book[] books = new Book[10];
        books[0] = new Book(101, "Data Structures", "Mark", 400.0);
        books[1] = new Book(101, "Data Structures", "Mark", 400.0); // Duplicate
        books[2] = new Book(102, "Java Basics", "James", 300.0);
        books[3] = new Book(103, "Python Guide", "Guido", 600.0);
        books[4] = new Book(104, "Database Systems", "Raghu", 500.0);
        books[5] = new Book(105, "Computer Networks", "Andrew", 700.0);
        int totalBooks = 6;

        System.out.println("Initial Input Array (6 Books with 1 Duplicate):");
        for (int i = 0; i < totalBooks; i++) {
            String duplicateNote = (i == 1) ? " <-- (Duplicate)" : "";
            System.out.println("Book " + (i + 1) + ": ID = " + books[i].bookId 
                    + ", Title = \"" + books[i].title + "\", Author = \"" + books[i].author 
                    + "\", Price = " + books[i].price + duplicateNote);
        }
        System.out.println();

        // 1. After Task 1 (Remove Duplicates)
        System.out.println("---------------------------------------------------------------");
        System.out.println("1. After Task 1 (Remove Duplicates):");
        int uniqueCount = removeDuplicates(books, totalBooks);
        System.out.println("Unique Books Count: " + uniqueCount);
        System.out.println("Book List:");
        for (int i = 0; i < uniqueCount; i++) {
            System.out.println(books[i]);
        }
        System.out.println();

        // 2. After Task 2 (Search Query: "data")
        System.out.println("---------------------------------------------------------------");
        System.out.println("2. After Task 2 (Search Query: \"data\"):");
        searchByTitle(books, uniqueCount, "data");
        System.out.println();

        // 3. After Task 3 (Sort by Price)
        System.out.println("---------------------------------------------------------------");
        System.out.println("3. After Task 3 (Sort by Price):");
        sortByPrice(books, uniqueCount);
        System.out.println();

        // 4. After Task 4 (Search for Price: 500.0)
        System.out.println("---------------------------------------------------------------");
        System.out.println("4. After Task 4 (Search for Price: 500.0):");
        double searchPrice = 500.0;
        System.out.println("Searching for Price Rs. " + searchPrice + "...");
        int foundIdx = searchByPrice(books, uniqueCount, searchPrice);
        if (foundIdx != -1) {
            System.out.println("Result: Book found at index " + foundIdx + ": [" 
                    + books[foundIdx].bookId + "] " + books[foundIdx].title 
                    + " (Rs. " + books[foundIdx].price + ")");
        } else {
            System.out.println("Result: Book not found with price Rs. " + searchPrice);
        }
        System.out.println();

        // 5. After Task 5 (Sliding Window for Target Cost S = Rs. 1000.0)
        System.out.println("---------------------------------------------------------------");
        System.out.println("5. After Task 5 (Sliding Window for Target Cost S = Rs. 1000.0):");
        double targetGrant = 1000.0;
        System.out.println("Finding minimum consecutive books whose total price >= Rs. " + targetGrant + "...");
        int minBooks = minBooksForTargetCost(books, uniqueCount, targetGrant);
        System.out.println("Minimum Consecutive Books Needed: " + minBooks);
        printSlidingWindowExplanation(books, uniqueCount, targetGrant, minBooks);
        System.out.println("===============================================================");
        System.out.println("       SYSTEM DEMONSTRATION COMPLETED SUCCESSFULLY!            ");
        System.out.println("===============================================================\n");
    }

    /**
     * Interactive CLI mode for real-time catalog management.
     */
    public static void runInteractiveMenu(Scanner sc) {
        Book[] books = new Book[100];
        // Populate with default sample books
        books[0] = new Book(101, "Data Structures", "Mark", 400.0);
        books[1] = new Book(101, "Data Structures", "Mark", 400.0);
        books[2] = new Book(102, "Java Basics", "James", 300.0);
        books[3] = new Book(103, "Python Guide", "Guido", 600.0);
        books[4] = new Book(104, "Database Systems", "Raghu", 500.0);
        books[5] = new Book(105, "Computer Networks", "Andrew", 700.0);
        int count = 6;

        boolean running = true;
        while (running) {
            System.out.println("\n===== Smart Library Management Menu =====");
            System.out.println("Current Books Count: " + count);
            System.out.println("1. Display all cataloged books");
            System.out.println("2. Remove duplicate books (In-Place)");
            System.out.println("3. Search books by partial title (Case-Insensitive)");
            System.out.println("4. Sort books by price (Ascending)");
            System.out.println("5. Fast search by exact price (Binary Search)");
            System.out.println("6. Find minimum consecutive books for target budget (Sliding Window)");
            System.out.println("7. Add a new book to catalog");
            System.out.println("8. Run automated system demonstration");
            System.out.println("9. Exit");
            System.out.print("Select an option (1-9): ");

            String input = sc.nextLine().trim();
            switch (input) {
                case "1":
                    System.out.println("\n--- Current Books in System ---");
                    for (int i = 0; i < count; i++) {
                        System.out.println("[" + i + "] " + books[i].toDetailString());
                    }
                    break;
                case "2":
                    System.out.println("\n--- Removing Duplicates In-Place ---");
                    count = removeDuplicates(books, count);
                    System.out.println("New unique count: " + count);
                    for (int i = 0; i < count; i++) {
                        System.out.println(books[i]);
                    }
                    break;
                case "3":
                    System.out.print("\nEnter partial title to search: ");
                    String q = sc.nextLine().trim();
                    searchByTitle(books, count, q);
                    break;
                case "4":
                    System.out.println("\n--- Sorting Books by Price ---");
                    sortByPrice(books, count);
                    break;
                case "5":
                    System.out.print("\nEnter exact target price to search: ");
                    try {
                        double p = Double.parseDouble(sc.nextLine().trim());
                        int idx = searchByPrice(books, count, p);
                        if (idx != -1) {
                            System.out.println("Book found at index " + idx + ": " + books[idx]);
                        } else {
                            System.out.println("No book found with price Rs. " + p + ". Note: Ensure catalog is sorted first!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price input.");
                    }
                    break;
                case "6":
                    System.out.print("\nEnter target budget / research grant amount (Rs.): ");
                    try {
                        double targetS = Double.parseDouble(sc.nextLine().trim());
                        int minLen = minBooksForTargetCost(books, count, targetS);
                        System.out.println("Minimum Consecutive Books Needed: " + minLen);
                        printSlidingWindowExplanation(books, count, targetS, minLen);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount input.");
                    }
                    break;
                case "7":
                    if (count >= books.length) {
                        System.out.println("Catalog array is full.");
                        break;
                    }
                    try {
                        System.out.print("Enter Book ID (int): ");
                        int bId = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Enter Title: ");
                        String bTitle = sc.nextLine().trim();
                        System.out.print("Enter Author: ");
                        String bAuthor = sc.nextLine().trim();
                        System.out.print("Enter Price: ");
                        double bPrice = Double.parseDouble(sc.nextLine().trim());
                        books[count++] = new Book(bId, bTitle, bAuthor, bPrice);
                        System.out.println("Book added successfully.");
                    } catch (Exception e) {
                        System.out.println("Error adding book: " + e.getMessage());
                    }
                    break;
                case "8":
                    runSampleTestCase();
                    break;
                case "9":
                    running = false;
                    System.out.println("Exiting Smart Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1 and 9.");
            }
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--interactive")) {
            Scanner sc = new Scanner(System.in);
            runInteractiveMenu(sc);
            sc.close();
        } else {
            // Default run executes standard demonstration pipeline
            runSampleTestCase();
            System.out.println("Tip: Pass '--interactive' flag to launch the interactive terminal menu!");
        }
    }
}

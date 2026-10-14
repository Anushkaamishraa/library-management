import java.util.Scanner;

/**
 * DSA Practical Assignment: Library Management System
 * Course: Data Structures & Algorithms (DSA)
 * Target Audience: Computer Science & Engineering Students
 *
 * Implements 5 key algorithmic tasks:
 * 1. Remove duplicate book records in-place (Two Pointers - O(N) time, O(1) space)
 * 2. Partial title search (Case-insensitive substring search - O(N * M) time)
 * 3. Selection sort by price (O(N^2) time, O(1) space, counts swaps)
 * 4. Fast price search (Binary Search - O(log N) time)
 * 5. Minimum consecutive books for research grant (Sliding Window - O(N) time, O(1) space)
 */
public class LibraryManagementSystem {

    // ==========================================
    // TASK 1: Remove Duplicate Books In-Place
    // ==========================================
    /**
     * Given an array of books sorted by bookId, removes duplicates in-place
     * so that each book appears only once.
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

    // ==========================================
    // TASK 2: Partial Title Search
    // ==========================================
    /**
     * Searches through the array and finds all books whose titles contain
     * the search word. The search is case-insensitive.
     *
     * @param books Array of Book objects
     * @param count Number of valid books in array
     * @param query Search string
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

    // ==========================================
    // TASK 3: Sort by Price (Selection Sort)
    // ==========================================
    /**
     * Sorts the unique books in ascending order of price (cheapest book first)
     * using Selection Sort (finds the minimum element in unsorted part and swaps).
     * Counts and prints how many swaps were performed.
     *
     * @param books Array of Book objects
     * @param count Number of valid books in array
     *
     * Time Complexity: O(N^2)
     * Space Complexity: O(1)
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
     * Helper version of selection sort that returns the number of swaps without printing.
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

    // ==========================================
    // TASK 4: Search by Price (Binary Search)
    // ==========================================
    /**
     * Searches for a book with an exact price targetPrice in O(log N) time
     * using Binary Search on a price-sorted array.
     *
     * @param books       Array of Book objects (sorted ascending by price)
     * @param count       Number of valid books in array
     * @param targetPrice Exact price to search for
     * @return 0-based index if found, or -1 if not found
     *
     * Time Complexity: O(log N)
     * Space Complexity: O(1)
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

    // ==========================================
    // TASK 5: Sliding Window for Target Cost S
    // ==========================================
    /**
     * Finds the minimum length of a contiguous subarray of books whose total price
     * meets or exceeds targetCost (S).
     *
     * Uses a Sliding Window (Two Pointers) approach:
     * - Expands window with right pointer.
     * - Whenever currentSum >= targetCost, records window length and contracts
     *   window with left pointer.
     *
     * @param books      Array of Book objects
     * @param count      Number of valid books in array
     * @param targetCost Target research grant amount S
     * @return Minimum length of contiguous books >= targetCost, or 0 if none exists.
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
     * Helper to print detailed explanations of the qualifying minimal contiguous windows.
     */
    public static void printSlidingWindowExplanation(Book[] books, int count, double targetCost, int minLength) {
        if (minLength <= 0 || books == null || count < minLength) {
            System.out.println("No contiguous subarray meets the target cost of Rs. " + targetCost);
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
     * Executes the exact test case provided in the assignment PDF.
     */
    public static void runSampleTestCase() {
        System.out.println("===============================================================");
        System.out.println("  DSA PRACTICAL ASSIGNMENT: LIBRARY MANAGEMENT SYSTEM");
        System.out.println("  Execution of Assignment Sample Test Case");
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
        System.out.println("  SAMPLE TEST CASE COMPLETED SUCCESSFULLY!");
        System.out.println("===============================================================\n");
    }

    /**
     * Interactive CLI mode for testing custom queries and books.
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
            System.out.println("1. Display all books");
            System.out.println("2. Task 1: Remove Duplicate Books (In-Place)");
            System.out.println("3. Task 2: Search Books by Title (Case-Insensitive)");
            System.out.println("4. Task 3: Sort Books by Price (Selection Sort)");
            System.out.println("5. Task 4: Fast Search by Exact Price (Binary Search)");
            System.out.println("6. Task 5: Find Min Consecutive Books for Grant S (Sliding Window)");
            System.out.println("7. Add a new book");
            System.out.println("8. Run Assignment Sample Test Case");
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
                            System.out.println("No book found with price Rs. " + p + ". Note: Ensure books are sorted first!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price input.");
                    }
                    break;
                case "6":
                    System.out.print("\nEnter research grant amount S: ");
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
                        System.out.println("Array is full.");
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
            // Default run executes the assignment's exact sample test case
            runSampleTestCase();
            System.out.println("Tip: Run with flag '--interactive' to launch the interactive terminal menu!");
        }
    }
}

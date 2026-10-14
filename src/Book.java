/**
 * Smart Library Management System
 * Core data model representing a cataloged library book entity.
 */
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

    @Override
    public String toString() {
        return "[" + bookId + "] " + title + " - Rs. " + price;
    }

    public String toDetailString() {
        return "Book [ID=" + bookId + ", Title=\"" + title + "\", Author=\"" + author + "\", Price=Rs. " + price + "]";
    }
}

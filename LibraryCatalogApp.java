import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class LibraryCatalogApp {
    static final int MAX_BOOKS = 4;
    static String[][] books = {
            {"101", "HTML and CSS", "Jon Duckett", "Available", "Null"},
            {"102", "JavaScript: The Good Parts", "Douglas C", "Available", "Null"},
            {"103", "Learning Web Design", "Jennifer N", "CP2014", "23-May-2023"},
            {"104", "Responsive Web Design", "Ben Frain", "EC3142", "17-May-2023"}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1:
                    displayBooks();
                    break;
                case 2:
                    issueBook(scanner);
                    break;
                case 3:
                    returnBook(scanner);
                    break;
                case 4:
                    System.out.println("Thank you for visiting  SmartPoint!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        System.out.println("Welcome to the Unique Library\n-----------------------------------------------------------------------------------------------------");
        System.out.println("1. View the complete list of Books");
        System.out.println("2. Issue a Book");
        System.out.println("3. Return a Book");
        System.out.println("4. Exit");
        System.out.println("\nPlease choose an option from the above menu: ");
    }

    private static void displayBooks() {
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        System.out.println("List of all Books\n-----------------------------------------------------------------------------------------------------");
        System.out.printf("%-8s%-25s%-20s%-15s%-15s\n", "Book ID", "Book Title", "Author", "Availability", "Issue Date");

        for (String[] book : books) {
            System.out.printf("%-8s%-25s%-20s%-15s%-15s\n", book[0], book[1], book[2], book[3], book[4]);
        }

        System.out.println("\nEnter Y to return to the main menu or N to Exit: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toUpperCase();
        if (choice.equals("N")) {
            System.out.println("Thank you for visiting SmartPoint!");
            System.exit(0);
        }
    }

    private static void issueBook(Scanner scanner) {
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        System.out.println("Issue the Book\n-----------------------------------------------------------------------------------------------------");
        System.out.println("Enter the Book ID: ");
        String bookID = scanner.nextLine();

        for (String[] book : books) {
            if (book[0].equals(bookID) && book[3].equals("Available")) {
                System.out.printf("%s - %s by %s: %s\n", book[0], book[1], book[2], book[3]);
                System.out.println("Enter StudentID: ");
                String studentID = scanner.nextLine();
                System.out.println("Enter C to confirm: ");
                String confirm = scanner.nextLine().toUpperCase();

                if (confirm.equals("C")) {
                    book[3] = studentID;
                    book[4] = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
                    System.out.printf("BookID: %s is Issued to %s\n", book[0], studentID);
                } else {
                    System.out.println("Issue process canceled.");
                }
                return;
            }
        }

        System.out.println("Book not found or not available for issuing.");
    }

    private static void returnBook(Scanner scanner) {
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        System.out.println("Return the Book\n-----------------------------------------------------------------------------------------------------");
        System.out.println("Enter the Book ID: ");
        String bookID = scanner.nextLine();

        for (String[] book : books) {
            if (book[0].equals(bookID) && !book[3].equals("Available")) {
                System.out.printf("%s - %s by %s\n", book[0], book[1], book[2]);
                System.out.printf("StudentID - %s\n", book[3]);
                System.out.printf("Issue Date - %s\n", book[4]);

                try {
                    Date issueDate = new SimpleDateFormat("dd-MMM-yyyy").parse(book[4]);
                    Date currentDate = new Date();

                    long diffInMillies = Math.abs(currentDate.getTime() - issueDate.getTime());
                    long diff = diffInMillies / (24 * 60 * 60 * 1000);

                    if (diff > 7) {
                        int lateDays = (int) (diff - 7);
                        double lateCharges = lateDays * 10.0;
                        System.out.printf("Delayed by - %d days\n", lateDays);
                        System.out.printf("Delayed Charges - Rs. %.2f\n", lateCharges);
                    }

                    System.out.println("Enter R to confirm the return: ");
                    String confirm = scanner.nextLine().toUpperCase();

                    if (confirm.equals("R")) {
                        book[3] = "Available";
                        book[4] = "Null";
                        System.out.printf("BookID: %s is returned back\n", book[0]);
                    } else {
                        System.out.println("Return process canceled.");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return;
            }
        }

        System.out.println("Book not found or not available for returning.");
    }
}

import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Series seriesManager = new Series();
        boolean running = true;

        System.out.println("Latest Series - 2025");
        System.out.println("******************************************");

        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Capture a new series");
            System.out.println("2. Search for a series");
            System.out.println("3. Update series details");
            System.out.println("4. Delete a series");
            System.out.println("5. Print series report");
            System.out.println("6. Exit application");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        seriesManager.CaptureSeries(scanner);
                        break;
                    case 2:
                        seriesManager.SearchSeries(scanner);
                        break;
                    case 3:
                        seriesManager.UpdateSeries(scanner);
                        break;
                    case 4:
                        seriesManager.DeleteSeries(scanner);
                        break;
                    case 5:
                        seriesManager.SeriesReport();
                        break;
                    case 6:
                        seriesManager.ExitSeriesApplication();
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        scanner.close(); // Close the scanner when the application exits.
    }
}

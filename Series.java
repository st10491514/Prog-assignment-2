import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

 class Series {
    // A list to store all the SeriesItem objects. This list acts as our in-memory database.
    private List<SeriesItem> seriesItems = new ArrayList<>();

    public void CaptureSeries(Scanner scanner) {
        System.out.println("\n--- Capture New Series ---");
        System.out.print("Enter series ID: ");
        String seriesID = scanner.nextLine();

        System.out.print("Enter series name: ");
        String seriesName = scanner.nextLine();

        System.out.print("Is this a TV show or a movie? (T/M): ");
        String typeChoice = scanner.nextLine();

        System.out.print("Enter age restriction (e.g., 13, 16, 18): ");
        int ageRestriction = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (typeChoice.equalsIgnoreCase("T")) {
            System.out.print("Enter number of episodes: ");
            int numberOfEpisodes = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            TvShow newTvShow = new TvShow(seriesID, seriesName, numberOfEpisodes, ageRestriction);
            seriesItems.add(newTvShow);
            System.out.println("TV Show '" + newTvShow.getSeriesName() + "' captured successfully.");
        } else if (typeChoice.equalsIgnoreCase("M")) {
            System.out.print("Enter run time in minutes: ");
            int runTime = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            Movie newMovie = new Movie(seriesID, seriesName, runTime, ageRestriction);
            seriesItems.add(newMovie);
            System.out.println("Movie '" + newMovie.getSeriesName() + "' captured successfully.");
        } else {
            System.out.println("Invalid series type. Capture cancelled.");
        }
    }

    public void SearchSeries(Scanner scanner) {
        System.out.println("\n--- Search for a Series ---");
        System.out.print("Enter the series ID to search for: ");
        String seriesID = scanner.nextLine();

        SeriesItem foundItem = findSeriesByID(seriesID);
        if (foundItem != null) {
            System.out.println("Series found:");
            System.out.println(foundItem); // Calls the overridden toString() method
        } else {
            System.out.println("Series with ID '" + seriesID + "' not found.");
        }
    }

    public void UpdateSeries(Scanner scanner) {
        System.out.println("\n--- Update Series Details ---");
        System.out.print("Enter the series ID to update: ");
        String seriesID = scanner.nextLine();

        SeriesItem foundItem = findSeriesByID(seriesID);
        if (foundItem != null) {
            System.out.println("Series found: " + foundItem.getSeriesName());

            System.out.print("Enter new series name (current: '" + foundItem.getSeriesName() + "', press Enter to skip): ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                foundItem.setSeriesName(newName);
                System.out.println("Series name updated.");
            }

            // Check if the item is a TvShow to update specific properties
            if (foundItem instanceof TvShow) {
                TvShow tvShow = (TvShow) foundItem;
                System.out.print("Enter new number of episodes (current: " + tvShow.getNumberOfEpisodes() + ", press Enter to skip): ");
                String newEpisodesStr = scanner.nextLine();
                if (!newEpisodesStr.trim().isEmpty()) {
                    try {
                        int newEpisodes = Integer.parseInt(newEpisodesStr);
                        tvShow.setNumberOfEpisodes(newEpisodes);
                        System.out.println("Number of episodes updated.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for number of episodes. Value not updated.");
                    }
                }
            }

            // Check if the item is a Movie to update specific properties
            if (foundItem instanceof Movie) {
                Movie movie = (Movie) foundItem;
                System.out.print("Enter new run time in minutes (current: " + movie.getRunTimeInMinutes() + ", press Enter to skip): ");
                String newRunTimeStr = scanner.nextLine();
                if (!newRunTimeStr.trim().isEmpty()) {
                    try {
                        int newRunTime = Integer.parseInt(newRunTimeStr);
                        movie.setRunTimeInMinutes(newRunTime);
                        System.out.println("Run time updated.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input for run time. Value not updated.");
                    }
                }
            }

            System.out.print("Enter new age restriction (current: " + foundItem.getAgeRestriction() + ", press Enter to skip): ");
            String newAgeRestrictionStr = scanner.nextLine();
            if (!newAgeRestrictionStr.trim().isEmpty()) {
                try {
                    int newAgeRestriction = Integer.parseInt(newAgeRestrictionStr);
                    foundItem.setAgeRestriction(newAgeRestriction);
                    System.out.println("Age restriction updated.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for age restriction. Value not updated.");
                }
            }

            System.out.println("\nUpdate complete for series '" + foundItem.getSeriesName() + "'.");

        } else {
            System.out.println("Series with ID '" + seriesID + "' not found.");
        }
    }
    public void DeleteSeries(Scanner scanner) {
        System.out.println("\n--- Delete a Series ---");
        System.out.print("Enter the series ID to delete: ");
        String seriesID = scanner.nextLine();

        SeriesItem foundItem = findSeriesByID(seriesID);
        if (foundItem != null) {
            System.out.println("Are you sure you want to delete the series '" + foundItem.getSeriesName() + "'? (Y/N): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("Y")) {
                seriesItems.remove(foundItem);
                System.out.println("Series '" + foundItem.getSeriesName() + "' deleted successfully.");
            } else if (confirmation.equalsIgnoreCase("N")) {
                System.out.println("Series deletion cancelled.");
            } else {
                System.out.println("Invalid input. Deletion cancelled.");
            }
        } else {
            System.out.println("Series with ID '" + seriesID + "' not found.");
        }
    }
    public void SeriesReport() {
        System.out.println("\n--- Series Report ---");
        if (seriesItems.isEmpty()) {
            System.out.println("No series have been captured yet.");
        } else {
            for (SeriesItem item : seriesItems) {
                System.out.println(item); // The toString() method provides the formatted report for each item.
                System.out.println("****************************");
            }
        }
    }
    public void ExitSeriesApplication() {
        System.out.println("Exiting application. Goodbye!");
    }
    private SeriesItem findSeriesByID(String seriesID) {
        for (SeriesItem item : seriesItems) {
            if (item.getSeriesID().equalsIgnoreCase(seriesID)) {
                return item;
            }
        }

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TestUnit {

    private Series seriesManager;
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() throws Exception {
        // Create a new Series object before each test to ensure a clean state
        seriesManager = new Series();
        // Redirect System.out to capture console output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    private Scanner setSimulatedInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        return new Scanner(System.in);
    }

    private String getOutput() {
        return outputStream.toString().replace("\r", "");
    }

    @Test
    @DisplayName("Test CaptureSeries for a TV show")
    void testCaptureSeries_TvShow() {
        String input = "TV101\nBreaking Bad\nT\n18\n62\n";
        Scanner scanner = setSimulatedInput(input);

        seriesManager.CaptureSeries(scanner);

        seriesManager.SearchSeries(setSimulatedInput("TV101\n"));
        String output = getOutput();

        assertTrue(output.contains("Series found:"));
        assertTrue(output.contains("Series Name: Breaking Bad"));
        assertTrue(output.contains("Series Type: TV Show"));
        assertTrue(output.contains("Number of Episodes: 62"));
    }

    @Test
    @DisplayName("Test CaptureSeries for a movie")
    void testCaptureSeries_Movie() {
        String input = "M202\nThe Matrix\nM\n16\n136\n";
        Scanner scanner = setSimulatedInput(input);

        seriesManager.CaptureSeries(scanner);

        seriesManager.SearchSeries(setSimulatedInput("M202\n"));
        String output = getOutput();

        assertTrue(output.contains("Series found:"));
        assertTrue(output.contains("Series Name: The Matrix"));
        assertTrue(output.contains("Series Type: Movie"));
        assertTrue(output.contains("Run Time: 136 minutes"));
    }

    @Test
    @DisplayName("Test SearchSeries for an existing series")
    void testSearchSeries_Existing() {
        // First capture a series to ensure it exists
        String captureInput = "TV101\nBreaking Bad\nT\n18\n62\n";
        seriesManager.CaptureSeries(setSimulatedInput(captureInput));

        // Then search for it
        String searchInput = "TV101\n";
        Scanner scanner = setSimulatedInput(searchInput);
        seriesManager.SearchSeries(scanner);

        String output = getOutput();
        assertTrue(output.contains("Series found:"));
        assertTrue(output.contains("Series Name: Breaking Bad"));
    }

    @Test
    @DisplayName("Test SearchSeries for a non-existent series")
    void testSearchSeries_NonExistent() {
        String input = "NONEXISTENT\n";
        Scanner scanner = setSimulatedInput(input);
        seriesManager.SearchSeries(scanner);

        String output = getOutput();
        assertTrue(output.contains("Series with ID 'NONEXISTENT' not found."));
    }

    @Test
    @DisplayName("Test UpdateSeries details")
    void testUpdateSeries_Details() {
        // First capture a series
        String captureInput = "TV101\nBreaking Bad\nT\n18\n62\n";
        seriesManager.CaptureSeries(setSimulatedInput(captureInput));

        // Simulate updates: new name, new episodes, new age restriction
        String updateInput = "TV101\nBetter Call Saul\n90\n16\n";
        Scanner scanner = setSimulatedInput(updateInput);
        seriesManager.UpdateSeries(scanner);

        // Search for the updated series to verify the changes
        seriesManager.SearchSeries(setSimulatedInput("TV101\n"));
        String output = getOutput();

        assertTrue(output.contains("Series Name: Better Call Saul"));
        assertTrue(output.contains("Number of Episodes: 90"));
        assertTrue(output.contains("Series Age Restriction: 16"));
    }

    @Test
    @DisplayName("Test DeleteSeries with confirmation 'Y'")
    void testDeleteSeries_Confirmed() {
        // Capture a series
        String captureInput = "M202\nThe Matrix\nM\n16\n136\n";
        seriesManager.CaptureSeries(setSimulatedInput(captureInput));

        // Delete it with 'Y' confirmation
        String deleteInput = "M202\nY\n";
        Scanner scanner = setSimulatedInput(deleteInput);
        seriesManager.DeleteSeries(scanner);

        // Try to search for it, should not be found
        seriesManager.SearchSeries(setSimulatedInput("M202\n"));
        String output = getOutput();

        assertTrue(output.contains("Series 'The Matrix' deleted successfully."));
        assertTrue(output.contains("Series with ID 'M202' not found."));
    }

    @Test
    @DisplayName("Test SeriesReport with captured series")
    void testSeriesReport() {
        // Capture a TV show
        String captureTvShowInput = "TV101\nBreaking Bad\nT\n18\n62\n";
        seriesManager.CaptureSeries(setSimulatedInput(captureTvShowInput));

        // Capture a movie
        String captureMovieInput = "M202\nThe Matrix\nM\n16\n136\n";
        seriesManager.CaptureSeries(setSimulatedInput(captureMovieInput));

        seriesManager.SeriesReport();
        String output = getOutput();

        assertTrue(output.contains("--- Series Report ---"));
        assertTrue(output.contains("Series Name: Breaking Bad"));
        assertTrue(output.contains("Number of Episodes: 62"));
        assertTrue(output.contains("Series Name: The Matrix"));
        assertTrue(output.contains("Run Time: 136 minutes"));
    }
}

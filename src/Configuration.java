import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {

    private static int totalTickets;
    private static int ticketReleaseRate;
    private static int customerRetrievalRate;
    private static int maxTicketCapacity;

    // Setter methods
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    //Getter Methods for the configuration parameters
    public static int getTicketRetrievalRate() {
        return customerRetrievalRate;
    }

    public static int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public static int getTotalTickets() {
        return totalTickets;
    }

    public static int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }


    // Save configuration to a file manually creating a JSON-like structure
    public void saveConfigToFile(String fileName) {
        // Create a JSON-like string manually
        String jsonConfig = String.format(
                "{\n" +
                        "  \"totalTickets\": %d,\n" +
                        "  \"ticketReleaseRate\": %d,\n" +
                        "  \"customerRetrievalRate\": %d,\n" +
                        "  \"maxTicketCapacity\": %d\n" +
                        "}",
                totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity
        );

        // Write the JSON string to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(jsonConfig); // Write JSON string to the file
            System.out.println("\nConfiguration successfully saved to " + fileName);
        } catch (IOException e) {
            System.err.println("\nError saving configuration: " + e.getMessage());
        }
    }

}

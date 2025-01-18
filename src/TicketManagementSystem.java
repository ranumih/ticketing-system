import java.util.Scanner;

public class TicketManagementSystem {

    private static boolean systemRunning = false;

    public static void setSystemConfigurations() {
        Scanner scanner = new Scanner(System.in);
        Configuration config = new Configuration();

        while (true) {
            System.out.println("-------------Starting System Configurations--------- \n");
            int totalTickets = getInput(scanner, "Enter initial tickets count (1-100): ", 1, 100);
            int ticketReleaseRate = getInput(scanner, "Enter the Ticket Release Rate (1-10): ", 1, 10);
            int ticketRetrievalRate = getInput(scanner, "Enter the Ticket Retrieval Rate (1-10): ", 1, 10);
            int maxTicketCapacity = getInput(scanner, "Enter the Max Ticket Capacity (1-100): ", 1, 100);

            if (totalTickets > maxTicketCapacity) {
                Logger.log("Initial ticket count cannot be higher than the max ticket capacity. Please re-enter the values.");
                continue;
            }

            config.setTotalTickets(totalTickets);
            config.setTicketReleaseRate(ticketReleaseRate);
            config.setCustomerRetrievalRate(ticketRetrievalRate);
            config.setMaxTicketCapacity(maxTicketCapacity);

            //Saving the data to the Config file
            config.saveConfigToFile("config.json");

            System.out.println("Configurations set up for system startup!");


            break;
        }
    }

    //getInput method to validate the user inputs for the configuration
    private static int getInput(Scanner scanner, String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value < min || value > max) {
                    throw new Exception("Value must be between " + min + " and " + max);
                }
                return value;
            } catch (NumberFormatException e) {
                Logger.log("Invalid input. Please enter a number.");
            } catch (Exception e) {
                Logger.log(e.getMessage());
            }
        }
    }

    //controlSystem method to start and stop the system
    public static void controlSystem() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if(!systemRunning) {
                System.out.println("\nType 'Start' if you want to start the Ticket System or 'Stop' to stop it.");
            }
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("start")) {
                if (!systemRunning) {
                    systemRunning = true;
                    System.out.println("Ticket System is now running.");
                    TicketPool.runOperations();
                } else {
                    Logger.log("The system is already running.");
                }
            } else if (input.equals("stop")) {
                if (systemRunning) {
                    TicketPool.stopOperations();
                    systemRunning = false;
                    Logger.log("Ticket System has been stopped.");
                    break;
                } else {
                    Logger.log("The system hasn't started yet to stop.");
                }
            } else {
                Logger.log("Invalid input. Please type 'Start' or 'Stop'.");
            }
        }
    }

    //Getter method to check if the system is running
    public static boolean isSystemRunning() {
        return systemRunning;
    }
}
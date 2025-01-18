import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TicketPool {

    private static final int retrievalRate = Configuration.getTicketRetrievalRate();
    private static final int releaseRate = Configuration.getTicketReleaseRate();
    private static final int totalTickets = Configuration.getTotalTickets();
    private static final int ticketCapacity = Configuration.getMaxTicketCapacity();

    private static final LinkedList<String> tickets = new LinkedList<>();
    private static boolean customerTurn = false;
    private static int totalTicketsAdded = 0;

    private static final List<Thread> threads = new ArrayList<>();


    public static void runOperations() {

        if (totalTickets > 0) {
            customerTurn = true;
        }

        for (int i = 0; i < totalTickets; i++) {
            tickets.add("Ticket ID: " + (i + 1));
            totalTicketsAdded++;
        }
        Logger.log("Initialized tickets. Total tickets: " + totalTickets + "\n");

        TicketPool ticketPool = new TicketPool();
        // Create and start customer threads
        for (int i = 0; i < 4; i++) {
            Thread customerThread = new Thread(new Customer(ticketPool));
            customerThread.setName("Customer-" + (i + 1));
            threads.add(customerThread);
            customerThread.start();
        }

        // Create and start vendor threads
        for (int i = 0; i < 5; i++) {
            Thread vendorThread = new Thread(new Vendor(ticketPool));
            vendorThread.setName("Vendor-" + (i + 1));
            threads.add(vendorThread);
            vendorThread.start();
        }
    }

    public static void stopOperations() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        Logger.log("All the threads have been interrupted.");
    }

    public synchronized void addTicket() {
        while (!customerTurn) {
            int ticketsToAdd = (int) (Math.random() * (releaseRate)) + 1;
            while ((tickets.size() + ticketsToAdd) > ticketCapacity) {
                try {
                    Logger.log("Cannot add " + ticketsToAdd + " tickets. Capacity will be exceeded. Waiting...");
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Logger.log("\nAdd ticket operation interrupted: " + e.getMessage());
                }
            }
            for (int i = 0; i < ticketsToAdd; i++) {
                tickets.add("Ticket ID: " + (totalTicketsAdded + 1));
                totalTicketsAdded++;
            }
            Logger.log(Thread.currentThread().getName() + " added " + ticketsToAdd + " tickets ");
            Logger.log("total tickets: " + tickets.size() + "\n");
            customerTurn = true;
            notifyAll();
        }
    }

    public synchronized void removeTicket() {
        while (customerTurn) {
            int ticketsToRemove = (int) (Math.random() * (retrievalRate)) + 1;
            while (tickets.size() - ticketsToRemove < 0) {
                try {
                    Logger.log("Cannot remove " + ticketsToRemove + " tickets. Not enough tickets available. Waiting...");
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    Logger.log("\nRemove ticket operation interrupted: " + e.getMessage());
                }
            }
            for (int i = 0; i < ticketsToRemove; i++) {
                tickets.removeFirst();
            }
            Logger.log(Thread.currentThread().getName() + " purchased " + ticketsToRemove + " tickets ");
            Logger.log("Total tickets: " + tickets.size() + "\n");
            customerTurn = false;
            notifyAll();
        }
    }

}




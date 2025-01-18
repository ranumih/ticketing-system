public class Vendor implements Runnable {
    private final TicketPool ticketPool;

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (TicketManagementSystem.isSystemRunning()) {

            try {
                ticketPool.addTicket();
                Thread.sleep(2000); // Simulate some operation
            } catch (InterruptedException e) {
                System.err.println("Vendor operation interrupted: " + e.getMessage());
            }
        }
    }
}
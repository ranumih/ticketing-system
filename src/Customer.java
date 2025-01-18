public class Customer implements Runnable {
    private final TicketPool ticketPool;

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (TicketManagementSystem.isSystemRunning()) {
            try {
                ticketPool.removeTicket();
                Thread.sleep(1000); // Simulate some operation
            } catch (InterruptedException e) {
                System.err.println("Customer operation interrupted: " + e.getMessage());
            }
        }
    }
}
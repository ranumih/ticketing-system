
public class Main {
    public static void main(String[] args) {

        Logger.initialize("logfile-TicketSystem.txt");

        TicketManagementSystem.setSystemConfigurations();
        TicketManagementSystem.controlSystem();

        Logger.close();

    }
}


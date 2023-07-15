public class ServerProgram {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ServerProgram <portNumber>");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);

        Server server = new Server(portNumber);
        server.start();
    }
}

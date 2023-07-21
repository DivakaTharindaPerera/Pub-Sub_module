public class ServerProgram {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ServerProgram <ipAddress> <portNumber>");
            return;
        }

        try {
            // Getting the ip Address from cmd arguments
            String ipAddress = args[0];
            int portNumber = Integer.parseInt(args[1]);

            if(portNumber<1024 || portNumber > 49151) {
                System.out.println("Error: port number should be between 1024 and 49151");
                return;
            }

            Server server = new Server(ipAddress, portNumber);
            server.start();

        } catch(Exception e) {
            System.out.println("Error: port Number should be an integer between 1024 and 49151");
        }
    }
}

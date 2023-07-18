public class ServerProgram {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ServerProgram <portNumber>");
            return;
        }

        try {
            int portNumber = Integer.parseInt(args[0]);

            if(portNumber<1024 || portNumber > 49151) {
                System.out.println("Error: port number should be between 1024 and 49151");
                return;
            }

            Server server = new Server(portNumber);
            server.start();

        } catch(Exception e) {
            System.out.println("Error: port Number should be an integer between 1024 and 49151");
        }
    }
}

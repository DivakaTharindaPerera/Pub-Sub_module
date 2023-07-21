public class ClientProgram {

    public static void main(String[] args) {
        Client client;
//        Getting command line args
        if (args.length != 4) {
            System.out.println("Usage: java ClientProgram <ipAddress> <portNumber> <type> <topic>");
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

            String type = args[2];
            String topic = args[3];

//          Create a pub or sub client based on command line args
            if ("PUBLISHER".equalsIgnoreCase(type)) {
                client = new Client(ipAddress, portNumber, "PUBLISHER",topic);
                client.start();
            }else if ("SUBSCRIBER".equalsIgnoreCase((type))) {
                client = new Client(ipAddress, portNumber, "SUBSCRIBER",topic);
                client.start();
            }else {
                System.out.println("Usage: java ClientProgram <portNumber> <type>");
            }

        } catch(Exception e) {
            System.out.println("Error: port Number should be an integer between 1024 and 49151");
        }
    }
}

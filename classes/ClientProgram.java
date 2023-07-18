public class ClientProgram {

    public static void main(String[] args) {
        Client client;
//        Getting command line args
        if (args.length != 3) {
            System.out.println("Usage: java ClientProgram <portNumber> <type> <topic>");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);
        String type = args[1];
        String topic = args[2];

//        Create a pub or sub client based on command line args
        if ("PUBLISHER".equalsIgnoreCase(type)) {
            client = new Client(portNumber, "PUBLISHER",topic);
            client.start();
        }else if ("SUBSCRIBER".equalsIgnoreCase((type))) {
            client = new Client(portNumber, "SUBSCRIBER",topic);
            client.start();
        }else {
            System.out.println("Usage: java ClientProgram <portNumber> <type>");
        }
    }
}

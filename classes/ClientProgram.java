public class ClientProgram {

    public static void main(String[] args) {
        Client client;
//        Getting command line args
        if (args.length != 2) {
            System.out.println("Usage: java ServerExample <portNumber> <type>");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);
        String type = args[1];


//        Create a pub or sub client based on command line args
        if ("PUBLISHER".equalsIgnoreCase(type)) {
            client = new Client(portNumber, "PUBLISHER");
            client.start();
        }else if ("SUBSCRIBER".equalsIgnoreCase((type))) {
            client = new Client(portNumber, "SUBSCRIBER");
            client.start();
        }else {
            System.out.println("Usage: java ServerExample <portNumber> <type>");
            return;
        }
    }
}

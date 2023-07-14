import java.io.*;
import java.net.*;
// import java.util.*;


public class Server{

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public void Client(String address, int port){
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to "+address+":"+port);

            input = new DataInputStream(System.in);
            output = new DataOutputStream(socket.getOutputStream());

        }catch(UnknownHostException Herror){
            System.out.println("Server.Client("+Herror+")");

        }catch (IOException e) {
            // TODO: handle exception
            System.out.println("Server.Client("+e+")");
            return;
        }
    }

    public static void main(String[] args) {
//        TODO: Uncomment this part to get command line args
//        if (args.length != 1) {
//            System.out.println("Usage: java ServerExample <portNumber>");
//            return;
//        }

//        int portNumber = Integer.parseInt(args[0]);
        int portNumber = 5000;

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is listening on port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                Thread serverThread = new ServerThread(clientSocket);
                serverThread.start();

            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}

class ServerThread extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerThread(Socket clientSocket) {
        System.out.println("Server thread is initiating");
        this.clientSocket = clientSocket;

        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void run() {
        System.out.println("Server thread is running");
        String inputLine;
        try {
            while ((inputLine = this.in.readLine()) != null) {
                System.out.println("Received from client: " + inputLine);
                out.println("Server received: " + inputLine);

                if("terminate".equalsIgnoreCase(inputLine)) {
                    System.out.println("Client is disconnecting");
                    break;
                }
            }

            this.out.close();
            this.in.close();
            this.clientSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Server thread is exiting");
    }
}
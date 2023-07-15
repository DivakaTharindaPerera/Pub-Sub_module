import java.io.*;
import java.net.*;
import java.util.*;
// import java.util.*;


public class Server{

    private final int port;
    private MessageList messageList;

    public Server(int port) {
        this.port = port;
        this.messageList = new MessageList();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            System.out.println("Server is listening on port " + this.port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                Thread serverThread = new ServerThread(clientSocket, this.messageList);
                serverThread.start();

            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    class ServerThread extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private MessageList messageList;
        private String type = null;
        int head;

        public ServerThread(Socket clientSocket, MessageList messageList) {
            System.out.println("Server thread is initiating");
            this.clientSocket = clientSocket;
            this.messageList = messageList;
            this.head = 0;

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

//                Capture the initial message where client introduces itself
                while (this.type == null && (inputLine = this.in.readLine()) != null) {
                    System.out.println("Client found: " + inputLine);
                    out.println("Hello " + inputLine);
                    this.type = inputLine;
                }

//                If client is a PUBLISHER,
//                Capture messages by publishers

                if("PUBLISHER".equalsIgnoreCase(this.type)) {
                    System.out.println("Publisher thread");
                    while((inputLine = this.in.readLine()) != null) {
                        System.out.println("Server received: " + inputLine);
                        this.messageList.addMessage(inputLine);
                        if("terminate".equalsIgnoreCase(inputLine)) {
                            System.out.println("Client is disconnecting");
                            break;
                        }
                    }


//                    Otherwise, client is a subscriber
                }else {

                    while((inputLine = this.messageList.getNextMessage(this.head)) != null) {
                        this.head++;
                        out.println("Message from publisher: " + inputLine);
                    }
                }


                this.out.close();
                this.in.close();
                this.clientSocket.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Server thread is exiting");
        }
    }
}



class MessageList {
    private List<String> messageList = new ArrayList<>();

    public synchronized void addMessage(String message) {
        this.messageList.add(message);
        notifyAll();
    }

    public synchronized String getNextMessage(int position) throws InterruptedException {
        if(this.messageList.size() - 1 < position) {
            wait();
        }
        return this.messageList.get(position);
    }

}
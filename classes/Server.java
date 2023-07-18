import java.io.*;
import java.net.*;
import java.util.*;
// import java.util.*;


public class Server{

    private final int port;
    // hashmap to store message lists according to topic 
    private HashMap<String,MessageList> messagePool;

    public Server(int port) {
        this.port = port;
        this.messagePool = new HashMap<String,MessageList>();
    }

    public void start() {
        try {
            try (ServerSocket serverSocket = new ServerSocket(this.port)) {
                System.out.println("Server is listening on port " + this.port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    Thread serverThread = new ServerThread(clientSocket);
                    serverThread.start();

                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    // get message list relevant to topic from message pool
    private MessageList getMessageList(String key) {
        return this.messagePool.get(key);
    }

    // check whether the topic is available
    private boolean isAvailable(String topic) {
        return this.messagePool.get(topic) == null;
    }

    // initialize new messagelist for new topic
    private void insertNewTopic(String key) {
        this.messagePool.put(key, new MessageList());
    }

    class ServerThread extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private MessageList messageList;
        private String type = null;
        private String topic = null;
        int head;

        public ServerThread(Socket clientSocket) {
            System.out.println("Server thread is initiating");
            this.clientSocket = clientSocket;
            this.messageList = null;
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
                    String[] arguments = inputLine.split(" ");
                    System.out.println("Client found: " + arguments[0]);
                    out.println("Hello " + arguments[0]);
                    this.type = arguments[0];
                    this.topic = arguments[1];

                    if(isAvailable(this.topic)) {
                        insertNewTopic(this.topic);
                    }

                    this.messageList = getMessageList(this.topic);
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

    public synchronized void printMessages() {
        System.out.println(this.messageList);
    }

}
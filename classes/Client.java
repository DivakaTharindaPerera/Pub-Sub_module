import java.io.*;
import java.net.*;
import java.util.*;

class Client{
    private int port;
    private String type;
    protected BufferedReader in;
    protected PrintWriter out;
    protected Scanner sc;
    public Client(int port, String type) {
        this.port = port;
        this.type = type;
    }

    public void start() {
        try (Socket socket = new Socket("localhost", this.port)){
            // send to server
            this.out = new PrintWriter(
                    socket.getOutputStream(), true
            );

            //get from server
           this.in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //object of scanner class
            this.sc = new Scanner(System.in);
            //Initial handshake
            this.out.println(this.type);
            this.out.flush();
            //display server reply
            System.out.println("Reply from server: " + this.in.readLine());

            String line = null;
            if(this.type.equalsIgnoreCase("PUBLISHER")) {
                System.out.println("Publishing started......");
                while(! "terminate".equalsIgnoreCase(line)){
                    //reading from server
                    line = sc.nextLine();

                    //sending to server
                    out.println(line);
                    out.flush();
                }

            } else {
                System.out.println("Client Listener listing");
                while(true) {
                    try {
                        if((line = this.in.readLine()) != null) {
                            System.out.println(line);

                            if("TERMINATE".equalsIgnoreCase(line)) {
                                System.out.println("Client subscriber is quiting.....");
                                break;
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//
//
//        try (Socket socket = new Socket("localhost", 5000)){
//
//            // send to server
//            PrintWriter out = new PrintWriter(
//                socket.getOutputStream(), true
//            );
//
//            //get from server
//            BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            //object of scanner class
//            Scanner sc = new Scanner(System.in);
//            String line = null;
//
//            while(! "terminate".equalsIgnoreCase(line)){
//                //reading from server
//                line = sc.nextLine();
//
//                //sending to server
//                out.println(line);
//                out.flush();
//
//                //display server reply
//                System.out.println("Reply from server: " + in.readLine());
//            }
//
//            sc.close();
//
//
//
//        } catch (IOException e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//    }
}

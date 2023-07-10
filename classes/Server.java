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
        
    }
}
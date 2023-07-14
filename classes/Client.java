import java.io.*;
import java.net.*;
import java.util.*;

class Client{
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)){
            
            // send to server
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true
            );

            //get from server
            BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //object of scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;

            while(! "terminate".equalsIgnoreCase(line)){
                //reading from server
                line = sc.nextLine();

                //sending to server
                out.println(line);
                out.flush();

                //display server reply
                System.out.println("Reply from server: " + in.readLine());
            }

            sc.close();



        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateSever {

    private static final int port_number=5119;

    static List<String> clients_messages = new ArrayList<>();


   public static void main(String args[]) throws Exception {



       DatagramSocket sever_socket= new DatagramSocket(port_number);

       ExecutorService pool = Executors.newFixedThreadPool(4);
       System.out.println("Waiting for Client Connection....\nConnect using this port: "+port_number);

     // while(true)
       //{
           System.out.println("*******");
           ClientHandler clientThread = new ClientHandler(sever_socket);
           pool.execute(clientThread);
           //System.out.println();
      //}
   }
}
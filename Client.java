import jdk.nashorn.internal.runtime.ListAdapter;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {


    private static    int port_number;
    public  boolean send =false;
    public  boolean get =false;
    public static boolean status_login =false;
    public static  void main(String[] args) throws Exception {

        Scanner keyboard  = new Scanner(System.in);
        //
        int pin = 5119;
        System.out.print("Enter your identifier: ");
        port_number =keyboard.nextInt();
        System.out.print("Enter recipient pin: ");
        int recipient = keyboard.nextInt();
        DatagramSocket socket = new DatagramSocket(port_number);

        keyboard.nextLine();  //clear scanned free spaces
        while(true)
        {

               System.out.print("Client: ");
               String  buffer = recipient+" : "+keyboard.nextLine();

               if(status_login==false)
               {
                   //get messages while user was offline
                  // buffer =recipient+" : view"
                   DatagramPacket req = new DatagramPacket((recipient+" : view").getBytes(),( recipient+" : view").getBytes().length, socket.getLocalAddress(), Math.toIntExact(pin));
                   socket.send(req);
                   status_login=true;
               }
               else
               {
                   if (buffer.equals("quit")) {
                       DatagramPacket req = new DatagramPacket(buffer.getBytes(), buffer.getBytes().length, socket.getLocalAddress(), Math.toIntExact(pin));
                       socket.send(req);
                       break;
                   } else {

                       //send connection

                       DatagramPacket req = new DatagramPacket(buffer.getBytes(), buffer.getBytes().length, socket.getLocalAddress(), Math.toIntExact(pin));
                       socket.send(req);

                       //receive connection
                       byte[] r = new byte[512 * 2];
                       DatagramPacket response = new DatagramPacket(r, r.length);
                       socket.receive(response);
                       System.out.println(new String(response.getData()));

                   }
               }
        }

    }

          // System.exit(0);
}





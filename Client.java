import jdk.nashorn.internal.runtime.ListAdapter;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {


    private static    int port_number;
    public  boolean send =false;
    public  boolean get =false;
    public static String status ="Receive";
    public static  void main(String[] args) throws Exception {

        Scanner keyboard  = new Scanner(System.in);
        //
        int pin = 5119;
        System.out.print("Enter your identifier: ");
        port_number =keyboard.nextInt();
        System.out.print("Enter recipient pin: ");
        int recipient = keyboard.nextInt();
        DatagramSocket socket = new DatagramSocket(port_number);

        String trigger = keyboard.nextLine();  //clear scanned free spaces
        while(true)
        {
            //receive connection
            byte[] r = new byte[512 * 2];
            DatagramPacket response = new DatagramPacket(r, r.length);
            Timer time  = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                    try {
                        socket.receive(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!new String(response.getData()).equalsIgnoreCase(""))
                         System.out.println(new String(response.getData()));
                   // System.out.println("*");
                }
            };
            time.schedule(task,1000);
                System.out.print("Client: ");
                String  buffer = recipient+" : "+keyboard.nextLine();

                if (buffer.equals("quit")) {
                    DatagramPacket req = new DatagramPacket(buffer.getBytes(), buffer.getBytes().length, socket.getLocalAddress(), Math.toIntExact(pin));
                    socket.send(req);
                    break;
                } else {

                    //send connection

                    DatagramPacket req = new DatagramPacket(buffer.getBytes(), buffer.getBytes().length, socket.getLocalAddress(), Math.toIntExact(pin));
                    socket.send(req);

                }

        }

    }

          // System.exit(0);
}





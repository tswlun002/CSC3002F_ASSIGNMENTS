
import java.io.*;
import java.net.*;
import java.util.*;
public class Client {


    private static    int port_number;
    public  boolean send =false;
    public  boolean get =false;
    public static String status ="Receive";
    public static  void main(String[] args) throws Exception {

        Scanner keyboard = new Scanner(System.in);
        //get port number from user
        System.out.print("Enter your identifier: ");
        port_number = keyboard.nextInt();

        //get destination port number
        System.out.print("Enter recipient pin: ");
        int recipient = keyboard.nextInt();
        String trigger = keyboard.nextLine();  //clear scanned free spaces

        //bind the sender port number
        DatagramSocket socket = new DatagramSocket(port_number);

        //make receive object and start its thread
        Recieve recieve = new Recieve(socket);
        Thread receiveThread = new Thread(recieve);
        receiveThread.start();

        //make send object and start its thread
        Send send = new Send(recipient, socket);
        Thread sendThread = new Thread(send);
        sendThread.start();
    }
}
class  Recieve implements  Runnable
{

    public static String status ="Receive";
    DatagramSocket socket;
    public Recieve(DatagramSocket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        //receive connection
        boolean re =true;
        while(re==true)
        {
            System.out.println("waiting to receive1....");
            byte[] r = new byte[1024];
            DatagramPacket response = new DatagramPacket(r, r.length);
            try {
                socket.receive(response);
                System.out.println("waiting to receive2....");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (new String(response.getData()).equalsIgnoreCase("quit")) {
                System.out.println(new String(response.getData()));
                re =false;
                //break;
            }else {
                System.out.println(re);
                System.out.println(new String(response.getData()));
                re=true;
            }
        }
    }
}
class  Send implements  Runnable {
    private static   int recipient;
    DatagramSocket socket;
    public  Send (int recipient,DatagramSocket socket) throws SocketException {
        this.socket =socket;
        this.recipient =recipient;
        System.out.println("Print something fo the second time....");
    }
    @Override
    public void run() {


        while (true)
        {
            Scanner keyboard = new Scanner(System.in);
            System.out.print("Client: ");

           // int recipient = keyboard.nextInt();
            String buffer = recipient + " : " + keyboard.nextLine();

            if (buffer.equals("quit")) {
                //send connection
                DatagramPacket req = new DatagramPacket(buffer.getBytes(), buffer.getBytes().length, socket.getLocalAddress(), Math.toIntExact(5119));
                try {
                    socket.send(req);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } else {

                //send connection
                DatagramPacket req = new DatagramPacket(buffer.getBytes(), buffer.getBytes().length, socket.getLocalAddress(), Math.toIntExact(5119));
                try {
                    socket.send(req);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}






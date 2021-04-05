
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable
{

    private   DatagramSocket sever_socket ;
    List<String> user_messages = new ArrayList<>();

    public  ClientHandler(DatagramSocket sever_socket)
    {

        this.sever_socket = sever_socket;
    }

    @Override
    public void run() {
        try
        {
            DatagramPacket receivedPecket;
            int port_recipient = 0;
            byte[] buffer = new byte[1024];
            while (true) {


               //receive data
                receivedPecket = new DatagramPacket(buffer, 0, buffer.length);
                sever_socket.receive(receivedPecket);

                //get identifier of user message is being send to
                String received_mess = new String(receivedPecket.getData());
                //int len = ;
                port_recipient = Integer.parseInt(received_mess.substring(0, received_mess.indexOf(":")).trim());
                String sender_message =received_mess /*received_mess.substring(received_mess.indexOf(":")+1,received_mess.length())*/;

                //print connection status
                System.out.println("From > " + receivedPecket.getPort()+" Connection status > " +sever_socket.isConnected());


                //sending and storing data
                if (new String(receivedPecket.getData()).equalsIgnoreCase("quit")) {

                    DatagramPacket sending_to_client = new DatagramPacket(sender_message.getBytes(StandardCharsets.UTF_8), sender_message.length(), receivedPecket.getAddress(), port_recipient);

                    sever_socket.send(sending_to_client);
                    System.out.println("client massage: " + new String(sending_to_client.getData()));
                    break;
                }
                else
                    {
                    DatagramPacket sending_to_client = new DatagramPacket(sender_message.getBytes(StandardCharsets.UTF_8), sender_message.length(), receivedPecket.getAddress(), port_recipient);

                    sever_socket.send(sending_to_client);
                    System.out.println("client massage: " + new String(sending_to_client.getData()));
                    System.out.println("From > " + receivedPecket.getPort()+" Connection status > " +sever_socket.isConnected());
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}


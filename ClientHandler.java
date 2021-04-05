
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
                port_recipient = Integer.parseInt(received_mess.substring(0, received_mess.indexOf(":")).trim());

                //print connection status
                System.out.println("From > " + receivedPecket.getPort()+" Connection status > " +sever_socket.isConnected());


                //sending and storing data
                if (new String(receivedPecket.getData()).equalsIgnoreCase("quit")) {

                    DatagramPacket sending_to_client = new DatagramPacket((new String(receivedPecket.getData())).getBytes(StandardCharsets.UTF_8), (new String(receivedPecket.getData()).length()), receivedPecket.getAddress(), port_recipient);

                    sever_socket.send(sending_to_client);
                    System.out.println("client massage: " + new String(sending_to_client.getData()));
                    break;
                }
                else if (new String(receivedPecket.getData()).equalsIgnoreCase("view"))
                {
                    DatagramPacket sending_to_client = new DatagramPacket((new String(receivedPecket.getData())).getBytes(StandardCharsets.UTF_8), (new String(receivedPecket.getData()).length()), receivedPecket.getAddress(), port_recipient);
                    sever_socket.send(sending_to_client);
                }
                else
                    {
                    DatagramPacket sending_to_client = new DatagramPacket((new String(receivedPecket.getData())).getBytes(StandardCharsets.UTF_8), (new String(receivedPecket.getData()).length()), receivedPecket.getAddress(), port_recipient);

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


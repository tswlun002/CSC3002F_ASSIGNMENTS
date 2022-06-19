

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable
{
    /**
     * @serialField  password  - boolean check if client password correct or not
     * @serialField  sever_socket  - datagram packet of the server
     * @serialField   buffer  - sizer of bytes server can receive and send
     */
    boolean password = false;
    private final DatagramSocket sever_socket ;
    static  final  byte[] buffer = SeverDetails.getBuffer();
    int connected=0;
    String username;

    /**
     * Set sever socket
     * @param sever_socket  DatagramSocket sever socket
     */
    public  ClientHandler(DatagramSocket sever_socket)
   {
      this.sever_socket = sever_socket;
   }

    /**
     * Connect  client to sever .
     * First  check if the client is registered or not  by checking password
     * If password is wrong, message is sent to client that their detail are not valid
     * @throws IOException for receiving wrong datagram packet
     */
    void  connect() throws IOException {

        DatagramPacket receivedPacket = new DatagramPacket(buffer, 0, buffer.length);
        sever_socket.receive(receivedPacket);
        String msg = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
        username = msg.split("#")[0];
        String pw = msg.split("#")[1];
        LogIn log = new LogIn(username, pw);
        password = log.isCorrect();
        if(!password){
            DatagramPacket sending_to_client = new DatagramPacket("false".getBytes(StandardCharsets.UTF_8),5,
                    receivedPacket.getAddress(), receivedPacket.getPort());
            sever_socket.send(sending_to_client);
        }else{
            /*DatagramPacket sending_to_client = new DatagramPacket(("On-Line-"+username).getBytes(StandardCharsets.UTF_8), 4,
                    receivedPacket.getAddress(), receivedPacket.getPort());
            sever_socket.send(sending_to_client);*/
            connected++;
        }
   }

    /**
     * Receive data from client
     * Port packet and make reply message
     * @return  true  if client send quit message else false
     * @throws IOException if receive corrupted  datagram packet
     */
   boolean receive_data() throws IOException {
       DatagramPacket receivedPacket  = new DatagramPacket(buffer, 0, buffer.length);
       sever_socket.receive(receivedPacket);
       String received_mess = new String(receivedPacket.getData());
       int port_recipient = Integer.parseInt(received_mess.substring(0, received_mess.indexOf(":")).trim());
       String sender_message = username+": "+received_mess.substring(received_mess.indexOf(":")+1);
       return  auto_reply(receivedPacket,port_recipient, sender_message);
   }

    /**
     * Auto reply to client by notifying if message is received and state about status of connection
     * Shutdown server when user sent quit message
     * @param receivedPacket datagram packet receive
     * @param port_recipient port number of client
     * @param sender_message message send to client
     * @return true if message client is quit else false
     * @throws IOException if sent corrupted packet
     */
   boolean auto_reply(DatagramPacket receivedPacket ,int port_recipient , String sender_message) throws IOException {
       //sending and storing data
       boolean stop = false;
       String rcv =   new String(receivedPacket.getData());
       if (rcv.equalsIgnoreCase("quit")) {

           DatagramPacket sending_to_client = new DatagramPacket(sender_message.getBytes(StandardCharsets.UTF_8),
                   sender_message.length(), receivedPacket.getAddress(), port_recipient);
           sever_socket.send(sending_to_client);
           stop = true;
       }
       else
       {
           DatagramPacket sending_to_client = new DatagramPacket(sender_message.getBytes(StandardCharsets.UTF_8),
                   sender_message.length(), receivedPacket.getAddress(), port_recipient);
           sever_socket.send(sending_to_client);
       }
       return stop;
   }

    /**
     * Thread allow server to connect with client and keep receiving and sending messages
     * Allow two clients to connect
     */
   @Override
   public void run() {
      try
      {
         while(connected<SeverDetails.getAllowClients()){
             connect();
         }
         while ( connected>1) {
              if (receive_data()) {

                  sever_socket.close();
                  break;
              }
         }
      }catch (Exception e)
      {
         e.printStackTrace();
      }
   
   }

}
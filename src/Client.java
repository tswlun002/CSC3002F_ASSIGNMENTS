
import java.io.IOException;
import java.net.*;
import javax.swing.JTextArea;

class Client implements  Runnable
{
   DatagramSocket socket;
    JTextArea textArea;
    int recipient;

    /**
     *  CONSTRUCT TO INITIALISE SERIAL FIELDS
     * @param socket -initialise client datagram packet
     * @param jTextArea - initialise text area or text box where client type message
     * @param recipient  - port number of the other client to receive message
     */
   public Client(DatagramSocket socket, JTextArea jTextArea, int recipient){
      this.socket=socket;
      this.recipient = recipient;
      this.textArea = jTextArea;
   }

    /**
     * Client receive message
     * Finally closes socket
     */
   @Override
   public void run() {
      while(true)
      {
          try {
              DatagramPacket response  = new DatagramPacket(SeverDetails.getBuffer(), 0,SeverDetails.getBuffer().length);
              socket.receive(response);
              String rec = new String(response.getData());
              if(rec.equals("On-Line-A")){
                  rec = "";
              }
              if(rec.equals("On-Line-B")){
                  rec = "";
              }
              textArea.append("\n"+recipient+":"+rec);
          } catch (IOException e) {
              e.printStackTrace();
          }
          finally {
              // socket.close();
          }
      }
   }
}

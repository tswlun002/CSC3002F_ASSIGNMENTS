
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sever {

	/***
	 * @serialField  port_number sever binding port number
	 */
	private static final int port_number= (int) SeverDetails.getSever_port();

	/***
	 * Execute sever( client handler) to receive and forward messages
	 * @param args list of inputs
	 * @throws Exception when can't make new datagram packet
	 */
   public static void main(String[] args) throws Exception {
       	DatagramSocket sever_socket= new DatagramSocket(port_number);
       	ExecutorService pool = Executors.newFixedThreadPool(SeverDetails.getAllowClients());
		ClientHandler clientThread = new ClientHandler(sever_socket);
		pool.execute(clientThread);
      
   }
}

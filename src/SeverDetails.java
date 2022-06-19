import java.net.InetAddress;
import java.net.UnknownHostException;

public class SeverDetails {
    /**
     * @serialField  sever_port  - port of the sever for the system/application
     * @serialField  buffer - size of bytes application accepts at a time
     */
   private static  final long sever_port = 5119;

   private static final byte[] buffer = new byte[1024];

   private static final int allowClients = 2;

    /***
     * Make Address using localhost
     * @return  localhost  address
     * @throws UnknownHostException when can't create address using local host
     */
    static  InetAddress getInet() throws UnknownHostException {
        return InetAddress.getByName("localhost");
    }
    /**
     * @return sever port number
     */
   static  long getSever_port(){
       return sever_port;
   }

    /**
     * @return buffer size of sever
     */
   static byte[] getBuffer(){return buffer;}

    /**
     * @return max number of customer allowed to use system
     */
    public static int getAllowClients() {
       return  allowClients;
    }
}

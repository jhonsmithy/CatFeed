package dev.iotml.ru.catfeed;



import java.io.IOException;
        import java.net.DatagramPacket;
        import java.net.InetAddress;
        import java.net.InetSocketAddress;
        import java.net.MulticastSocket;
        import java.net.NetworkInterface;
        import java.net.SocketAddress;

public class SSDPSocket {
    SocketAddress mSSDPMulticastGroup;
    MulticastSocket mSSDPSocket;

    public SSDPSocket() throws IOException {
        InetAddress localInAddress = InetAddress.getLocalHost(); //crashes here first**
        System.out.println("Local address: " + localInAddress.getHostAddress());

        mSSDPMulticastGroup = new InetSocketAddress(SSDP.ADDRESS, SSDP.PORT);
        mSSDPSocket = new MulticastSocket(new InetSocketAddress(localInAddress,4210));

        NetworkInterface netIf = NetworkInterface
                .getByInetAddress(localInAddress);
        mSSDPSocket.joinGroup(mSSDPMulticastGroup, netIf);
    }

    /* Used to receive SSDP packet */
    public DatagramPacket receive() throws IOException {
        byte[] buf = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buf, buf.length);

        mSSDPSocket.receive(dp);

        return dp;
    }

    public void close() {
        if (mSSDPSocket != null) {
            mSSDPSocket.close();
        }
    }

}

 class SSDP {
    /* New line definition */
    public static final String NEWLINE = "\r\n";

    public static final String ADDRESS = "239.255.255.255"; //239.255.255.255
    public static final int PORT = 4210;                    //4210

    /* Definitions of start line */
    public static final String SL_NOTIFY = "NOTIFY * HTTP/1.1";
    public static final String SL_MSEARCH = "M-SEARCH * HTTP/1.1";
    public static final String SL_OK = "HTTP/1.1 200 OK";

    /* Definitions of search targets */
    public static final String ST_RootDevice = "ST:rootdevice";
    public static final String ST_ContentDirectory = "ST:urn:schemas-upnp- org:service:ContentDirectory:1";

    /* Definitions of notification sub type */
    public static final String NTS_ALIVE = "NTS:ssdp:alive";
    public static final String NTS_BYE = "NTS:ssdp:byebye";
    public static final String NTS_UPDATE = "NTS:ssdp:update";
}




class SSDPSearchMsg {

    static final String HOST = "Host:" + SSDP.ADDRESS + ":" + SSDP.PORT;
    static final String MAN = "Man:ssdp:discover";

    int mMX = 3;    /* seconds to delay response */
    String mST;     /* Search target */

    public SSDPSearchMsg(String ST) {
        mST = ST;
    }

    public int getmMX() {
        return mMX;
    }

    public void setmMX(int mMX) {
        this.mMX = mMX;
    }

    public String getmST() {
        return mST;
    }

    public void setmST(String mST) {
        this.mST = mST;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        content.append(SSDP.SL_MSEARCH).append(SSDP.NEWLINE);
        content.append(HOST).append(SSDP.NEWLINE);
        content.append(MAN).append(SSDP.NEWLINE);
        content.append(mST).append(SSDP.NEWLINE);
        content.append("MX:" + mMX).append(SSDP.NEWLINE);
        content.append(SSDP.NEWLINE);

        return content.toString();
    }
}
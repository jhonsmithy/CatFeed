package dev.iotml.ru.catfeed;

//import java.net.SocketException;

import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UdpBrowser {
    // Find the server using UDP broadcast

    DatagramSocket scpg;
    String ReciveMsg;
    public static final String ADDRESS = "239.255.255.255"; //
    public static final int PORT = 4210;                    //
    public static boolean running = true;

    public void receive_data(){
        byte[] receiveData = new byte[1024];
        String response_ip;
        DatagramSocket clientSocket = null; //create socket to transport data
        try {
            clientSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            Log.d("ssdp", "Socket Exception thrown when creating socket to transport data");
            e.printStackTrace();
        }
        System.out.println(getClass().getName() + ">>> CREATE !!! clientSocket") ;
        // receive data
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        System.out.println(getClass().getName() + ">>> CREATE !!! DatagramPacket");
        try {
            if (clientSocket != null) {
                while(running){
                    clientSocket.receive(receivePacket);
                    response_ip = new String(receivePacket.getAddress().getHostAddress());
                    System.out.println(getClass().getName() + " >>> Find from receivePacket "+ response_ip);
                    SystemClock.sleep(1000); //ms
                    //Log.d("ssdp","Checking target package to see if its empty on iteration#: ");
                }
            }else {System.out.println(getClass().getName() + ">>> ClientSocket is NULL Object");}
        } catch (IOException e) {
            Log.d("ssdp","IOException thrown when receiving data");
            e.printStackTrace();
        }
    }

    public void read_udp_paket()
    {

        //Wait for a response
        byte[] recvBuf = new byte[15000];
        DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);

            try {
                //scpg.setBroadcast(true);
                scpg.receive(receivePacket);
            } catch (Exception e) {}


            //We have a response
        String message = new String(receivePacket.getData()).trim();
            //Check if the message is correct
            //String message = new String(receivePacket.getData()).trim();
            System.out.println(getClass().getName() + "UDP Recive bytes:"+message);
    }

    public void Mainfunc()
    {
        try {
            scpg = new DatagramSocket();
            scpg.setBroadcast(true);
            byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();
            //Try the 255.255.255.255 first
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("239.255.255.255"), 4210);
                scpg.send(sendPacket);
                System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            } catch (Exception e) {
            }
            // Broadcast the message over all the network interfaces
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }

                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }

                    // Send the broadcast package!
                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 4210);
                        scpg.send(sendPacket);
                    } catch (Exception e) {
                    }

                    System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                }
            }
            System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");

            //Wait for a response
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            System.out.println(getClass().getName() + "UDP Recive bytes1:");
            SystemClock.sleep(1); //ms
            System.out.println(getClass().getName() + "UDP Recive bytes2:");
            while (true) {
                SystemClock.sleep(1); //ms
                try {
                    scpg.receive(receivePacket);
                } catch (Exception e) {

                }

                //We have a response

                //Check if the message is correct
                //String message = new String(receivePacket.getData()).trim();
                System.out.println(getClass().getName() + "UDP Recive bytes3:");
/*
                if (message != null) {
                    System.out.println(getClass().getName() + "UDP Recive " + receivePacket.toString());
                }
                if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
                    //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
                    //Controller_Base.setServerIp(receivePacket.getAddress());
                    System.out.println(receivePacket.getAddress());
                    System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.toString());//receivePacket.getAddress().getHostAddress());
                    ReciveMsg = receivePacket.getAddress().toString();
                }
*/
            }
            //scpg.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            //Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

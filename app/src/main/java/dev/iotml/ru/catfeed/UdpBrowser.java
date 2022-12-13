package dev.iotml.ru.catfeed;

//import java.net.SocketException;

import android.os.SystemClock;
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

    //try {
        //Open a random port to send the package
    DatagramSocket scpg;
    String ReciveMsg;
    public void PrintMainToast(){
        //Toast.makeText(this, "Printed", Toast.LENGTH_SHORT).show();
        System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
    }

    public void Mainfunc()
    {
        try {
            scpg = new DatagramSocket();
            scpg.setBroadcast(true);
            byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();
            //Try the 255.255.255.255 first
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
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
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
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
            SystemClock.sleep(200); //ms
            try {
                scpg.receive(receivePacket);
            } catch (Exception e) {

            }



            //We have a response

            //Check if the message is correct
            String message = new String(receivePacket.getData()).trim();
            if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
                //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
                //Controller_Base.setServerIp(receivePacket.getAddress());
                System.out.println(receivePacket.getAddress());
                System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.toString());//receivePacket.getAddress().getHostAddress());
                ReciveMsg = receivePacket.getAddress().toString();
            }
            else
            {ReciveMsg="empty";}

            scpg.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            //Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

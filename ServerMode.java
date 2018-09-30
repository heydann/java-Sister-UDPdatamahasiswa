/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PertemuanSocket;


import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author SUBARI
 */
public class ServerMode {
    
    public static final int SERVICE_PORT = 8;
    public static final int BUFSIZE = 4096;
    DatagramSocket socket ;
    
    public ServerMode(){
        try {
            socket = new DatagramSocket(SERVICE_PORT);
            System.out.println("Server active on port " + socket.getLocalPort());
            
        } catch (Exception e) {
            System.out.println("Unable to bind port");
        }
    }
    
    public void createAndListenSocket() {
        byte[] buffer = new byte[BUFSIZE];
        boolean run = true;
        while (run) {            
          try {
            DatagramPacket packet = new DatagramPacket(buffer, BUFSIZE);
            socket.receive(packet);
            System.out.println("Received packet from " + packet.getAddress() + ":" + packet.getPort() + " of length " + packet.getLength());
            
            String data = new String(packet.getData(), 0, packet.getLength());
            if(data.equalsIgnoreCase("5")){
                run = false;
            }else{
                try{
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("databaru.ser"));
                        for(int i =0; i<data.length(); i++){
                            out.writeObject(data);
                        }
                        out.flush();
                        out.close();
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }
            
            socket.send(packet);
          } catch(IOException ioe) {
              System.out.println("Error: " + ioe);
          }
        }    
    }
    
    public static void main(String[] args) {
        ServerMode server = new ServerMode();
        server.createAndListenSocket();
    }

}

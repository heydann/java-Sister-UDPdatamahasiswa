/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PertemuanSocket;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
/**
 *
 * @author SUBARI
 */
public class ServerMode {
    
    DatagramSocket socket = null;
    
    public ServerMode(){
        
    }
    
    public void createAndListenSocket() {
        try {
            socket = new DatagramSocket(9876);
            byte[] inComingData = new byte[1024];
            
            while (true){
                DatagramPacket incomingPacket = new DatagramPacket(inComingData, inComingData.length);
                socket.receive(incomingPacket);
                byte[] data = incomingPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                
                    try {
                        Mahasiswa mahasiswa = (Mahasiswa) is.readObject();
                        System.out.println("Mahasiswa object received = " + mahasiswa);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    
                InetAddress IPAddress = incomingPacket.getAddress();
                int port = incomingPacket.getPort();
                String reply = "Thanks for your message";
                byte[] replyBytea = reply.getBytes();
                DatagramPacket replyPacket = new DatagramPacket(replyBytea, replyBytea.length, IPAddress, port);
                socket.send(replyPacket);
                Thread.sleep(2000);
                System.exit(0);
            }
            
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException i ){
            i.printStackTrace();
        } catch (InterruptedException exi){
            exi.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ServerMode server = new ServerMode();
        server.createAndListenSocket();
    }

}

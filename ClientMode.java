/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PertemuanSocket;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.in;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author SUBARI
 */
public class ClientMode {
    
    DatagramSocket socket;
    
    public ClientMode(){
        
    }
    //ArrayList<Mahasiswa> mahasiswa = new ArrayList<Mahasiswa>();
    
    
    public void createAndListenSocket(){
        
        
            //choice provided to the user
            System.out.println("Welcome To Mahasiswa Praktikum Sister");
            System.out.println("Enter your choice");
            System.out.println("Enter 1:->show mahasiswa");
            System.out.println("Enter 2:->Create new mahasiswa");
            System.out.println("Enter 3:->Delete mahasiswa");
            System.out.println("Enter 4:->quit");
            System.out.println("Enter 5:->Update mahasiswa");
            
            Scanner in = new Scanner(System.in);
            int choice = in.nextInt();
        try {
            

            
            //show 
            if (choice == 2) {
                
            
            socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] incomingData = new byte[1024];
            String str1, str2, str3;
            
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter data of mahasiswa you want to add");
            Scanner in1 = new Scanner(System.in);
            
            str1 = reader.readLine(); //nim
            str2 = reader.readLine(); //nama
            str3 = reader.readLine(); //alamat
            
            Mahasiswa mahasiswa = new Mahasiswa(str1, str2, str3);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(mahasiswa);
            

            
            byte[] data = outputStream.toByteArray();
            
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
            socket.send(sendPacket);
            System.out.println("Message sent from client");
            
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            
            socket.receive(incomingPacket);
            
            String response = new String (incomingPacket.getData());
            System.out.println("Response from server : " + response);
            Thread.sleep(2000);
            
            FileOutputStream fos = new FileOutputStream("Datamhs.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(mahasiswa);
            oos.flush();
            oos.close();

            }
            
            //show 
            //untuk show masih belum bisa menampilkan
            if (choice == 1) {
                try{
                FileInputStream  fis = new FileInputStream("Datamhs.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                ArrayList object2 = (ArrayList)ois.readObject();

                ois.close();
                System.out.println("mahasiswa"+" "+ object2 + " ");
            }
            catch(Exception e){
                e.printStackTrace();
            }
            }
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException es) {
            es.printStackTrace();
        } catch (InterruptedException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public static void main(String[] args)  {
        ClientMode client = new ClientMode();
        client.createAndListenSocket();
    }
}

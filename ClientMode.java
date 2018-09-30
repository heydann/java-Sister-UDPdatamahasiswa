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
import java.io.PrintStream;
import static java.lang.System.in;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author SUBARI
 */
public class ClientMode {
    
    public static final int SERVICE_PORT = 8;
    public static final int BUFSIZE = 256;
    DatagramSocket socket;
    
    public ClientMode(){
        
    }
    
    
    
    public static void main(String[] args) throws UnknownHostException, SocketException {
        
        List<Mahasiswa> mahasiswa = new ArrayList<Mahasiswa>();
        String nim, nama, alamat, kelas;
        int posisi;
        boolean run = true;
        
        SerializationDemo demo = new SerializationDemo();
        String direktori = "Datamahasiwa.ser";
        
        String hostname = "localhost";
        InetAddress addr = InetAddress.getByName(hostname);

        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(2000);
        
        try {
            int choice;
            Scanner input = new Scanner(System.in);
            while (true) {                
                System.out.println("==================================");
                System.out.println("Welcome To Mahasiswa Praktikum Sister");
                
                
                System.out.println("Enter 1:->Create new data mahasiswa");
                System.out.println("Enter 2:->Update data mahasiswa");
                System.out.println("Enter 3:->Delete data mahasiswa");
                System.out.println("Enter 4:->Print data mahasiswa");
                System.out.println("Enter 5:-> QUIT");
                choice = input.nextInt();
                
                if (choice == 1) {
                    System.out.println("==================================");
                    System.out.print("NIM : ");
                    nim = input.next();
                    System.out.print("Nama : ");
                    nama = input.next();
                    System.out.print("Alamat : ");
                    alamat = input.next();
                    System.out.print("Kelas : ");
                    kelas = input.next();
                    
                    mahasiswa.add(new Mahasiswa(nim, nama, alamat, kelas));
                    System.out.println("\n" + mahasiswa);

                    demo.serialize(mahasiswa, direktori);
                    System.out.println("Save Successful");
                    
                    //send to server
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    PrintStream pout = new PrintStream(bout);
                    pout.print(mahasiswa);

                    byte[] barray = bout.toByteArray();

                    DatagramPacket packet = new DatagramPacket(barray, barray.length, addr, SERVICE_PORT);
                    System.out.println("\nSending packet...");
                    socket.send(packet);
                    System.out.println(packet + " send!");
                } else if(choice == 2){
                    System.out.println("==================================");
                    System.out.print("Update data Index ");
                    posisi = input.nextInt();
                    if (posisi > mahasiswa.size() - 1) {
                        System.out.println("Index Not Found!");
                        break;
                    }

                    System.out.print("Data : " + mahasiswa.get(posisi));
                    System.out.println("================");
                    System.out.print("\nNIM : ");
                    nim = input.next();
                    System.out.print("Nama : ");
                    nama = input.next();
                    System.out.print("Alamat : ");
                    alamat = input.next();
                    System.out.print("Kelas : ");
                    kelas = input.next();
                    
                    Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, kelas);

                    mahasiswa.remove(posisi);
                    mahasiswa.add(posisi, mhs);
                    System.out.println("\n" + mahasiswa);

                    demo.serialize(mahasiswa, direktori);
                    System.out.println("Save Successful");
                    
                    //send update data to server
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    PrintStream pout = new PrintStream(bout);
                    pout.print(mahasiswa);

                    byte[] barray = bout.toByteArray();

                    DatagramPacket packet = new DatagramPacket(barray, barray.length, addr, SERVICE_PORT);
                    System.out.println("\nSending packet...");
                    socket.send(packet);
                    System.out.println(packet + " send!");
                    
                }
                else if(choice == 3){
                    System.out.println("==================================");
                    System.out.print("Delete data Mahasiswa : ");
                    posisi = input.nextInt();
                    if (posisi > mahasiswa.size() - 1) {
                        System.out.println("Index Not Found!");
                        break;
                    }
                    
                    Mahasiswa mhs = mahasiswa.get(posisi);
                    mahasiswa.remove(posisi);

                    demo.serialize(mahasiswa, direktori);
                    System.out.println("Delete Successful");
                    
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    PrintStream pout = new PrintStream(bout);
                    pout.print(mahasiswa);

                    byte[] barray = bout.toByteArray();

                    DatagramPacket packet = new DatagramPacket(barray, barray.length, addr, SERVICE_PORT);
                    System.out.println("\nSending packet...");
                    socket.send(packet);
                    System.out.println(packet + " send!");
                }
                else if(choice == 4){
                    System.out.println("==================================");
                    System.out.println("Data of mahasiswa : ");

                    int i = 0;
                    for (Mahasiswa mhs : demo.deserialize(direktori)) {
                        System.out.printf("%d. %s", i++, mhs);
                        System.out.println("");
                    }
                }
                else if(choice == 5){
                    System.out.println("==================================");
                    System.out.println("You're logout");
                    
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    PrintStream pout = new PrintStream(bout);
                    pout.print(choice);

                    byte[] barray = bout.toByteArray();

                    DatagramPacket packet = new DatagramPacket(barray, barray.length, addr, SERVICE_PORT);
                    socket.send(packet);
                    socket.close();
                    run = false;
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}



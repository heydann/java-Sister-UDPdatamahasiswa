/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PertemuanSocket;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SUBARI
 */
public class SerializationDemo {
    
    public void serialize(List<Mahasiswa> pList, String fileName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(pList);

            out.flush();
            out.close();
        }catch(IOException ex){
            System.out.println("A Problem Occured during Serialization\n"+ex.getMessage());
        }
    }
    
    public List<Mahasiswa> deserialize(String fileName) throws ClassNotFoundException{
        List<Mahasiswa> pList = null;
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            pList = (List<Mahasiswa>) in.readObject();
        }catch(IOException ex){
            System.out.printf("A Problem Occured during Deserializing %s%n", fileName);
            System.out.println(ex.getMessage());
        }
        return pList;
    }
}

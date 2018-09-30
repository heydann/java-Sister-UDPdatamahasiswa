/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PertemuanSocket;

import java.io.Serializable;

/**
 *
 * @author SUBARI
 */
public class Mahasiswa implements Serializable{
    
    private String nim;
    private String nama;
    private String alamat;
    private String kelas;
 
    public Mahasiswa (String nim, String nama, String alamat, String kelas){
        this.nim = nim;
        this.nama = nama;
        this.alamat = alamat;
        this.kelas = kelas;
    }
    
    public String getNim(){
        return nim;
    }
    
    public void setNim(String nim){
        this.nim = nim;
    }
    
    public String getNama(){
        return nama;
    }
    
    public void setNama(String nama){
        this.nama = nama;
    }
    
    public String getAlamat(){
        return alamat;
    }
    
    public void setAlamat(String alamat){
        this.alamat = alamat;
    }
    
    public String getKelas(){
        return kelas;
    }
    
    public void setKelas(String kelas){
        this.kelas = kelas;
    }
    public String toString(){
        return "\nNim : " + getNim() + "\nNama : " + getNama() + "\nAlamat : " + getAlamat() + "\nKelas : " + getKelas();
    }
       
}

package com.example.kegafirstapp.testxml;

/**
 * Created by Kega on 7/20/2016.
 */
public class Test {
    private String kode;
    private String nama;

    public String getKode(){
        return kode;
    }

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getNama(){
        return nama;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    @Override
    public String toString() {
        return "Test [kode=" + kode + ", nama=" + nama + "]";
    }
}

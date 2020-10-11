package com.danieloloan.pbp_uts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Booked  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "nama")
    public String simpan_nama;

    @ColumnInfo (name = "address")
    public String simpan_address;

    @ColumnInfo (name = "namaMobil")
    public String simpan_namaMobil;

    @ColumnInfo (name = "lamaSewa")
    public int simpan_lamaSewa;

    @ColumnInfo (name = "harga")
    public int simpan_harga;

    public int getId() {return id;}

    public void setId(int id) {this.id=id;}

    public String getSimpan_nama() {return simpan_nama;}

    public void setSimpan_nama(String simpan_nama) {this.simpan_nama = simpan_nama;}

    public String getSimpan_address() {return simpan_address;}

    public void setSimpan_address(String simpan_address) {this.simpan_address = simpan_address;}

    public String getSimpan_namaMobil() {return simpan_namaMobil;}

    public void setSimpan_namaMobil(String simpan_namaMobil) {this.simpan_namaMobil = simpan_namaMobil;}

    public int getSimpan_lamaSewa() {return simpan_lamaSewa;}

    public void setSimpan_lamaSewa(int simpan_lamaSewa) {this.simpan_lamaSewa = simpan_lamaSewa;}

    public String getStringSimpan_lamaSewa() {return String.valueOf(simpan_lamaSewa);}

    public void setStringSimpan_lamaSewa(String simpan_lamaSewa) {this.simpan_lamaSewa =Integer.parseInt(simpan_lamaSewa);}

    public int getSimpan_harga() {return simpan_harga;}

    public void setSimpan_harga(int simpan_harga) {this.simpan_harga = simpan_harga;}

    public String getStringSimpan_harga() {return String.valueOf(simpan_harga);}

    public void setStringSimpan_harga(String simpan_harga) {this.simpan_harga =Integer.parseInt(simpan_harga);}

}
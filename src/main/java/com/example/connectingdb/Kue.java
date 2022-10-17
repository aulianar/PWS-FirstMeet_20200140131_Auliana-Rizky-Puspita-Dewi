/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.connectingdb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author USER DJOGJA
 */
@Entity
@Table(name = "kue")
@NamedQueries({
    @NamedQuery(name = "Kue.findAll", query = "SELECT k FROM Kue k"),
    @NamedQuery(name = "Kue.findByKodeKue", query = "SELECT k FROM Kue k WHERE k.kodeKue = :kodeKue"),
    @NamedQuery(name = "Kue.findByJenisKue", query = "SELECT k FROM Kue k WHERE k.jenisKue = :jenisKue"),
    @NamedQuery(name = "Kue.findByHarga", query = "SELECT k FROM Kue k WHERE k.harga = :harga"),
    @NamedQuery(name = "Kue.findByStock", query = "SELECT k FROM Kue k WHERE k.stock = :stock")})
public class Kue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Kode_Kue")
    private String kodeKue;
    @Basic(optional = false)
    @Column(name = "Jenis_Kue")
    private String jenisKue;
    @Basic(optional = false)
    @Column(name = "Harga")
    private int harga;
    @Basic(optional = false)
    @Column(name = "Stock")
    private String stock;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "kodeKue")
    private Transaksi transaksi;

    public Kue() {
    }

    public Kue(String kodeKue) {
        this.kodeKue = kodeKue;
    }

    public Kue(String kodeKue, String jenisKue, int harga, String stock) {
        this.kodeKue = kodeKue;
        this.jenisKue = jenisKue;
        this.harga = harga;
        this.stock = stock;
    }

    public String getKodeKue() {
        return kodeKue;
    }

    public void setKodeKue(String kodeKue) {
        this.kodeKue = kodeKue;
    }

    public String getJenisKue() {
        return jenisKue;
    }

    public void setJenisKue(String jenisKue) {
        this.jenisKue = jenisKue;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeKue != null ? kodeKue.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kue)) {
            return false;
        }
        Kue other = (Kue) object;
        if ((this.kodeKue == null && other.kodeKue != null) || (this.kodeKue != null && !this.kodeKue.equals(other.kodeKue))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.connectingdb.Kue[ kodeKue=" + kodeKue + " ]";
    }
    
}

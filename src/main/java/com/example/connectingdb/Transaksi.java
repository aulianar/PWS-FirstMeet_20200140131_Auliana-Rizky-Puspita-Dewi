/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.connectingdb;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USER DJOGJA
 */
@Entity
@Table(name = "transaksi")
@NamedQueries({
    @NamedQuery(name = "Transaksi.findAll", query = "SELECT t FROM Transaksi t"),
    @NamedQuery(name = "Transaksi.findByKodeTransaksi", query = "SELECT t FROM Transaksi t WHERE t.kodeTransaksi = :kodeTransaksi"),
    @NamedQuery(name = "Transaksi.findByTanggalTransaksi", query = "SELECT t FROM Transaksi t WHERE t.tanggalTransaksi = :tanggalTransaksi"),
    @NamedQuery(name = "Transaksi.findByQTYPembelian", query = "SELECT t FROM Transaksi t WHERE t.qTYPembelian = :qTYPembelian"),
    @NamedQuery(name = "Transaksi.findByTotalHarga", query = "SELECT t FROM Transaksi t WHERE t.totalHarga = :totalHarga")})
public class Transaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Kode_Transaksi")
    private String kodeTransaksi;
    @Basic(optional = false)
    @Column(name = "Tanggal_Transaksi")
    @Temporal(TemporalType.DATE)
    private Date tanggalTransaksi;
    @Basic(optional = false)
    @Column(name = "QTY_Pembelian")
    private String qTYPembelian;
    @Basic(optional = false)
    @Column(name = "Total_Harga")
    private int totalHarga;
    @JoinColumn(name = "Id_Pembeli", referencedColumnName = "Id_Pembeli")
    @OneToOne(optional = false)
    private Pembeli idPembeli;
    @JoinColumn(name = "Id_Karyawan", referencedColumnName = "Id_Karyawan")
    @OneToOne(optional = false)
    private Karyawan idKaryawan;
    @JoinColumn(name = "Kode_Kue", referencedColumnName = "Kode_Kue")
    @OneToOne(optional = false)
    private Kue kodeKue;

    public Transaksi() {
    }

    public Transaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public Transaksi(String kodeTransaksi, Date tanggalTransaksi, String qTYPembelian, int totalHarga) {
        this.kodeTransaksi = kodeTransaksi;
        this.tanggalTransaksi = tanggalTransaksi;
        this.qTYPembelian = qTYPembelian;
        this.totalHarga = totalHarga;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public Date getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(Date tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public String getQTYPembelian() {
        return qTYPembelian;
    }

    public void setQTYPembelian(String qTYPembelian) {
        this.qTYPembelian = qTYPembelian;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public Pembeli getIdPembeli() {
        return idPembeli;
    }

    public void setIdPembeli(Pembeli idPembeli) {
        this.idPembeli = idPembeli;
    }

    public Karyawan getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(Karyawan idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public Kue getKodeKue() {
        return kodeKue;
    }

    public void setKodeKue(Kue kodeKue) {
        this.kodeKue = kodeKue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kodeTransaksi != null ? kodeTransaksi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaksi)) {
            return false;
        }
        Transaksi other = (Transaksi) object;
        if ((this.kodeTransaksi == null && other.kodeTransaksi != null) || (this.kodeTransaksi != null && !this.kodeTransaksi.equals(other.kodeTransaksi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.connectingdb.Transaksi[ kodeTransaksi=" + kodeTransaksi + " ]";
    }
    
}

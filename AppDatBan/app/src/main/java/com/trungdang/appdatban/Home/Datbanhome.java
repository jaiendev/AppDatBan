package com.trungdang.appdatban.Home;

public class Datbanhome {
    private int Hinhdatban;
    private String Soban;
    private String Mota;
    private String giatien;
    private String theloai;

    public Datbanhome(int hinhdatban, String soban, String mota, String giatien, String theloai) {
        Hinhdatban = hinhdatban;
        Soban = soban;
        Mota = mota;
        this.giatien = giatien;
        this.theloai = theloai;
    }

    public int getHinhdatban() {
        return Hinhdatban;
    }

    public void setHinhdatban(int hinhdatban) {
        Hinhdatban = hinhdatban;
    }

    public String getSoban() {
        return Soban;
    }

    public void setSoban(String soban) {
        Soban = soban;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getGiatien() {
        return giatien;
    }

    public void setGiatien(String giatien) {
        this.giatien = giatien;
    }

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }
}

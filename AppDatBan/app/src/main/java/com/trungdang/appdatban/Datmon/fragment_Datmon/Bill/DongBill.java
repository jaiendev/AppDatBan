package com.trungdang.appdatban.Datmon.fragment_Datmon.Bill;

public class DongBill {
    public int soluong;
    private String idmon;
    private String Tenmon;
    private String Giatien;
    private String Soluong;
    private int tongtien;
    public DongBill(String idmon, String tenmon, String giatien, String soluong,int tongtien) {
        this.idmon = idmon;
        Tenmon = tenmon;
        Giatien = giatien;
        Soluong = soluong;
        this.tongtien=tongtien;
    }

    public String getIdmon() {
        return idmon;
    }

    public void setIdmon(String idmon) {
        this.idmon = idmon;
    }

    public String getTenmon() {
        return Tenmon;
    }

    public void setTenmon(String tenmon) {
        Tenmon = tenmon;
    }

    public String getGiatien() {
        return Giatien;
    }

    public void setGiatien(String giatien) {
        Giatien = giatien;
    }

    public String getSoluong() {
        return Soluong;
    }

    public void setSoluong(String soluong) {
        Soluong = soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }
}

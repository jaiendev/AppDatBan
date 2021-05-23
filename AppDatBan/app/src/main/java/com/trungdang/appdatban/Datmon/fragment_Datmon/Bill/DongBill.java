package com.trungdang.appdatban.Datmon.fragment_Datmon.Bill;

public class DongBill {
    public int soluong;
    private String idmon;
    private String Tenmon;
    private String Giatien;
    private String Soluong;

    public DongBill(String idmon, String tenmon, String giatien, String soluong) {
        this.idmon = idmon;
        Tenmon = tenmon;
        Giatien = giatien;
        Soluong = soluong;
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
}

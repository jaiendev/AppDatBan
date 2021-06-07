package com.trungdang.appdatban.Datmon.fragment_Datmon.Bill;

import java.util.List;

public class BillConstructor {
    String idban,date,IdUser,TongTien,Loaiban;
    List<String> Monchinh;
    List<String> Trangmieng;
    List<String> Giaikhat;

    public BillConstructor(String idban, String date, String idUser, String tongTien, String loaiban, List<String> monchinh, List<String> trangmieng, List<String> giaikhat) {
        this.idban = idban;
        this.date = date;
        IdUser = idUser;
        TongTien = tongTien;
        Loaiban = loaiban;
        Monchinh = monchinh;
        Trangmieng = trangmieng;
        Giaikhat = giaikhat;
    }

    public String getIdban() {
        return idban;
    }

    public void setIdban(String idban) {
        this.idban = idban;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public String getLoaiban() {
        return Loaiban;
    }

    public void setLoaiban(String loaiban) {
        Loaiban = loaiban;
    }

    public List<String> getMonchinh() {
        return Monchinh;
    }

    public void setMonchinh(List<String> monchinh) {
        Monchinh = monchinh;
    }

    public List<String> getTrangmieng() {
        return Trangmieng;
    }

    public void setTrangmieng(List<String> trangmieng) {
        Trangmieng = trangmieng;
    }

    public List<String> getGiaikhat() {
        return Giaikhat;
    }

    public void setGiaikhat(List<String> giaikhat) {
        Giaikhat = giaikhat;
    }
}

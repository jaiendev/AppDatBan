package com.trungdang.appdatban.HomeAdmin.QuanlyKH;

public class Dongquanlykhachhang {
    String Ten,Email;

    public Dongquanlykhachhang(String ten, String email) {
        Ten = ten;
        Email = email;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

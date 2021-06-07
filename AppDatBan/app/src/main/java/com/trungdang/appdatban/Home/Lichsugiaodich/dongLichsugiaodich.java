package com.trungdang.appdatban.Home.Lichsugiaodich;

public class dongLichsugiaodich {
    String Madonhang,Ngaydat,Tongtien;

    public dongLichsugiaodich(String madonhang, String ngaydat, String tongtien) {
        Madonhang = madonhang;
        Ngaydat = ngaydat;
        Tongtien = tongtien;
    }

    public String getMadonhang() {
        return Madonhang;
    }

    public void setMadonhang(String madonhang) {
        Madonhang = madonhang;
    }

    public String getNgaydat() {
        return Ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        Ngaydat = ngaydat;
    }

    public String getTongtien() {
        return Tongtien;
    }

    public void setTongtien(String tongtien) {
        Tongtien = tongtien;
    }
}

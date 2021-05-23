package com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh;

public class DongMonChinh {
    public String bill;
    public int soluong;
    private  String idLoai;
    public String idMonchinh;
    private String Hinhmonchinh;
    private String Tenmonchinh;
    private String Soluongdat;
    private String Giamon;
    private String Loaimon;

    public DongMonChinh(String idLoai, String idMonchinh, String hinhmonchinh, String tenmonchinh, String soluongdat, String giamon, String loaimon) {
        this.idLoai = idLoai;
        this.idMonchinh = idMonchinh;
        Hinhmonchinh = hinhmonchinh;
        Tenmonchinh = tenmonchinh;
        Soluongdat = soluongdat;
        Giamon = giamon;
        Loaimon = loaimon;
    }

    public String getIdLoai() {
        return idLoai;
    }

    public void setIdLoai(String idLoai) {
        this.idLoai = idLoai;
    }

    public String getIdMonchinh() {
        return idMonchinh;
    }

    public void setIdMonchinh(String idMonchinh) {
        this.idMonchinh = idMonchinh;
    }

    public String getHinhmonchinh() {
        return Hinhmonchinh;
    }

    public void setHinhmonchinh(String hinhmonchinh) {
        Hinhmonchinh = hinhmonchinh;
    }

    public String getTenmonchinh() {
        return Tenmonchinh;
    }

    public void setTenmonchinh(String tenmonchinh) {
        Tenmonchinh = tenmonchinh;
    }

    public String getSoluongdat() {
        return Soluongdat;
    }

    public void setSoluongdat(String soluongdat) {
        Soluongdat = soluongdat;
    }

    public String getGiamon() {
        return Giamon;
    }

    public void setGiamon(String giamon) {
        Giamon = giamon;
    }

    public String getLoaimon() {
        return Loaimon;
    }

    public void setLoaimon(String loaimon) {
        Loaimon = loaimon;
    }
}

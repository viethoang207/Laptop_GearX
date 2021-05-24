package com.example.laptop_gearx.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonDatHang {

@SerializedName("maDonDatHang")
@Expose
private String maDonDatHang;
@SerializedName("tenTk")
@Expose
private String tenTk;
@SerializedName("hoTen")
@Expose
private String hoTen;
@SerializedName("ngayDat")
@Expose
private String ngayDat;
@SerializedName("ngayHenLay")
@Expose
private String ngayHenLay;
@SerializedName("tongTien")
@Expose
private Integer tongTien;
@SerializedName("trangThai")
@Expose
private String trangThai;

public String getMaDonDatHang() {
return maDonDatHang;
}

public void setMaDonDatHang(String maDonDatHang) {
this.maDonDatHang = maDonDatHang;
}

public String getTenTk() {
return tenTk;
}

public void setTenTk(String tenTk) {
this.tenTk = tenTk;
}

public String getHoTen() {
return hoTen;
}

public void setHoTen(String hoTen) {
this.hoTen = hoTen;
}

public String getNgayDat() {
return ngayDat;
}

public void setNgayDat(String ngayDat) {
this.ngayDat = ngayDat;
}

public String getNgayHenLay() {
return ngayHenLay;
}

public void setNgayHenLay(String ngayHenLay) {
this.ngayHenLay = ngayHenLay;
}

public Integer getTongTien() {
return tongTien;
}

public void setTongTien(Integer tongTien) {
this.tongTien = tongTien;
}

public String getTrangThai() {
return trangThai;
}

public void setTrangThai(String trangThai) {
this.trangThai = trangThai;
}

}
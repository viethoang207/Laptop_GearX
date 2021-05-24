package com.example.laptop_gearx.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChiTietDonDatHang {

@SerializedName("maDonDatHang")
@Expose
private String maDonDatHang;
@SerializedName("maLaptop")
@Expose
private Integer maLaptop;
@SerializedName("tenLaptop")
@Expose
private String tenLaptop;
@SerializedName("linkAnh")
@Expose
private String linkAnh;
@SerializedName("soLuong")
@Expose
private Integer soLuong;
@SerializedName("giaBan")
@Expose
private Integer giaBan;
@SerializedName("thanhtien")
@Expose
private Integer thanhtien;

public String getMaDonDatHang() {
return maDonDatHang;
}

public void setMaDonDatHang(String maDonDatHang) {
this.maDonDatHang = maDonDatHang;
}

public Integer getMaLaptop() {
return maLaptop;
}

public void setMaLaptop(Integer maLaptop) {
this.maLaptop = maLaptop;
}

public String getTenLaptop() {
return tenLaptop;
}

public void setTenLaptop(String tenLaptop) {
this.tenLaptop = tenLaptop;
}

public String getLinkAnh() {
return linkAnh;
}

public void setLinkAnh(String linkAnh) {
this.linkAnh = linkAnh;
}

public Integer getSoLuong() {
return soLuong;
}

public void setSoLuong(Integer soLuong) {
this.soLuong = soLuong;
}

public Integer getGiaBan() {
return giaBan;
}

public void setGiaBan(Integer giaBan) {
this.giaBan = giaBan;
}

public Integer getThanhtien() {
return thanhtien;
}

public void setThanhtien(Integer thanhtien) {
this.thanhtien = thanhtien;
}

}
package com.example.laptop_gearx.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HoaDonBan {

@SerializedName("maHoaDonBan")
@Expose
private String maHoaDonBan;
@SerializedName("hoTenNguoiBan")
@Expose
private String hoTenNguoiBan;
@SerializedName("hoTenNguoiMua")
@Expose
private String hoTenNguoiMua;
@SerializedName("ngayBan")
@Expose
private String ngayBan;
@SerializedName("tongTien")
@Expose
private Integer tongTien;

public String getMaHoaDonBan() {
return maHoaDonBan;
}

public void setMaHoaDonBan(String maHoaDonBan) {
this.maHoaDonBan = maHoaDonBan;
}

public String getHoTenNguoiBan() {
return hoTenNguoiBan;
}

public void setHoTenNguoiBan(String hoTenNguoiBan) {
this.hoTenNguoiBan = hoTenNguoiBan;
}

public String getHoTenNguoiMua() {
return hoTenNguoiMua;
}

public void setHoTenNguoiMua(String hoTenNguoiMua) {
this.hoTenNguoiMua = hoTenNguoiMua;
}

public String getNgayBan() {
return ngayBan;
}

public void setNgayBan(String ngayBan) {
this.ngayBan = ngayBan;
}

public Integer getTongTien() {
return tongTien;
}

public void setTongTien(Integer tongTien) {
this.tongTien = tongTien;
}

}
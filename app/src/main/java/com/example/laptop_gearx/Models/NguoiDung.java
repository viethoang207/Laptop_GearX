package com.example.laptop_gearx.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NguoiDung {

@SerializedName("tenTk")
@Expose
private String tenTk;
@SerializedName("hoTen")
@Expose
private String hoTen;
@SerializedName("gioiTinh")
@Expose
private String gioiTinh;
@SerializedName("email")
@Expose
private String email;
@SerializedName("chucVu")
@Expose
private String chucVu;
@SerializedName("trangThai")
@Expose
private String trangThai;

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

public String getGioiTinh() {
return gioiTinh;
}

public void setGioiTinh(String gioiTinh) {
this.gioiTinh = gioiTinh;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getChucVu() {
return chucVu;
}

public void setChucVu(String chucVu) {
this.chucVu = chucVu;
}

public String getTrangThai() {
return trangThai;
}

public void setTrangThai(String trangThai) {
this.trangThai = trangThai;
}

}
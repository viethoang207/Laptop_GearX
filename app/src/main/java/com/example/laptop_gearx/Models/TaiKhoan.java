package com.example.laptop_gearx.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaiKhoan {

@SerializedName("tenTk")
@Expose
private String tenTk;
@SerializedName("matKhau")
@Expose
private String matKhau;
@SerializedName("loaiTk")
@Expose
private String loaiTk;

public String getTenTk() {
return tenTk;
}

public void setTenTk(String tenTk) {
this.tenTk = tenTk;
}

public String getMatKhau() {
return matKhau;
}

public void setMatKhau(String matKhau) {
this.matKhau = matKhau;
}

public String getLoaiTk() {
return loaiTk;
}

public void setLoaiTk(String loaiTk) {
this.loaiTk = loaiTk;
}

}
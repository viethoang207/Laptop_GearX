package com.example.laptop_gearx.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Laptop {

@SerializedName("maLaptop")
@Expose
private Integer maLaptop;
@SerializedName("tenLaptop")
@Expose
private String tenLaptop;
@SerializedName("thuongHieu")
@Expose
private String thuongHieu;
@SerializedName("cauhinh")
@Expose
private String cauhinh;
@SerializedName("mauSac")
@Expose
private String mauSac;
@SerializedName("giaNhap")
@Expose
private Integer giaNhap;
@SerializedName("giaBan")
@Expose
private Integer giaBan;
@SerializedName("linkAnh")
@Expose
private String linkAnh;
@SerializedName("soLuong")
@Expose
private Integer soLuong;


    public Laptop() {
    }

    public Laptop(Integer maLaptop, String tenLaptop, String thuongHieu, String cauhinh, String mauSac, Integer giaNhap, Integer giaBan, String linkAnh, Integer soLuong) {
        this.maLaptop = maLaptop;
        this.tenLaptop = tenLaptop;
        this.thuongHieu = thuongHieu;
        this.cauhinh = cauhinh;
        this.mauSac = mauSac;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.linkAnh = linkAnh;
        this.soLuong = soLuong;

    }

    @SerializedName("chiTietHdns")
@Expose


private List<Object> chiTietHdns = null;

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

public String getThuongHieu() {
return thuongHieu;
}

public void setThuongHieu(String thuongHieu) {
this.thuongHieu = thuongHieu;
}

public String getCauhinh() {
return cauhinh;
}

public void setCauhinh(String cauhinh) {
this.cauhinh = cauhinh;
}

public String getMauSac() {
return mauSac;
}

public void setMauSac(String mauSac) {
this.mauSac = mauSac;
}

public Integer getGiaNhap() {
return giaNhap;
}

public void setGiaNhap(Integer giaNhap) {
this.giaNhap = giaNhap;
}

public Integer getGiaBan() {
return giaBan;
}

public void setGiaBan(Integer giaBan) {
this.giaBan = giaBan;
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

    @Override
    public String toString() {
        return "Laptop{" +
                "maLaptop=" + maLaptop +
                ", tenLaptop='" + tenLaptop + '\'' +
                ", thuongHieu='" + thuongHieu + '\'' +
                ", cauhinh='" + cauhinh + '\'' +
                ", mauSac='" + mauSac + '\'' +
                ", giaNhap=" + giaNhap +
                ", giaBan=" + giaBan +
                ", linkAnh='" + linkAnh + '\'' +
                ", soLuong=" + soLuong +
                ", chiTietHdns=" + chiTietHdns +
                '}';
    }
}
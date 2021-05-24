package com.example.laptop_gearx.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NhomThuongHieu {

@SerializedName("thuongHieu")
@Expose
private String thuongHieu;
@SerializedName("quocGia")
@Expose
private String quocGia;

public String getThuongHieu() {
return thuongHieu;
}

public void setThuongHieu(String thuongHieu) {
this.thuongHieu = thuongHieu;
}

public String getQuocGia() {
return quocGia;
}

public void setQuocGia(String quocGia) {
this.quocGia = quocGia;
}

}
package com.example.laptop_gearx.Adapter;

import com.example.laptop_gearx.Models.Laptop;

import java.util.List;

public class Obj_NhomThuongHieu {
    private String nameNhomThuongHieu;
    private List<Laptop> laptops;

    public Obj_NhomThuongHieu() {
    }

    public Obj_NhomThuongHieu(String nameNhomThuongHieu, List<Laptop> laptops) {
        this.nameNhomThuongHieu = nameNhomThuongHieu;
        this.laptops = laptops;
    }

    public String getNameNhomThuongHieu() {
        return nameNhomThuongHieu;
    }

    public void setNameNhomThuongHieu(String nameNhomThuongHieu) {
        this.nameNhomThuongHieu = nameNhomThuongHieu;
    }

    public List<Laptop> getLaptops() {
        return laptops;
    }

    public void setLaptops(List<Laptop> laptops) {
        this.laptops = laptops;
    }
}

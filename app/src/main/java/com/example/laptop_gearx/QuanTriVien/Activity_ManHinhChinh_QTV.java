package com.example.laptop_gearx.QuanTriVien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.Activity_QuanLyDonDatHang;
import com.example.laptop_gearx.QuanTriVien.QuanLyLaptop.Activity_QuanLyLaptop;
import com.example.laptop_gearx.R;

public class Activity_ManHinhChinh_QTV extends AppCompatActivity {
    ImageView imgSanPham,imgThoat,imgDonDatHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__man_hinh_chinh__q_t_v);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {
        imgSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_ManHinhChinh_QTV.this, Activity_QuanLyLaptop.class));
            }
        });
        imgThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_ManHinhChinh_QTV.this, Activity_DangNhap.class));
            }
        });

        imgDonDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_ManHinhChinh_QTV.this, Activity_QuanLyDonDatHang.class));
            }
        });
    }

    private void Init() {
        imgSanPham = findViewById(R.id.QTV_imgLaptop);
        imgThoat = findViewById(R.id.QTV_imgThoat);
        imgDonDatHang = findViewById(R.id.QTV_imgDonHang);
    }
}
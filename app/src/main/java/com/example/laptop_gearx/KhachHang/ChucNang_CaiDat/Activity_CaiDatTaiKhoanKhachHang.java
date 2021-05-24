package com.example.laptop_gearx.KhachHang.ChucNang_CaiDat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.KhachHang.ChucNang_XemTinhTrangDonHang.Activity_KhachHang_XemTinhTrangDonHang;
import com.example.laptop_gearx.R;

public class Activity_CaiDatTaiKhoanKhachHang extends AppCompatActivity {

    Button btnThongTin,btnDonHang,btnDoiMK,btnDangXuat;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cai_dat_tai_khoan_khach_hang);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();

        Events();
    }

    private void Events() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Activity_CaiDatTaiKhoanKhachHang.this, Activity_DangNhap.class));
            }
        });

        btnDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_CaiDatTaiKhoanKhachHang.this, Activity_KhachHang_XemTinhTrangDonHang.class));
            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.CDTaiKHoan_ToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cài đặt tài khoản");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                //startActivity(new Intent(Activity_DatHangTuGioHang.this, Activity_ManHinhChinh_KhachHang.class));
            }
        });



        btnThongTin = findViewById(R.id.CDTaiKHoan_ThongTin);
        btnDonHang = findViewById(R.id.CDTaiKHoan_DonDatHang);
        btnDoiMK = findViewById(R.id.CDTaiKHoan_DoiMK);
        btnDangXuat = findViewById(R.id.CDTaiKHoan_DangXuat);
    }
}
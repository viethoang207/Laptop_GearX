package com.example.laptop_gearx.DangNhap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_gearx.DangKiTaiKhoan.Activity_DangKiTaiKhoan;
import com.example.laptop_gearx.KhachHang.Activity_ManHinhChinh_KhachHang;
import com.example.laptop_gearx.MainActivity;
import com.example.laptop_gearx.Models.TaiKhoan;
import com.example.laptop_gearx.QuanTriVien.Activity_ManHinhChinh_QTV;
import com.example.laptop_gearx.R;

import java.util.ArrayList;

public class Activity_DangNhap extends AppCompatActivity {
    TextView tvTaoTK;
    Button btnDangNhap;
    EditText edtID,edtPW;

    public static TaiKhoan tk_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dang_nhap);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Init();
        Events();
    }

    private void Events() {
        tvTaoTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_DangNhap.this, Activity_DangKiTaiKhoan.class));
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<TaiKhoan> listTK = MainActivity.list_tk_fromDB;

                if(edtID.getText().length()==0){
                    edtID.setError("Thông tin còn trống");
                    edtID.requestFocus();
                    return;
                }
                if(edtPW.getText().length()==0){
                    edtPW.setError("Thông tin còn trống");
                    edtPW.requestFocus();
                    return;
                }

               if(CheckDN(edtID.getText().toString(),edtPW.getText().toString(),listTK)==true){
                   Toast.makeText(Activity_DangNhap.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                   if(tk_login.getLoaiTk().equals("qtv")){
                       startActivity(new Intent(Activity_DangNhap.this, Activity_ManHinhChinh_QTV.class));

                   }else{
                       startActivity(new Intent(Activity_DangNhap.this, Activity_ManHinhChinh_KhachHang.class));
                   }
               }else{
                   Toast.makeText(Activity_DangNhap.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private boolean CheckDN(String id,String pw,ArrayList<TaiKhoan> list){
        for(TaiKhoan tk : list){
            if(tk.getTenTk().toLowerCase().equals(id.toLowerCase())){
                if(tk.getMatKhau().equals(pw)){
                    tk_login = tk;
                    return true;
                }
            }
        }
        return false;
    }

    private void Init() {
        tvTaoTK = findViewById(R.id.DangNhap_tvTaoTK);
        edtID = findViewById(R.id.DangNhap_edtID);
        edtPW = findViewById(R.id.DangNhap_edtPW);

        btnDangNhap = findViewById(R.id.DangNhap_btnDangNhap);
    }
}
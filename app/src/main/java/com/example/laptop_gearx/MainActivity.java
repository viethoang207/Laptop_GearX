package com.example.laptop_gearx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.KhachHang.Activity_ManHinhChinh_KhachHang;
import com.example.laptop_gearx.Models.TaiKhoan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<TaiKhoan> list_tk_fromDB;
    ProgressBar pgrbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();

    }

    private void Init() {
        list_tk_fromDB = new ArrayList<>();
        pgrbar = findViewById(R.id.Main_ProgressBar);
        getDSTK();

        //startActivity(new Intent(MainActivity.this, Activity_ManHinhChinh_KhachHang.class));
    }

    private void getDSTK(){
        Dataservice dataservice = APIService.getService();
        Call<List<TaiKhoan>> callBack = dataservice.getTaiKhoan();
        callBack.enqueue(new Callback<List<TaiKhoan>>() {
            @Override
            public void onResponse(Call<List<TaiKhoan>> call, Response<List<TaiKhoan>> response) {
                ArrayList<TaiKhoan> tk_list = new ArrayList<>();
                tk_list = (ArrayList<TaiKhoan>) response.body();
                if(tk_list.size()==0){
                    Toast.makeText(MainActivity.this,"Lấy dữ liệu tài khoản thất bại",Toast.LENGTH_SHORT).show();
                    pgrbar.setVisibility(View.GONE);
                }else{
                    for(TaiKhoan t : tk_list){
                        list_tk_fromDB.add(t);
                    }
                    startActivity(new Intent(MainActivity.this,Activity_DangNhap.class));
                }
            }

            @Override
            public void onFailure(Call<List<TaiKhoan>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Lấy dữ liệu tài khoản thất bại",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.example.laptop_gearx.KhachHang.ChucNang_GioHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.KhachHang.Activity_ManHinhChinh_KhachHang;
import com.example.laptop_gearx.KhachHang.ChucNang_DatHangTuGioHang.Activity_DatHangTuGioHang;
import com.example.laptop_gearx.MainActivity;
import com.example.laptop_gearx.Models.ChiTietGioHang;
import com.example.laptop_gearx.Models.Laptop;
import com.example.laptop_gearx.QuanTriVien.Activity_ManHinhChinh_QTV;
import com.example.laptop_gearx.QuanTriVien.QuanLyLaptop.Activity_QuanLyLaptop;
import com.example.laptop_gearx.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_ChiTietGioHang extends AppCompatActivity {

    Toolbar toolbar;

    SwipeMenuListView lv;
    
    public static TextView tvTongTien;
    
    Button btnDatHang;
    
    ProgressDialog TempDialog;
    int i=0;

    int tongTienGioHang =0;
    
    public static ArrayList<ChiTietGioHang> listGioHang;
    public static Adapter_ChiTietGioHang adapter_chiTietGioHang;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__chi_tiet_gio_hang);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();

        Events();
    }

    private void Events() {

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listGioHang.size()==0){
                    Toast.makeText(Activity_ChiTietGioHang.this,"Giỏ hàng đang trống",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(Activity_ChiTietGioHang.this, Activity_DatHangTuGioHang.class));
            }
        });

        //events swipe lisview
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        XoaChiTietGioHang(position);
                        break;

                }
                // false : close the menu; true : not close the menu
                return false;
            }

            private void XoaChiTietGioHang(int position) {
                //Toast.makeText(Activity_ChiTietGioHang.this,listGioHang.get(position).getTenLaptop(),Toast.LENGTH_SHORT).show();
                AlertDialog.Builder al = new AlertDialog.Builder(Activity_ChiTietGioHang.this);
                al.setMessage("Xóa sản phẩm này khỏi giỏ hàng ?");
                al.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TempDialog.setTitle("Đang xử lý");
                        TempDialog.show();
                        //xóa sp khỏi giỏ hàng

                        //lấy về tên tài khoản và mã laptop cần xóa trong giỏ
                        String tenTK = listGioHang.get(position).getTenTk();
                        int maLaptop = listGioHang.get(position).getMaLaptop();

                        //thực hiện xóa
                        Dataservice dataservice = APIService.getService();
                        dataservice.deleteCTGH(tenTK,maLaptop).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){

                                    dialog.cancel();
                                    TempDialog.dismiss();
                                    TinhTongTien();
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(Activity_ChiTietGioHang.this,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                                    TempDialog.dismiss();
                                    dialog.cancel();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(Activity_ChiTietGioHang.this,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                                TempDialog.dismiss();
                                dialog.cancel();
                            }
                        });

                    }
                });

                al.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                al.create();
                al.show();
            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.XemGioHang_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                //startActivity(new Intent(Activity_ChiTietGioHang.this,Activity_ManHinhChinh_KhachHang.class));
            }
        });

        TempDialog = new ProgressDialog(Activity_ChiTietGioHang.this);
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setTitle("Đang tải dữ liệu giỏ hàng");
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        
        tvTongTien = findViewById(R.id.XemGioHang_tvTongTien);
        
        lv = findViewById(R.id.XemGioHang_lv);

        
        btnDatHang = findViewById(R.id.XemGioHang_btnDatHang);
        
        listGioHang = new ArrayList<>();


        //Toast.makeText(Activity_ChiTietGioHang.this,""+Activity_DangNhap.tk_login.getTenTk(),Toast.LENGTH_SHORT).show();



        //khởi tạo swipe listview
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.RED));

                //set item text color
                deleteItem.setTitleColor(Color.WHITE);
                deleteItem.setTitleSize(18);
                // set item width
                deleteItem.setWidth(230);
                // set a icon
                deleteItem.setTitle("Xóa");


                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        lv.setMenuCreator(creator);
        adapter_chiTietGioHang = new Adapter_ChiTietGioHang(Activity_ChiTietGioHang.this,listGioHang);
        lv.setAdapter(adapter_chiTietGioHang);

        //
        GetDataGioHang();
    }

    public void GetDataGioHang() {
        TempDialog.show();
        listGioHang.clear();
        Dataservice dataservice = APIService.getService();
        dataservice.getGioHangByTaiKhoan(Activity_DangNhap.tk_login.getTenTk()).enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                if(response.isSuccessful()){
                    ArrayList<ChiTietGioHang> ctgh_list = new ArrayList<>();
                    ctgh_list = (ArrayList<ChiTietGioHang>) response.body();
                    if(ctgh_list.size()==0){
                        TempDialog.dismiss();
                        Toast.makeText(Activity_ChiTietGioHang.this,"Giỏ hàng đang trống",Toast.LENGTH_SHORT).show();
                    }else{
                        for(ChiTietGioHang ct : ctgh_list){
                            tongTienGioHang+=ct.getThanhTien();
                            listGioHang.add(ct);
                        }
                        adapter_chiTietGioHang.notifyDataSetChanged();
                        DecimalFormat formatter = new DecimalFormat("###,###,###");
                        String tt = "₫ " +formatter.format(tongTienGioHang);
                        tvTongTien.setText(tt);
                        TempDialog.dismiss();
                    }
                }else{
                    Toast.makeText(Activity_ChiTietGioHang.this,"Lấy dữ liệu giỏ hàng thất bại 2",Toast.LENGTH_SHORT).show();
                    TempDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                Toast.makeText(Activity_ChiTietGioHang.this,"Lấy dữ liệu giỏ hàng thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });
    }

    public static void TinhTongTien(){

        int tt = 0;
        for(ChiTietGioHang ct : listGioHang){
            tt +=ct.getThanhTien();
        }

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String s = "₫ " +formatter.format(tt);
        tvTongTien.setText(s);
    }





}
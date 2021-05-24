package com.example.laptop_gearx.KhachHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.Adapter.Adapter_ObjNhomThuongHieu;
import com.example.laptop_gearx.Adapter.Obj_NhomThuongHieu;
import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.KhachHang.ChucNang_CaiDat.Activity_CaiDatTaiKhoanKhachHang;
import com.example.laptop_gearx.KhachHang.ChucNang_GioHang.Activity_ChiTietGioHang;
import com.example.laptop_gearx.Models.ChiTietGioHang;
import com.example.laptop_gearx.Models.Laptop;
import com.example.laptop_gearx.Models.NhomThuongHieu;
import com.example.laptop_gearx.OnLaptopClickListener;
import com.example.laptop_gearx.QuanTriVien.Activity_ManHinhChinh_QTV;
import com.example.laptop_gearx.QuanTriVien.QuanLyLaptop.Activity_QuanLyLaptop;
import com.example.laptop_gearx.QuanTriVien.QuanLyLaptop.Activity_ThemLaptop;
import com.example.laptop_gearx.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_ManHinhChinh_KhachHang extends AppCompatActivity {
    private RecyclerView rcvNhomThuongHieu;
    private Adapter_ObjNhomThuongHieu categoryAdapter;

    ArrayList<String> listNTH;
    List<Obj_NhomThuongHieu> listData;
    ProgressDialog TempDialog;
    int i=0;

    public static ArrayList<ChiTietGioHang> listCTGH;

    List<Obj_NhomThuongHieu> list1 = new ArrayList<>();
    List<Laptop> listLaptop = new ArrayList<>();

    public static List<Laptop> listLaptopFromDB;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__man_hinh_chinh_khachhang);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Init();
    }

    private void Init() {

        toolbar = findViewById(R.id.MHC_KhachHang_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Sản phẩm");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        TempDialog = new ProgressDialog(Activity_ManHinhChinh_KhachHang.this);
        TempDialog.setTitle("Đang tải dữ liệu");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        listNTH = new ArrayList<>();
        rcvNhomThuongHieu = findViewById(R.id.rcv_nhomThuongHieu);
        categoryAdapter = new Adapter_ObjNhomThuongHieu(this);

        listCTGH = new ArrayList<>();
        listLaptopFromDB = new ArrayList<>();

        listData = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcvNhomThuongHieu.setLayoutManager(linearLayoutManager);

//        TempDialog.show();
//
//        GetNhomThuongHieu();

        GetAllLaptopFromDB();







    }

    private void getListData() {


        listLaptop.add(new Laptop(1,"DELL G5 2020","DELL"," GEFORCE GTX 1660TI 6GB INTEL CORE I7 10750H 512GB 8GB 15.6 FHD IPS 120HZ 4-ZONES RGB WIN 10","Xám",22000000,27990000,"https://xgear.vn/wp-content/uploads/2021/02/DELL-G5-5500-70225485-768x768.jpg",20));
        listLaptop.add(new Laptop(2,"DELL G5 2020","DELL"," GEFORCE GTX 1660TI 6GB INTEL CORE I7 10750H 512GB 8GB 15.6 FHD IPS 120HZ 4-ZONES RGB WIN 10","Xám",22000000,27990000,"https://xgear.vn/wp-content/uploads/2021/02/DELL-G5-5500-70225485-768x768.jpg",20));
        listLaptop.add(new Laptop(3,"DELL G5 2020","DELL"," GEFORCE GTX 1660TI 6GB INTEL CORE I7 10750H 512GB 8GB 15.6 FHD IPS 120HZ 4-ZONES RGB WIN 10","Xám",22000000,27990000,"https://xgear.vn/wp-content/uploads/2021/02/DELL-G5-5500-70225485-768x768.jpg",20));
        listLaptop.add(new Laptop(3,"DELL G5 2020","DELL"," GEFORCE GTX 1660TI 6GB INTEL CORE I7 10750H 512GB 8GB 15.6 FHD IPS 120HZ 4-ZONES RGB WIN 10","Xám",22000000,27990000,"https://xgear.vn/wp-content/uploads/2021/02/DELL-G5-5500-70225485-768x768.jpg",20));
        listLaptop.add(new Laptop(3,"DELL G5 2020","DELL"," GEFORCE GTX 1660TI 6GB INTEL CORE I7 10750H 512GB 8GB 15.6 FHD IPS 120HZ 4-ZONES RGB WIN 10","Xám",22000000,27990000,"https://xgear.vn/wp-content/uploads/2021/02/DELL-G5-5500-70225485-768x768.jpg",20));

        list1.add(new Obj_NhomThuongHieu("DELL",listLaptop));
        list1.add(new Obj_NhomThuongHieu("Asus",listLaptop));
        list1.add(new Obj_NhomThuongHieu("Lenovo",listLaptop));

//        for(String tenTH : listNTH){
//            String thuongHieu = tenTH;
//            List<Laptop> list = GetLaptopTheoNTH(thuongHieu);
//            list1.add(new Obj_NhomThuongHieu(tenTH,list));
//        }
       TempDialog.dismiss();



    }

    private void GetAllLaptopFromDB(){
        TempDialog.show();
        GetDataGioHang();
        listLaptopFromDB.clear();
        Dataservice dataservice = APIService.getService();
        Call<List<Laptop>> callBack = dataservice.getAllLaptop();
        callBack.enqueue(new Callback<List<Laptop>>() {
            @Override
            public void onResponse(Call<List<Laptop>> call, Response<List<Laptop>> response) {
                ArrayList<Laptop> lt_list = new ArrayList<>();
                lt_list = (ArrayList<Laptop>) response.body();
                if(lt_list.size()==0){
                    Toast.makeText(Activity_ManHinhChinh_KhachHang.this,"Lấy dữ liệu laptop thất bại",Toast.LENGTH_SHORT).show();
                    TempDialog.dismiss();
                }else{
                    for(Laptop lt : lt_list){
                        listLaptopFromDB.add(lt);
                    }

                    //Toast.makeText(Activity_ManHinhChinh_KhachHang.this,listLaptopFromDB.size()+"",Toast.LENGTH_SHORT).show();
                    GetNhomThuongHieu();
                }
            }

            @Override
            public void onFailure(Call<List<Laptop>> call, Throwable t) {

            }
        });
    }


    private List<Laptop> GetLaptopTheoNTH(String nth){
        ArrayList<Laptop> listLT = new ArrayList<>();
        Dataservice dataservice = APIService.getService();
        dataservice.getLaptopByNhomThuongHieu(nth).enqueue(new Callback<List<Laptop>>() {
            @Override
            public void onResponse(Call<List<Laptop>> call, Response<List<Laptop>> response) {
                if(response.isSuccessful()){
                    ArrayList<Laptop> lt_list = new ArrayList<>();
                    lt_list = (ArrayList<Laptop>) response.body();
                    for(Laptop l : lt_list){
                        listLT.add(l);
                        Log.d("Laptop",l.toString());
                    }

                }
            }


            @Override
            public void onFailure(Call<List<Laptop>> call, Throwable t) {

            }
        });

        return listLT;

    }

    private Obj_NhomThuongHieu getOBJNTH(String tenNTH){
        Obj_NhomThuongHieu obj = new Obj_NhomThuongHieu();
        ArrayList<Laptop> list = new ArrayList<>();

        for(Laptop l : listLaptopFromDB){
            if(l.getThuongHieu().toLowerCase().equals(tenNTH.toLowerCase())){
                list.add(l);
            }
        }

        obj.setNameNhomThuongHieu(tenNTH);
        obj.setLaptops(list);

        return obj;
    }

    private void GetNhomThuongHieu(){

        Dataservice dataservice = APIService.getService();
        Call<List<NhomThuongHieu>> callBack = dataservice.getAllNhomThuongHieu();
        callBack.enqueue(new Callback<List<NhomThuongHieu>>() {
            @Override
            public void onResponse(Call<List<NhomThuongHieu>> call, Response<List<NhomThuongHieu>> response) {
                ArrayList<NhomThuongHieu> nth_list = new ArrayList<>();
                nth_list = (ArrayList<NhomThuongHieu>) response.body();

                for(NhomThuongHieu n : nth_list) {
                    listNTH.add(n.getThuongHieu());
                    Obj_NhomThuongHieu obj = getOBJNTH(n.getThuongHieu());
                    listData.add(obj);
                }
                TempDialog.dismiss();

                categoryAdapter.setData(listData);
                rcvNhomThuongHieu.setAdapter(categoryAdapter);

            }
            @Override
            public void onFailure(Call<List<NhomThuongHieu>> call, Throwable t) {
                Toast.makeText(Activity_ManHinhChinh_KhachHang.this,"Lấy dữ liệu NTH thất bại",Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_danhmucsanpham, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_KH_itGioHang:
                startActivity(new Intent(Activity_ManHinhChinh_KhachHang.this, Activity_ChiTietGioHang.class));
                break;
            case  R.id.menu_KH_itCaiDat:
                startActivity(new Intent(Activity_ManHinhChinh_KhachHang.this, Activity_CaiDatTaiKhoanKhachHang.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void GetDataGioHang() {
        TempDialog.show();
        listCTGH.clear();
        Dataservice dataservice = APIService.getService();
        dataservice.getGioHangByTaiKhoan(Activity_DangNhap.tk_login.getTenTk()).enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                if(response.isSuccessful()){
                    ArrayList<ChiTietGioHang> ctgh_list = new ArrayList<>();
                    ctgh_list = (ArrayList<ChiTietGioHang>) response.body();
                    if(ctgh_list.size()==0){

                        //Toast.makeText(Activity_ManHinhChinh_KhachHang.this,"Giỏ",Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(Activity_ManHinhChinh_KhachHang.this, Activity_ManHinhChinh_QTV.class));
                    }else{
                        for(ChiTietGioHang ct : ctgh_list){
                            listCTGH.add(ct);
                        }
                        //Toast.makeText(Activity_ManHinhChinh_KhachHang.this,"Lấy dữ liệu giỏ hàng :"+listCTGH.size(),Toast.LENGTH_SHORT).show();


                    }
                }else{
                    Toast.makeText(Activity_ManHinhChinh_KhachHang.this,"Lấy dữ liệu giỏ hàng thất bại 2",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                Toast.makeText(Activity_ManHinhChinh_KhachHang.this,"Lấy dữ liệu giỏ hàng thất bại",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
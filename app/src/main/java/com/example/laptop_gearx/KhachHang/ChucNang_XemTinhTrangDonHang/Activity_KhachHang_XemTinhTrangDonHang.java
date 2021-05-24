package com.example.laptop_gearx.KhachHang.ChucNang_XemTinhTrangDonHang;

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
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.Adapter.Adapter_KhachHang_TinhTrangDonHang;
import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.KhachHang.ChucNang_GioHang.Activity_ChiTietGioHang;
import com.example.laptop_gearx.KhachHang.ChucNang_GioHang.Adapter_ChiTietGioHang;
import com.example.laptop_gearx.Models.DonDatHang;
import com.example.laptop_gearx.Models.NguoiDung;
import com.example.laptop_gearx.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_KhachHang_XemTinhTrangDonHang extends AppCompatActivity {

    Toolbar toolbar;

    SwipeMenuListView lv;

    ArrayList<DonDatHang> listDDH;
    Adapter_KhachHang_TinhTrangDonHang adapter;

    Dataservice dataservice;

    ProgressDialog TempDialog;
    int i =0;

    String trangthai_click = "";

    public static DonDatHang ddh_chose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__khach_hang__xem_tinh_trang_don_hang);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {


        //events swipe lisview
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        ddh_chose = listDDH.get(position);
                        startActivity(new Intent(Activity_KhachHang_XemTinhTrangDonHang.this,Activity_XemChiTietDonHang.class));
                        break;
                    case 1:

                        if(listDDH.get(position).getTrangThai().equals("huy") || listDDH.get(position).getTrangThai().equals("thanhtoan")){
                            Toast.makeText(Activity_KhachHang_XemTinhTrangDonHang.this,"Đơn đã bị hủy hoặc đã thanh toán",Toast.LENGTH_SHORT).show();
                        }else{
                            //hủy đơn hàng nếu đơn chưa bị hủy
                            AlertDialog.Builder al = new AlertDialog.Builder(Activity_KhachHang_XemTinhTrangDonHang.this);
                            al.setMessage("Bạn có chắc muốn hủy đơn hàng này không ?");
                            al.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    XoaDonHang();
                                }

                                private void XoaDonHang() {
                                    TempDialog.show();
                                    dataservice.deleteDonDatHang(listDDH.get(position).getMaDonDatHang()).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if(response.isSuccessful()){
                                                Toast.makeText(Activity_KhachHang_XemTinhTrangDonHang.this,"Hủy đơn hàng thành công",Toast.LENGTH_SHORT).show();
                                                GetDataDonHang();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Toast.makeText(Activity_KhachHang_XemTinhTrangDonHang.this,"Hủy đơn hàng thất bại",Toast.LENGTH_SHORT).show();
                                            TempDialog.cancel();
                                        }
                                    });
                                }
                            });
                            al.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            al.create();
                            al.show();
                        }

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.XemTTDonHang_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đơn hàng của tôi");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        TempDialog = new ProgressDialog(Activity_KhachHang_XemTinhTrangDonHang.this);
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setTitle("Đang tải");
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        lv = findViewById(R.id.XemTTDonHang_lv);


        listDDH = new ArrayList<>();


        //Toast.makeText(Activity_ChiTietGioHang.this,""+Activity_DangNhap.tk_login.getTenTk(),Toast.LENGTH_SHORT).show();



        //khởi tạo swipe listview
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem ctietItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                ctietItem.setBackground(new ColorDrawable(Color.BLUE));

                //set item text color
                ctietItem.setTitleColor(Color.WHITE);

                ctietItem.setTitleSize(18);
                // set item width
                ctietItem.setWidth(230);
                // set a icon
                ctietItem.setTitle("Chi tiết");


                // add to menu
                menu.addMenuItem(ctietItem);


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
                deleteItem.setTitle("Hủy");


                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        lv.setMenuCreator(creator);
        adapter = new Adapter_KhachHang_TinhTrangDonHang(Activity_KhachHang_XemTinhTrangDonHang.this,listDDH);
        lv.setAdapter(adapter);

        dataservice = APIService.getService();
        //
        GetDataDonHang();
    }

    private void GetDataDonHang() {
        TempDialog.show();
        listDDH.clear();
        dataservice.getDonDatHangByTenTK(Activity_DangNhap.tk_login.getTenTk()).enqueue(new Callback<List<DonDatHang>>() {
            @Override
            public void onResponse(Call<List<DonDatHang>> call, Response<List<DonDatHang>> response) {
                if(response.isSuccessful()){
                    ArrayList<DonDatHang> list_ddh = (ArrayList<DonDatHang>) response.body();
                    for(DonDatHang d : list_ddh){
                        if(d.getTrangThai().equals("dangxacnhan")){
                            listDDH.add(d);
                        }
                    }
                    for(DonDatHang d : list_ddh){
                        if(d.getTrangThai().equals("xacnhan")){
                            listDDH.add(d);
                        }
                    }
                    for(DonDatHang d : list_ddh){
                        if(d.getTrangThai().equals("thanhtoan")){
                            listDDH.add(d);
                        }
                    }
                    for(DonDatHang d : list_ddh){
                        if(d.getTrangThai().equals("huy")){
                            listDDH.add(d);
                        }
                    }
                    //Toast.makeText(Activity_KhachHang_XemTinhTrangDonHang.this,"Lấy dữ liệu :"+listDDH.size(),Toast.LENGTH_SHORT).show();

                    adapter.notifyDataSetChanged();
                    TempDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<DonDatHang>> call, Throwable t) {
                Toast.makeText(Activity_KhachHang_XemTinhTrangDonHang.this,"Lấy dữ liệu thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });
    }
}
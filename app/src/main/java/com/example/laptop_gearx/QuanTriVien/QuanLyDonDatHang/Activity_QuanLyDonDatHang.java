package com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.Adapter.ExpandableListViewDonDatHang_Adapter;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.KhachHang.Activity_ManHinhChinh_KhachHang;
import com.example.laptop_gearx.KhachHang.ChucNang_CaiDat.Activity_CaiDatTaiKhoanKhachHang;
import com.example.laptop_gearx.KhachHang.ChucNang_GioHang.Activity_ChiTietGioHang;
import com.example.laptop_gearx.Models.ChiTietDonDatHang;
import com.example.laptop_gearx.Models.DonDatHang;
import com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.QuanLyDonThanhToan.Activity_QuanLyHoaDonThanhToan;
import com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.QuanLyDonXacNhan.Activity_QuanLyDonXacNhan;
import com.example.laptop_gearx.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_QuanLyDonDatHang extends AppCompatActivity {

    ProgressDialog TempDialog;
    int i =0;

    Toolbar toolbar;

    ExpandableListView lv;


    List<DonDatHang> mListDonDatHang;
    Map<DonDatHang, List<ChiTietDonDatHang>> mListCTDon;
    ExpandableListViewDonDatHang_Adapter adapter;


    int pos_click = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__quan_ly_don_dat_hang);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Activity_QuanLyDonDatHang.this,"Long click",Toast.LENGTH_SHORT).show();
                pos_click = position;
                return false;
            }
        });



    }

    private void Init() {
        toolbar = findViewById(R.id.QuanLyDonDatHang_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đang chờ xác nhận");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mListDonDatHang = new ArrayList<>();
        mListCTDon = new HashMap<>();

        TempDialog = new ProgressDialog(Activity_QuanLyDonDatHang.this);
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setTitle("Đang tải dữ liệu");
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        lv = findViewById(R.id.QuanLyDonDatHang_expLv);
        adapter = new ExpandableListViewDonDatHang_Adapter(mListDonDatHang,mListCTDon);
        lv.setAdapter(adapter);

        registerForContextMenu(lv);

        GetData();

    }

    private void GetData() {
        mListDonDatHang.clear();
        mListCTDon.clear();

        TempDialog.show();

        Dataservice dataservice = APIService.getService();
        dataservice.getAllDonDatHang().enqueue(new Callback<List<DonDatHang>>() {
            @Override
            public void onResponse(Call<List<DonDatHang>> call, Response<List<DonDatHang>> response) {
                if(response.isSuccessful()){
                    ArrayList<DonDatHang> listDonDatHang = (ArrayList<DonDatHang>) response.body();
                    if(listDonDatHang.size()==0){
                        Toast.makeText(Activity_QuanLyDonDatHang.this,"Hiện không có đơn đặt hàng nào",Toast.LENGTH_SHORT).show();
                        TempDialog.dismiss();
                        return;
                    }else{
                        for(DonDatHang d : listDonDatHang){
                            if(d.getTrangThai().equals("dangxacnhan")){
                                mListDonDatHang.add(d);
                                dataservice.getCTDonDatHangByMaDonDatHang(d.getMaDonDatHang()).enqueue(new Callback<List<ChiTietDonDatHang>>() {
                                    @Override
                                    public void onResponse(Call<List<ChiTietDonDatHang>> call, Response<List<ChiTietDonDatHang>> response) {
                                        if(response.isSuccessful()){
                                            List<ChiTietDonDatHang> listCT = (List<ChiTietDonDatHang>) response.body();
                                            mListCTDon.put(d,listCT);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<ChiTietDonDatHang>> call, Throwable t) {
                                        Toast.makeText(Activity_QuanLyDonDatHang.this,"Lấy dữ liệu ct đơn hàng thất bại",Toast.LENGTH_SHORT).show();
                                        TempDialog.dismiss();
                                    }
                                });
                            }

                        }
                        adapter.notifyDataSetChanged();
                        TempDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DonDatHang>> call, Throwable t) {
                Toast.makeText(Activity_QuanLyDonDatHang.this,"Lấy dữ liệu đơn hàng thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu_donhang_choxacnhan_itXacNhanDon:
                XacNhanDonHang();
                break;
            case R.id.context_menu_donhang_choxacnhan_itHuyDon:
                AlertDialog.Builder al = new AlertDialog.Builder(Activity_QuanLyDonDatHang.this);
                al.setMessage("Xác nhận hủy đơn này ?");
                al.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HuyDonHang();
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

                break;

        }


        return super.onContextItemSelected(item);
    }

    private void HuyDonHang() {
        TempDialog.show();
        String maDon = mListDonDatHang.get(pos_click).getMaDonDatHang();
        Dataservice dataservice = APIService.getService();
        dataservice.HuyDonHang(maDon).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Activity_QuanLyDonDatHang.this,"Hủy đơn hàng thành công",Toast.LENGTH_SHORT).show();
                    TempDialog.dismiss();
                    GetData();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Activity_QuanLyDonDatHang.this,"Hủy đơn hàng thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });
    }

    private void XacNhanDonHang() {
        TempDialog.show();
        String maDon = mListDonDatHang.get(pos_click).getMaDonDatHang();
        Dataservice dataservice = APIService.getService();
        dataservice.XacNhanDonHang(maDon).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Activity_QuanLyDonDatHang.this,"Xác nhận đơn hàng thành công",Toast.LENGTH_SHORT).show();
                    GetData();
                }else{
                    Toast.makeText(Activity_QuanLyDonDatHang.this,"Xác nhận đơn hàng thất bại",Toast.LENGTH_SHORT).show();
                    TempDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Activity_QuanLyDonDatHang.this,"Xác nhận đơn hàng thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_dondathang_choxacnhan, menu);
    }


    //option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_donhang, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_menu_donhang_itChoXacNhan:
                break;
            case R.id.option_menu_donhang_itDaXacNhan:
                startActivity(new Intent(Activity_QuanLyDonDatHang.this, Activity_QuanLyDonXacNhan.class));
                break;
            case R.id.option_menu_donhang_itThanhToan:
                startActivity(new Intent(Activity_QuanLyDonDatHang.this, Activity_QuanLyHoaDonThanhToan.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
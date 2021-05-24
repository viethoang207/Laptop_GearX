package com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.QuanLyDonXacNhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
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
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.Adapter.ExpandableListViewDonDatHang_Adapter;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.Models.ChiTietDonDatHang;
import com.example.laptop_gearx.Models.DonDatHang;
import com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.Activity_QuanLyDonDatHang;
import com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.ChucNang_ThanhToanHoaDon.Activity_ThanhToanHoaDon;
import com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.QuanLyDonThanhToan.Activity_QuanLyHoaDonThanhToan;
import com.example.laptop_gearx.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_QuanLyDonXacNhan extends AppCompatActivity {


    public static DonDatHang ddh_chose;

    ProgressDialog TempDialog;
    int i =0;

    Toolbar toolbar;

    ExpandableListView lv;


    List<DonDatHang> mListDonDatHang;
    Map<DonDatHang, List<ChiTietDonDatHang>> mListCTDon;
    ExpandableListViewDonDatHang_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__quan_ly_don_xac_nhan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Init();
        Events();
    }

    private void Events() {
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ddh_chose = new DonDatHang();
                ddh_chose = mListDonDatHang.get(position);
                return false;
            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.QuanLyDonXacNhan_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Đơn đã xác nhận");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mListDonDatHang = new ArrayList<>();
        mListCTDon = new HashMap<>();

        TempDialog = new ProgressDialog(Activity_QuanLyDonXacNhan.this);
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setTitle("Đang tải dữ liệu");
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        lv = findViewById(R.id.QuanLyDonXacNhan_expLv);
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
        dataservice.getDonHangXacNhan().enqueue(new Callback<List<DonDatHang>>() {
            @Override
            public void onResponse(Call<List<DonDatHang>> call, Response<List<DonDatHang>> response) {
                if(response.isSuccessful()){
                    List<DonDatHang> list_don = response.body();
                    if(list_don.size()==0){
                        Toast.makeText(Activity_QuanLyDonXacNhan.this,"Không có hóa đơn nào",Toast.LENGTH_SHORT).show();
                        TempDialog.dismiss();
                    }else{
                        for (DonDatHang ddh :list_don){
                            mListDonDatHang.add(ddh);
                            dataservice.getCTDonDatHangByMaDonDatHang(ddh.getMaDonDatHang()).enqueue(new Callback<List<ChiTietDonDatHang>>() {
                                @Override
                                public void onResponse(Call<List<ChiTietDonDatHang>> call, Response<List<ChiTietDonDatHang>> response) {
                                    if(response.isSuccessful()){
                                        List<ChiTietDonDatHang> list_ct = response.body();
                                        mListCTDon.put(ddh,list_ct);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<ChiTietDonDatHang>> call, Throwable t) {
                                    Toast.makeText(Activity_QuanLyDonXacNhan.this,"Lấy hóa đơn thất bại",Toast.LENGTH_SHORT).show();
                                    TempDialog.dismiss();
                                }
                            });
                        }
                        TempDialog.dismiss();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Activity_QuanLyDonXacNhan.this,"Lấy hóa đơn : "+mListDonDatHang.size(),Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<List<DonDatHang>> call, Throwable t) {
                Toast.makeText(Activity_QuanLyDonXacNhan.this,"Lấy hóa đơn thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });
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
                startActivity(new Intent(Activity_QuanLyDonXacNhan.this, Activity_QuanLyDonDatHang.class));
                break;
            case R.id.option_menu_donhang_itDaXacNhan:
                break;
            case R.id.option_menu_donhang_itThanhToan:
                startActivity(new Intent(Activity_QuanLyDonXacNhan.this, Activity_QuanLyHoaDonThanhToan.class));

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_dondathang_xacnhan, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu_dondathang_itThanhToan:
                startActivity(new Intent(Activity_QuanLyDonXacNhan.this, Activity_ThanhToanHoaDon.class));
                break;
            case R.id.context_menu_donhang_choxacnhan_itHuyDon:
                break;
        }


        return super.onContextItemSelected(item);
    }

}
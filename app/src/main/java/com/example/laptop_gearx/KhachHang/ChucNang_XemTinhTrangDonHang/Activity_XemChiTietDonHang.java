package com.example.laptop_gearx.KhachHang.ChucNang_XemTinhTrangDonHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.KhachHang.ChucNang_DatHangTuGioHang.Adapter_ChiTietDatHang;
import com.example.laptop_gearx.Models.ChiTietDonDatHang;
import com.example.laptop_gearx.Models.ChiTietGioHang;
import com.example.laptop_gearx.Models.DonDatHang;
import com.example.laptop_gearx.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_XemChiTietDonHang extends AppCompatActivity {
    Toolbar toolbar;

    TextView tvMaDon,tvNgayDat,tvTongtien,tvTinhTrang,tvLiDoHuy,tvNoiDungHuy;
    ListView lv;

    ProgressDialog TempDialog;
    int i =0;

    ArrayList<ChiTietDonDatHang> listCTDH;
    Adapter_XemChiTietDatHang adapter_chiTietDatHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__xem_chi_tiet_don_hang);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();

    }

    private void Init() {
        toolbar = findViewById(R.id.XemCTDonHang_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết đơn hàng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TempDialog = new ProgressDialog(Activity_XemChiTietDonHang.this);
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setTitle("Đang tải");
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        tvMaDon = findViewById(R.id.XemCTDonHang_tvMaDon);
        tvNgayDat = findViewById(R.id.XemCTDonHang_tvNgayDat);
        tvTongtien = findViewById(R.id.XemCTDonHang_tvTongTien);
        tvTinhTrang = findViewById(R.id.XemCTDonHang_tvTinhTrangDon);
        tvLiDoHuy = findViewById(R.id.XemCTDonHang_tvLyDoHuyDon);
        tvNoiDungHuy = findViewById(R.id.XemCTDonHang_tvNoiDungHuyDon);


        listCTDH = new ArrayList<>();
        lv = findViewById(R.id.XemCTDonHang_lv);
        adapter_chiTietDatHang = new Adapter_XemChiTietDatHang(Activity_XemChiTietDonHang.this,listCTDH);

        lv.setAdapter(adapter_chiTietDatHang);

        GetData();


    }

    private void GetData() {
        TempDialog.show();
        //set dữ liệu đơn hàng
        DonDatHang ddh = Activity_KhachHang_XemTinhTrangDonHang.ddh_chose;
        tvMaDon.setText("Mã đơn : "+ddh.getMaDonDatHang());
        tvNgayDat.setText("Ngày đặt : "+ddh.getNgayDat());

        tvLiDoHuy.setVisibility(View.GONE);
        tvNoiDungHuy.setVisibility(View.GONE);

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String gia = "Tổng tiền : " +formatter.format(ddh.getTongTien())+" VNĐ";
        tvTongtien.setText(gia);

        switch (ddh.getTrangThai()){
            case "dangxacnhan":
                tvTinhTrang.setText("ĐANG CHỜ XÁC NHẬN");
                break;
            case "xacnhan":
                tvTinhTrang.setText("ĐÃ XÁC NHẬN");
                break;
            case "thanhtoan":
                tvTinhTrang.setText("ĐÃ THANH TOÁN");
                break;
            case "huy":
                tvTinhTrang.setText("ĐƠN BỊ HỦY");
                tvLiDoHuy.setVisibility(View.VISIBLE);
                tvNoiDungHuy.setVisibility(View.VISIBLE);
                break;
        }


        listCTDH.clear();
        Dataservice dataservice = APIService.getService();
        dataservice.getCTDonDatHangByMaDonDatHang(Activity_KhachHang_XemTinhTrangDonHang.ddh_chose.getMaDonDatHang()).enqueue(new Callback<List<ChiTietDonDatHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietDonDatHang>> call, Response<List<ChiTietDonDatHang>> response) {
                if(response.isSuccessful()){
                    ArrayList<ChiTietDonDatHang> list_ct = (ArrayList<ChiTietDonDatHang>) response.body();
                    for(ChiTietDonDatHang ct : list_ct){
                        listCTDH.add(ct);
                    }
                    adapter_chiTietDatHang.notifyDataSetChanged();
                    TempDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietDonDatHang>> call, Throwable t) {
                Toast.makeText(Activity_XemChiTietDonHang.this,"Lấy dữ liệu đơn hàng thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });
    }
}
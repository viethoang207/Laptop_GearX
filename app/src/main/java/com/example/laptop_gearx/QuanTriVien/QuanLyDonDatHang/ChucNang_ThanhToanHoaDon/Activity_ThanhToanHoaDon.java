package com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.ChucNang_ThanhToanHoaDon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.KhachHang.ChucNang_DatHangTuGioHang.Adapter_ChiTietDatHang;
import com.example.laptop_gearx.Models.ChiTietDonDatHang;
import com.example.laptop_gearx.Models.ChiTietGioHang;
import com.example.laptop_gearx.Models.ChiTietHoaDonBan;
import com.example.laptop_gearx.Models.DonDatHang;
import com.example.laptop_gearx.Models.HoaDonBan;
import com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.Activity_QuanLyDonDatHang;
import com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.QuanLyDonXacNhan.Activity_QuanLyDonXacNhan;
import com.example.laptop_gearx.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_ThanhToanHoaDon extends AppCompatActivity {
    Toolbar toolbar;
    
    TextView tvMaHD,tvNguoiBan,tvNguoiMua,tvNgayBan,tvTongTien;
    ListView lv;
    Button btnThanhToan;

    ProgressDialog TempDialog;
    int i =0;

    int tongtien_hoadon = 0;

    ArrayList<ChiTietDonDatHang> listCT;
    Adapter_ThanhToanHoaDon_ItemsLaptop adapter_chiTietDatHang;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__thanh_toan_hoa_don);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        
        Init();
        Events();
    }

    private void Events() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder al = new AlertDialog.Builder(Activity_ThanhToanHoaDon.this);
                al.setMessage("Xác nhận thanh toán hóa đơn này ?");
                al.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ThanhToanHoaDon();
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
        });
    }

    private void ThanhToanHoaDon(){
        TempDialog.show();

        //các dữ liệu liên quan đến hóa đơn bán
        HoaDonBan hdb = new HoaDonBan();
        hdb.setMaHoaDonBan(tvMaHD.getText().toString());
        hdb.setHoTenNguoiBan(tvNguoiBan.getText().toString());
        hdb.setHoTenNguoiMua(tvNguoiMua.getText().toString());
        hdb.setNgayBan(tvNgayBan.getText().toString());
        hdb.setTongTien(tongtien_hoadon);

        Dataservice dataservice = APIService.getService();
        dataservice.themHoaDonBan(hdb).enqueue(new Callback<HoaDonBan>() {
            @Override
            public void onResponse(Call<HoaDonBan> call, Response<HoaDonBan> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Activity_ThanhToanHoaDon.this,"Lập hóa đơn bán thành công",Toast.LENGTH_SHORT).show();
                    for(ChiTietDonDatHang ct : listCT){
                        ChiTietHoaDonBan cthdb = new ChiTietHoaDonBan();
                        cthdb.setMaHoaDonBan(tvMaHD.getText().toString());
                        cthdb.setMaLaptop(ct.getMaLaptop());
                        cthdb.setTenLaptop(ct.getTenLaptop());
                        cthdb.setLinkAnh(ct.getLinkAnh());
                        cthdb.setSoLuong(ct.getSoLuong());
                        cthdb.setGiaBan(ct.getGiaBan());
                        cthdb.setThanhtien(ct.getThanhtien());

                        dataservice.themCTHoaDonBan(cthdb).enqueue(new Callback<ChiTietHoaDonBan>() {
                            @Override
                            public void onResponse(Call<ChiTietHoaDonBan> call, Response<ChiTietHoaDonBan> response) {
                                if(response.isSuccessful()){
                                    Log.d("themcthoadon","thanhcong");
                                }
                            }

                            @Override
                            public void onFailure(Call<ChiTietHoaDonBan> call, Throwable t) {
                                Log.d("themcthoadon","thatbai");
                            }
                        });
                    }
                    dataservice.ThanhToanDonHang(Activity_QuanLyDonXacNhan.ddh_chose.getMaDonDatHang()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(Activity_ThanhToanHoaDon.this,"Đã xác nhận thanh toán đơn hàng",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(Activity_ThanhToanHoaDon.this,"Lỗi xác nhận thanh toán đơn hàng",Toast.LENGTH_SHORT).show();

                        }
                    });
                    TempDialog.dismiss();
                    startActivity(new Intent(Activity_ThanhToanHoaDon.this,Activity_QuanLyDonDatHang.class));
                }
            }

            @Override
            public void onFailure(Call<HoaDonBan> call, Throwable t) {
                Toast.makeText(Activity_ThanhToanHoaDon.this,"Thêm hóa đơn thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.ThanhToanHoaDon_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tạo hóa đơn");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvMaHD = findViewById(R.id.ThanhToanHoaDon_tvMaHD);
        tvNguoiBan = findViewById(R.id.ThanhToanHoaDon_tvNguoiBan);
        tvNguoiMua = findViewById(R.id.ThanhToanHoaDon_NguoiMua);
        tvNgayBan = findViewById(R.id.ThanhToanHoaDon_tvNgayBan);
        tvTongTien = findViewById(R.id.ThanhToanHoaDon_tvTongTien);

        lv = findViewById(R.id.ThanhToanHoaDon_lv);

        btnThanhToan = findViewById(R.id.ThanhToanHoaDon_btnThanhToan);

        TempDialog = new ProgressDialog(Activity_ThanhToanHoaDon.this);
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setTitle("Đang tải dữ liệu");
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        listCT = new ArrayList<>();
        adapter_chiTietDatHang = new Adapter_ThanhToanHoaDon_ItemsLaptop(Activity_ThanhToanHoaDon.this,listCT);

        lv.setAdapter(adapter_chiTietDatHang);

        SetData();


    }

    private void SetData() {

        TempDialog.show();
        DonDatHang ddh_get = Activity_QuanLyDonXacNhan.ddh_chose;
        tvMaHD.setText("HD"+ddh_get.getMaDonDatHang());
        tvNguoiBan.setText("Công ty TNHH GearX");
        tvNguoiMua.setText(ddh_get.getHoTen());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        tvNgayBan.setText(currentDateandTime);
        Dataservice dataservice = APIService.getService();
        dataservice.getCTDonDatHangByMaDonDatHang(ddh_get.getMaDonDatHang()).enqueue(new Callback<List<ChiTietDonDatHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietDonDatHang>> call, Response<List<ChiTietDonDatHang>> response) {
                if(response.isSuccessful()){
                    ArrayList<ChiTietDonDatHang> list_ct = (ArrayList<ChiTietDonDatHang>) response.body();
                    for(ChiTietDonDatHang ct : list_ct){
                        tongtien_hoadon+=ct.getThanhtien();
                        listCT.add(ct);
                    }
                    TempDialog.dismiss();
                    DecimalFormat formatter2 = new DecimalFormat("###,###,###");
                    String tongtien =  formatter2.format(tongtien_hoadon)+" VNĐ";
                    tvTongTien.setText("Tổng tiền : "+tongtien);

                    adapter_chiTietDatHang.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietDonDatHang>> call, Throwable t) {
                Toast.makeText(Activity_ThanhToanHoaDon.this,"Lấy dữ liệu thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });

    }
}
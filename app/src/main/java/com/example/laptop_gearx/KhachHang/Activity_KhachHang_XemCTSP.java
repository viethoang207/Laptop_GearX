package com.example.laptop_gearx.KhachHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.KhachHang.ChucNang_GioHang.Activity_ChiTietGioHang;
import com.example.laptop_gearx.Models.ChiTietGioHang;
import com.example.laptop_gearx.Models.Laptop;
import com.example.laptop_gearx.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_KhachHang_XemCTSP extends AppCompatActivity {
    Toolbar toolbar;

    ImageView img;
    TextView tvGiaBan,tvTen,tvThuongHieu,tvCauHinh,tvMauSac,tvKho,tvDaCoGioHang;

    LinearLayout linearGioHang;
    Button btnMuaNgay;

    ArrayList<ChiTietGioHang> listCTGH;

    boolean isExistCategory = false;

    Laptop lt_chose;

    ProgressDialog TempDialog;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__khachhang_xemctlaptop);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Init();
        Events();
    }

    private void Events() {
        linearGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lt_chose.getSoLuong()==0){
                    Toast.makeText(Activity_KhachHang_XemCTSP.this,"Sản phẩm tạm thời đang hết",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isExistCategory==true){
                    startActivity(new Intent(Activity_KhachHang_XemCTSP.this, Activity_ChiTietGioHang.class));
                }else{
                    TempDialog.show();
                    //thêm vào giỏ hàng
                    ChiTietGioHang ctgh = new ChiTietGioHang();
                    ctgh.setTenTk(Activity_DangNhap.tk_login.getTenTk());
                    ctgh.setMaLaptop(lt_chose.getMaLaptop());
                    ctgh.setTenLaptop(lt_chose.getTenLaptop());
                    ctgh.setLinkAnh(lt_chose.getLinkAnh());
                    ctgh.setSoLuong(1);
                    ctgh.setGiaBan(lt_chose.getGiaBan());
                    ctgh.setThanhTien(lt_chose.getGiaBan());

                    Dataservice dataservice = APIService.getService();
                    dataservice.themGioHang(ctgh).enqueue(new Callback<ChiTietGioHang>() {
                        @Override
                        public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(Activity_KhachHang_XemCTSP.this,"Thêm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show();
                                TempDialog.dismiss();
                                finish();
                                //startActivity(new Intent(Activity_KhachHang_XemCTSP.this,Activity_ManHinhChinh_KhachHang.class));
                            }else{
                                Toast.makeText(Activity_KhachHang_XemCTSP.this,"Thêm vào giỏ hàng thất bại",Toast.LENGTH_SHORT).show();
                                TempDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<ChiTietGioHang> call, Throwable t) {
                            Toast.makeText(Activity_KhachHang_XemCTSP.this,"Thêm vào giỏ hàng thất bại",Toast.LENGTH_SHORT).show();
                            TempDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.XemCTSP_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listCTGH = new ArrayList<>();
        listCTGH.clear();
        listCTGH = Activity_ManHinhChinh_KhachHang.listCTGH;

        TempDialog = new ProgressDialog(Activity_KhachHang_XemCTSP.this);
        TempDialog.setTitle("Đang thêm vào giỏ hàng");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        img = findViewById(R.id.XemCTSP_img);

        tvGiaBan = findViewById(R.id.XemCTSP_tvGiaBan);
        tvTen = findViewById(R.id.XemCTSP_tvTenLaptop);
        tvThuongHieu = findViewById(R.id.XemCTSP_tvThuongHieu);
        tvCauHinh = findViewById(R.id.XemCTSP_tvCauHinh);
        tvMauSac = findViewById(R.id.XemCTSP_tvMauSac);
        tvKho = findViewById(R.id.XemCTSP_tvSLCon);
        tvDaCoGioHang = findViewById(R.id.XemCTSP_tvDaCoGioHang);

        linearGioHang = findViewById(R.id.XemCTSP_linearThemGioHang);
        btnMuaNgay = findViewById(R.id.XemCTSP_btnMuaNgay);

        tvDaCoGioHang.setVisibility(View.GONE);
        SetData();
    }

    private void SetData() {
        Intent it = getIntent();
        String value = it.getStringExtra("maLaptopClick");
        int maLaptop = Integer.parseInt(value);

        List<Laptop> listLaptop = Activity_ManHinhChinh_KhachHang.listLaptopFromDB;

        Laptop lt = new Laptop();

        for(Laptop l : listLaptop){
            if(l.getMaLaptop() == maLaptop){
                lt = l;
                lt_chose = new Laptop();
                lt_chose = l;
                break;
            }
        }

        if(lt.getLinkAnh()==""){
            img.setImageResource(R.drawable.ic_laptop);
        }else{
            Picasso.get()
                    .load(lt.getLinkAnh().toString().trim())
                    .into(img);
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String gia = formatter.format(lt.getGiaBan())+" đ";
        tvGiaBan.setText(gia);
        tvTen.setText(lt.getTenLaptop());
        tvCauHinh.setText(lt.getCauhinh());
        tvMauSac.setText(lt.getMauSac());
        tvThuongHieu.setText(lt.getThuongHieu());
        tvKho.setText(lt.getSoLuong()+"");
        getSupportActionBar().setTitle(lt.getTenLaptop()+"");

        for(ChiTietGioHang ct : listCTGH){
            if(ct.getMaLaptop() == lt.getMaLaptop()){
                tvDaCoGioHang.setVisibility(View.VISIBLE);
                isExistCategory = true;
                //Toast.makeText(Activity_KhachHang_XemCTSP.this,"Đã có giỏ hàng",Toast.LENGTH_SHORT).show();
                break;
            }
        }



    }
}
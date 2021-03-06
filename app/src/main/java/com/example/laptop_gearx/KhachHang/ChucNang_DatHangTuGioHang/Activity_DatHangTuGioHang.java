package com.example.laptop_gearx.KhachHang.ChucNang_DatHangTuGioHang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.KhachHang.Activity_ManHinhChinh_KhachHang;
import com.example.laptop_gearx.KhachHang.ChucNang_GioHang.Activity_ChiTietGioHang;
import com.example.laptop_gearx.KhachHang.ChucNang_GioHang.Adapter_ChiTietGioHang;
import com.example.laptop_gearx.Models.ChiTietDonDatHang;
import com.example.laptop_gearx.Models.ChiTietGioHang;
import com.example.laptop_gearx.Models.DonDatHang;
import com.example.laptop_gearx.Models.NguoiDung;
import com.example.laptop_gearx.QuanTriVien.Activity_ManHinhChinh_QTV;
import com.example.laptop_gearx.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_DatHangTuGioHang extends AppCompatActivity {

    Toolbar toolbar;

    ProgressDialog TempDialog;
    int i = 0;

    EditText edtTen;
    TextView tvNgayDat, tvNgayNhan,tvTongTien;
    ListView lv;
    Button btnXacNhan;

    ArrayList<ChiTietGioHang> listCTGH;
    Adapter_ChiTietDatHang adapter_chiTietGioHang;

    int tt = 0;

    String maDDH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__dat_hang_tu_gio_hang);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        Date date = new Date();



        tvNgayNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_DatHangTuGioHang.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year,month,dayOfMonth);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            tvNgayNhan.setText(sdf.format(calendar.getTime()));

                        }
                    },nam,thang,ngay);
                    datePickerDialog.show();
                }

            }
        });


        //s??? ki???n n??t ?????t h??ng x??c nh???n
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTen.getText().length()==0){
                    edtTen.setError("Th??ng tin c??n tr???ng");
                    edtTen.requestFocus();
                    return;
                }

                if(tvNgayNhan.getText().toString().toLowerCase().equals("ch???n ng??y")){
                    Toast.makeText(Activity_DatHangTuGioHang.this,"Vui l??ng ch???n ng??y ?????n nh???n",Toast.LENGTH_SHORT).show();
                    return;
                }

                TempDialog.show();

                //th???c hi???n th??m 1 ????n ?????t h??ng
                DonDatHang donDatHang = new DonDatHang();
                donDatHang.setTenTk(Activity_DangNhap.tk_login.getTenTk());
                donDatHang.setHoTen(edtTen.getText().toString());


                //t???o m?? ????n ?????t h??ng
                String maDonHang = "DH"+calendar.getTimeInMillis();
                maDDH = maDonHang;

                donDatHang.setMaDonDatHang(maDonHang);

                donDatHang.setNgayDat(tvNgayDat.getText().toString());
                donDatHang.setNgayHenLay(tvNgayNhan.getText().toString());

                donDatHang.setTongTien(tt);
                donDatHang.setTrangThai("dangxacnhan");



                Dataservice dataservice = APIService.getService();
                dataservice.themDonDatHang(donDatHang).enqueue(new Callback<DonDatHang>() {
                    @Override
                    public void onResponse(Call<DonDatHang> call, Response<DonDatHang> response) {
                        if(response.isSuccessful()){
                            for(ChiTietGioHang ct : listCTGH){
                                ChiTietDonDatHang ctddh = new ChiTietDonDatHang();
                                ctddh.setMaDonDatHang(maDDH);
                                ctddh.setMaLaptop(ct.getMaLaptop());
                                ctddh.setTenLaptop(ct.getTenLaptop());
                                ctddh.setLinkAnh(ct.getLinkAnh());
                                ctddh.setSoLuong(ct.getSoLuong());
                                ctddh.setGiaBan(ct.getGiaBan());
                                ctddh.setThanhtien(ct.getThanhTien());

                                ThemChiTietDonHang(ctddh);

                                dataservice.deleteCTGH(Activity_DangNhap.tk_login.getTenTk(),ctddh.getMaLaptop()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()){
                                            Log.d("remove_ctgh "+ctddh.getMaLaptop(),"success");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.d("remove_ctgh "+ctddh.getMaLaptop(),"fail");
                                    }
                                });
                            }
                            TempDialog.dismiss();
                            startActivity(new Intent(Activity_DatHangTuGioHang.this,Activity_ManHinhChinh_KhachHang.class));
                        }
                    }

                    private void ThemChiTietDonHang(ChiTietDonDatHang ct) {
                        Dataservice dataservice = APIService.getService();
                        dataservice.themCTDonDatHang(ct).enqueue(new Callback<ChiTietDonDatHang>() {
                            @Override
                            public void onResponse(Call<ChiTietDonDatHang> call, Response<ChiTietDonDatHang> response) {

                            }

                            @Override
                            public void onFailure(Call<ChiTietDonDatHang> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<DonDatHang> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.DatHangGioHang_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi ti???t ?????t h??ng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                //startActivity(new Intent(Activity_DatHangTuGioHang.this, Activity_ManHinhChinh_KhachHang.class));
            }
        });

        TempDialog = new ProgressDialog(Activity_DatHangTuGioHang.this);
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setTitle("??ang x??? l??");
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        edtTen = findViewById(R.id.DatHangGioHang_edtHoTen);
        tvNgayDat = findViewById(R.id.DatHangGioHang_tvNgayDat);
        tvNgayNhan = findViewById(R.id.DatHangGioHang_tvNgayNhan);
        tvTongTien = findViewById(R.id.DatHangGioHang_tvTongTien);

        lv = findViewById(R.id.DatHangGioHang_lv);
        btnXacNhan = findViewById(R.id.DatHangGioHang_btnXacNhan);

        listCTGH = new ArrayList<>();
        listCTGH = Activity_ChiTietGioHang.listGioHang;
        adapter_chiTietGioHang = new Adapter_ChiTietDatHang(Activity_DatHangTuGioHang.this, listCTGH);



        SetData();

    }

    private void SetData() {
        //set H??? t??n
        TempDialog.show();

        Dataservice dataservice = APIService.getService();
        dataservice.getNguoidungByTenTK(Activity_DangNhap.tk_login.getTenTk()).enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                if (response.isSuccessful()) {
                    ArrayList<NguoiDung> nd_list = new ArrayList<>();
                    nd_list = (ArrayList<NguoiDung>) response.body();
                    if (nd_list.size() == 0) {
                        Toast.makeText(Activity_DatHangTuGioHang.this, "L???y d??? li???u nd th???t b???i 1", Toast.LENGTH_SHORT).show();
                    } else {
                        for (NguoiDung nd : nd_list) {
                            edtTen.setText(nd.getHoTen());
                            break;
                        }
                        SetData2();

                        TempDialog.dismiss();
                    }
                } else {
                    Toast.makeText(Activity_DatHangTuGioHang.this, "L???y d??? li???u nd th???t b???i 2", Toast.LENGTH_SHORT).show();
                    TempDialog.dismiss();
                }
            }

            private void SetData2() {
                //set ng??y hi???n t???i
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());

                tvNgayDat.setText(currentDateandTime);


                for(ChiTietGioHang ct : listCTGH){
                    tt+=ct.getThanhTien();
                }


                lv.setAdapter(adapter_chiTietGioHang);
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                String gia = "???" +formatter.format(tt);
                tvTongTien.setText(gia);
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                Toast.makeText(Activity_DatHangTuGioHang.this, "L???y d??? li???u nd th???t b???i 2", Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });









    }


}
package com.example.laptop_gearx.QuanTriVien.QuanLyLaptop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.MainActivity;
import com.example.laptop_gearx.Models.Laptop;
import com.example.laptop_gearx.Models.NhomThuongHieu;
import com.example.laptop_gearx.QuanTriVien.Activity_ManHinhChinh_QTV;
import com.example.laptop_gearx.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_ThemLaptop extends AppCompatActivity {
    Toolbar toolbar;

    ArrayList<Laptop> listLaptop;

    public static ArrayList<String> listThuongHieu;
    ArrayAdapter<String> adapterThuongHieu;

    Spinner spnTH;
    EditText edtMa,edtTen,edtCauHinh,edtMau,edtGiaNhap,edtGiaBan,edtLink,edtSL;
    Button btnThem;

    ProgressDialog TempDialog;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__them_laptop);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtMa.getText().length()==0){
                    edtMa.setError("Thông tin trống");
                    edtMa.requestFocus();
                    return;
                }
                if(TonTaiMaLaptop(Integer.parseInt(edtMa.getText().toString()))==true){
                    edtMa.setError("Mã lớp đã tồn tại");
                    edtMa.requestFocus();
                    return;
                }
                if(edtTen.getText().length()==0){
                    edtTen.setError("Thông tin trống");
                    edtTen.requestFocus();
                    return;
                }
                if(edtCauHinh.getText().length()==0){
                    edtCauHinh.setError("Thông tin trống");
                    edtCauHinh.requestFocus();
                    return;
                }
                if(edtMau.getText().length()==0){
                    edtMau.setError("Thông tin trống");
                    edtMau.requestFocus();
                    return;
                }
                if(edtGiaNhap.getText().length()==0){
                    edtGiaNhap.setError("Thông tin trống");
                    edtGiaNhap.requestFocus();
                    return;
                }
                if(edtGiaBan.getText().length()==0){
                    edtGiaBan.setError("Thông tin trống");
                    edtGiaBan.requestFocus();
                    return;
                }
                if(edtSL.getText().length()==0){
                    edtSL.setError("Thông tin trống");
                    edtSL.requestFocus();
                    return;
                }
                if(spnTH.getSelectedItemPosition()==0){
                    Toast.makeText(Activity_ThemLaptop.this,"Vui lòng chọn thương hiệu",Toast.LENGTH_SHORT).show();
                    return;
                }

                TempDialog.show();
                //lấy về thông tin laptop vừa nhập
                int ma = Integer.parseInt(edtMa.getText().toString());
                String ten = edtTen.getText().toString();
                String th = spnTH.getSelectedItem().toString();
                String cauHinh = edtCauHinh.getText().toString();
                String mau = edtMau.getText().toString();
                int giaNhap = Integer.parseInt(edtGiaNhap.getText().toString());
                int giaBan = Integer.parseInt(edtGiaBan.getText().toString());
                String link = edtLink.getText().toString();
                int sl = Integer.parseInt(edtSL.getText().toString());

                Laptop l = new Laptop(ma,ten,th,cauHinh,mau,giaNhap,giaBan,link,sl);
                //thêm laptop
                Dataservice dataservice = APIService.getService();
                dataservice.themLaptop(l).enqueue(new Callback<Laptop>() {
                    @Override
                    public void onResponse(Call<Laptop> call, Response<Laptop> response) {
                        Toast.makeText(Activity_ThemLaptop.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                        TempDialog.dismiss();
                        startActivity(new Intent(Activity_ThemLaptop.this,Activity_QuanLyLaptop.class));
                    }

                    @Override
                    public void onFailure(Call<Laptop> call, Throwable t) {
                        Toast.makeText(Activity_ThemLaptop.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        TempDialog.dismiss();
                    }
                });
            }
        });
    }

    private boolean TonTaiMaLaptop(int ma){
        for(Laptop lt : listLaptop){
            if(lt.getMaLaptop()==ma){
                return true;
            }
        }
        return false;
    }

    private void Init() {
        toolbar = findViewById(R.id.ThemLaptop_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm sản phẩm");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TempDialog = new ProgressDialog(Activity_ThemLaptop.this);
        TempDialog.setTitle("Đang tải dữ liệu");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        spnTH = findViewById(R.id.ThemLaptop_spnThuongHieu);

        listThuongHieu = new ArrayList<>();
        adapterThuongHieu = new ArrayAdapter<>(Activity_ThemLaptop.this, android.R.layout.simple_list_item_1,listThuongHieu);
        spnTH.setAdapter(adapterThuongHieu);

        listThuongHieu.clear();
        listThuongHieu.add("Chọn thương hiệu");
        GetData();

        edtMa = findViewById(R.id.ThemLaptop_edtMaLaptop);
        edtTen = findViewById(R.id.ThemLaptop_edtTenLaptop);
        edtCauHinh = findViewById(R.id.ThemLaptop_edtCauHinh);
        edtMau = findViewById(R.id.ThemLaptop_edtMauSac);
        edtGiaNhap = findViewById(R.id.ThemLaptop_edtGiaNhap);
        edtGiaBan = findViewById(R.id.ThemLaptop_edtGiaBan);
        edtLink = findViewById(R.id.ThemLaptop_edtLink);
        edtSL = findViewById(R.id.ThemLaptop_edtSL);
        btnThem = findViewById(R.id.ThemLaptop_btnThem);
    }



    private void GetData(){
        TempDialog.show();
        listLaptop = Activity_QuanLyLaptop.listLaptop;
        Dataservice dataservice = APIService.getService();
        Call<List<NhomThuongHieu>> callBack = dataservice.getAllNhomThuongHieu();
        callBack.enqueue(new Callback<List<NhomThuongHieu>>() {
            @Override
            public void onResponse(Call<List<NhomThuongHieu>> call, Response<List<NhomThuongHieu>> response) {
                ArrayList<NhomThuongHieu> nth_list = new ArrayList<>();
                nth_list = (ArrayList<NhomThuongHieu>) response.body();
                if(nth_list.size()==0){
                    Toast.makeText(Activity_ThemLaptop.this,"Lấy dữ liệu NTH thất bại",Toast.LENGTH_SHORT).show();
                }else{
                    for(NhomThuongHieu n : nth_list){
                        listThuongHieu.add(n.getThuongHieu());
                    }
                    adapterThuongHieu.notifyDataSetChanged();
                    TempDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<NhomThuongHieu>> call, Throwable t) {
                Toast.makeText(Activity_ThemLaptop.this,"Lấy dữ liệu NTH thất bại",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
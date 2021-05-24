package com.example.laptop_gearx.QuanTriVien.QuanLyLaptop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.Models.Laptop;
import com.example.laptop_gearx.Models.NhomThuongHieu;
import com.example.laptop_gearx.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_XemChiTietLaptop extends AppCompatActivity {
    Toolbar toolbar;

    ProgressDialog TempDialog;
    int i=0;

    Spinner spnTH;
    EditText edtTen,edtCauHinh,edtMau,edtGiaNhap,edtGiaBan,edtLink,edtSL;
    TextView tvMa;
    ImageView img;

    ArrayList<String> listTH;
    ArrayAdapter<String> adapterTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__xem_chi_tiet_laptop);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {

    }

    private void Init() {
        toolbar = findViewById(R.id.XemLaptop_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết sản phẩm");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TempDialog = new ProgressDialog(Activity_XemChiTietLaptop.this);
        TempDialog.setTitle("Đang tải dữ liệu");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        spnTH = findViewById(R.id.XemLaptop_spnThuongHieu);
        edtTen = findViewById(R.id.XemLaptop_edtTenLaptop);
        edtCauHinh = findViewById(R.id.XemLaptop_edtCauHinh);
        edtMau = findViewById(R.id.XemLaptop_edtMauSac);
        edtGiaNhap = findViewById(R.id.XemLaptop_edtGiaNhap);
        edtGiaBan = findViewById(R.id.XemLaptop_edtGiaBan);
        edtLink = findViewById(R.id.XemLaptop_edtLink);
        edtSL = findViewById(R.id.XemLaptop_edtSL);
        tvMa = findViewById(R.id.XemLaptop_tvMaLaptop);

        img = findViewById(R.id.XemLaptop_img);

        listTH = new ArrayList<>();
        listTH.add("Chọn thương hiệu");

        adapterTH = new ArrayAdapter<>(Activity_XemChiTietLaptop.this, android.R.layout.simple_list_item_1,listTH);
        spnTH.setAdapter(adapterTH);

        GetData();


    }

    private void SetData() {
        Laptop lt = Activity_QuanLyLaptop.lt_chose;

        if(lt.getLinkAnh()==""){
            img.setImageResource(R.drawable.ic_laptop);
        }else{
            Picasso.get()
                    .load(lt.getLinkAnh().toString().trim())
                    .into(img);
        }

        tvMa.setText(lt.getMaLaptop()+"");
        edtTen.setText(lt.getTenLaptop());
        //Set dữ liệu cho spinner
        String th = lt.getThuongHieu();
        int index = -1;
        for(int i=0;i<listTH.size();++i){
            if(listTH.get(i).toLowerCase().equals(th.toLowerCase())){
                index = i;
            }
        }

        spnTH.setSelection(index);





        edtCauHinh.setText(lt.getCauhinh());
        edtMau.setText(lt.getMauSac());
        edtGiaNhap.setText(lt.getGiaNhap()+"");
        edtGiaBan.setText(lt.getGiaBan()+"");
        edtLink.setText(lt.getLinkAnh());
        edtSL.setText(lt.getSoLuong()+"");

    }

    private void GetData(){
        TempDialog.show();
        Dataservice dataservice = APIService.getService();
        Call<List<NhomThuongHieu>> callBack = dataservice.getAllNhomThuongHieu();
        callBack.enqueue(new Callback<List<NhomThuongHieu>>() {
            @Override
            public void onResponse(Call<List<NhomThuongHieu>> call, Response<List<NhomThuongHieu>> response) {
                ArrayList<NhomThuongHieu> nth_list = new ArrayList<>();
                nth_list = (ArrayList<NhomThuongHieu>) response.body();
                if(nth_list.size()==0){
                    Toast.makeText(Activity_XemChiTietLaptop.this,"Lấy dữ liệu NTH thất bại",Toast.LENGTH_SHORT).show();
                    TempDialog.dismiss();
                    startActivity(new Intent(Activity_XemChiTietLaptop.this,Activity_QuanLyLaptop.class));
                }else{
                    for(NhomThuongHieu n : nth_list){
                        listTH.add(n.getThuongHieu());
                    }
                    adapterTH.notifyDataSetChanged();
                    TempDialog.dismiss();
                    SetData();
                }
            }

            @Override
            public void onFailure(Call<List<NhomThuongHieu>> call, Throwable t) {
                Toast.makeText(Activity_XemChiTietLaptop.this,"Lấy dữ liệu NTH thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
                startActivity(new Intent(Activity_XemChiTietLaptop.this,Activity_QuanLyLaptop.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_xemlaptop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuXemLaptop_itLuu:
                UpdateLaptop();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UpdateLaptop() {
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
            Toast.makeText(Activity_XemChiTietLaptop.this,"Vui lòng chọn thương hiệu",Toast.LENGTH_SHORT).show();
            return;
        }

        TempDialog.show();
        int ma = Integer.parseInt(tvMa.getText().toString());
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
        dataservice.updateLaptop(l).enqueue(new Callback<Laptop>() {
            @Override
            public void onResponse(Call<Laptop> call, Response<Laptop> response) {
                TempDialog.dismiss();
                Toast.makeText(Activity_XemChiTietLaptop.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Activity_XemChiTietLaptop.this,Activity_QuanLyLaptop.class));
            }

            @Override
            public void onFailure(Call<Laptop> call, Throwable t) {
                Toast.makeText(Activity_XemChiTietLaptop.this,"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });

    }
}
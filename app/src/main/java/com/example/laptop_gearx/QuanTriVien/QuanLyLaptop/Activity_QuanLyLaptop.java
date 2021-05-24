package com.example.laptop_gearx.QuanTriVien.QuanLyLaptop;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.MainActivity;
import com.example.laptop_gearx.Models.Laptop;
import com.example.laptop_gearx.Models.NhomThuongHieu;
import com.example.laptop_gearx.Models.TaiKhoan;
import com.example.laptop_gearx.QuanTriVien.Activity_ManHinhChinh_QTV;
import com.example.laptop_gearx.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_QuanLyLaptop extends AppCompatActivity {
    EditText edtTimKiem;
    ListView lv;

    ProgressBar pgrBar;

    Spinner spnTH;
    ArrayList<String> listTH;
    ArrayAdapter<String> adapterTH;

    public static ArrayList<Laptop> listLaptop;
    Adapter_SanPhamLaptop adapter_sanPhamLaptop;

    ProgressDialog TempDialog;
    int i=0;

    Toolbar toolbar;

    int index_chose = -1;

    public static Laptop lt_chose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__quan_ly_laptop);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    lt_chose = listLaptop.get(position);
                    startActivity(new Intent(Activity_QuanLyLaptop.this,Activity_XemChiTietLaptop.class));
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index_chose = position;
               // Toast.makeText(Activity_QuanLyLaptop.this,listLaptop.get(index_chose).getMaLaptop()+"",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        spnTH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnTH.getSelectedItem().toString().equals("Tất cả")){
                    GetDataLaptop();
                }else{
                    lv.setVisibility(View.GONE);
                    pgrBar.setVisibility(View.VISIBLE);
                    edtTimKiem.setText("");
                    Dataservice dataservice = APIService.getService();
                    dataservice.getLaptopByNhomThuongHieu(spnTH.getSelectedItem().toString()).enqueue(new Callback<List<Laptop>>() {
                        @Override
                        public void onResponse(Call<List<Laptop>> call, Response<List<Laptop>> response) {
                            if(response.isSuccessful()){
                                listLaptop.clear();
                                ArrayList<Laptop> lt_list = new ArrayList<>();
                                lt_list = (ArrayList<Laptop>) response.body();

                                for(Laptop lt : lt_list){
                                    listLaptop.add(lt);
                                }
                                adapter_sanPhamLaptop.notifyDataSetChanged();
                                lv.setVisibility(View.VISIBLE);
                                pgrBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Laptop>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.QTV_QLLaptop_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản lý laptop");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spnTH = findViewById(R.id.QTV_QLLaptop_spnTH);
        listTH = new ArrayList<>();
        adapterTH = new ArrayAdapter<>(Activity_QuanLyLaptop.this, android.R.layout.simple_list_item_1,listTH);
        spnTH.setAdapter(adapterTH);

        pgrBar = findViewById(R.id.QTV_QLLaptop_pgrBar);
        pgrBar.setVisibility(View.GONE);

        edtTimKiem = findViewById(R.id.QTV_QLLaptop_edtTimKiem);
        lv = findViewById(R.id.QTV_QLLaptop_lv);
        registerForContextMenu(lv);
        listLaptop = new ArrayList<>();
        adapter_sanPhamLaptop = new Adapter_SanPhamLaptop(Activity_QuanLyLaptop.this,listLaptop);
        lv.setAdapter(adapter_sanPhamLaptop);

        TempDialog = new ProgressDialog(Activity_QuanLyLaptop.this);
        TempDialog.setTitle("Đang tải dữ liệu");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        GetDataLaptop();

    }

    private void GetDataLaptop(){
        listLaptop.clear();
        TempDialog.show();
        GetDataTH();
        Dataservice dataservice = APIService.getService();
        Call<List<Laptop>> callBack = dataservice.getAllLaptop();
        callBack.enqueue(new Callback<List<Laptop>>() {
            @Override
            public void onResponse(Call<List<Laptop>> call, Response<List<Laptop>> response) {
                ArrayList<Laptop> lt_list = new ArrayList<>();
                lt_list = (ArrayList<Laptop>) response.body();
                if(lt_list.size()==0){
                    Toast.makeText(Activity_QuanLyLaptop.this,"Lấy dữ liệu laptop thất bại",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Activity_QuanLyLaptop.this, Activity_ManHinhChinh_QTV.class));
                }else{
                    for(Laptop lt : lt_list){
                        listLaptop.add(lt);
                    }
                    adapter_sanPhamLaptop.notifyDataSetChanged();
                    TempDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Laptop>> call, Throwable t) {
                Toast.makeText(Activity_QuanLyLaptop.this,"Lấy dữ liệu laptop thất bại",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu_quanlylaptop, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuQLLT_itThem:
                startActivity(new Intent(Activity_QuanLyLaptop.this,Activity_ThemLaptop.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itXoaLaptop:
                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_QuanLyLaptop.this);
                alert.setMessage("Bạn có muốn xoá sản phẩm này ?");
                alert.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RemoveSP();
                    }

                    private void RemoveSP() {
                        TempDialog.show();
                        int ma = listLaptop.get(index_chose).getMaLaptop();
                        Dataservice dataservice = APIService.getService();
                        dataservice.deleteLaptop(ma).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    TempDialog.dismiss();
                                    Toast.makeText(Activity_QuanLyLaptop.this,"Xóa thành công",Toast.LENGTH_SHORT).show();
                                    GetDataLaptop();
                                }else{
                                    Toast.makeText(Activity_QuanLyLaptop.this,"Xóa thất bại",Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(Activity_QuanLyLaptop.this,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                                TempDialog.dismiss();
                            }
                        });
                    }
                });

                alert.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alert.show();
                break;
        }


        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_quanlylaptop, menu);
    }

    private void GetDataTH(){
        TempDialog.show();
        listTH.clear();
        listTH.add("Tất cả");
        Dataservice dataservice = APIService.getService();
        Call<List<NhomThuongHieu>> callBack = dataservice.getAllNhomThuongHieu();
        callBack.enqueue(new Callback<List<NhomThuongHieu>>() {
            @Override
            public void onResponse(Call<List<NhomThuongHieu>> call, Response<List<NhomThuongHieu>> response) {
                ArrayList<NhomThuongHieu> nth_list = new ArrayList<>();
                nth_list = (ArrayList<NhomThuongHieu>) response.body();
                if(nth_list.size()==0){
                    Toast.makeText(Activity_QuanLyLaptop.this,"Lấy dữ liệu NTH thất bại",Toast.LENGTH_SHORT).show();
                }else{
                    for(NhomThuongHieu n : nth_list){
                        listTH.add(n.getThuongHieu());
                    }
                    adapterTH.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<NhomThuongHieu>> call, Throwable t) {
                Toast.makeText(Activity_QuanLyLaptop.this,"Lấy dữ liệu NTH thất bại",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
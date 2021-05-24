package com.example.laptop_gearx.KhachHang.ChucNang_GioHang;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptop_gearx.APIService;
import com.example.laptop_gearx.Dataservice;
import com.example.laptop_gearx.KhachHang.Activity_ManHinhChinh_KhachHang;
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

public class Adapter_ChiTietGioHang extends BaseAdapter {
    Activity context;
    ArrayList<ChiTietGioHang> list;

    List<Laptop> listLaptop = new ArrayList<>();


    public Adapter_ChiTietGioHang() {
    }

    public Adapter_ChiTietGioHang(Activity context, ArrayList<ChiTietGioHang> list) {
        this.context = context;
        this.list = list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_giohang, null);

        ImageView img = row.findViewById(R.id.rowGioHang_img);
        TextView tvTenLaptop = row.findViewById(R.id.rowGioHang_tvTenLaptop);
        TextView tvGia = row.findViewById(R.id.rowGioHang_tvGiaBan);
        Button btnGiam = row.findViewById(R.id.rowGioHang_btnGiam);
        Button btnTang = row.findViewById(R.id.rowGioHang_btnTang);
        TextView tvSL = row.findViewById(R.id.rowGioHang_tvSL);

        ChiTietGioHang ctgh = list.get(position);

        if(ctgh.getLinkAnh()==""){
            img.setImageResource(R.drawable.ic_laptop);
        }else{
            Picasso.get()
                    .load(ctgh.getLinkAnh().toString().trim())
                    .into(img);
        }

        tvTenLaptop.setText(ctgh.getTenLaptop());

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String gia = "₫" +formatter.format(ctgh.getGiaBan());
        tvGia.setText(gia);

        tvSL.setText(ctgh.getSoLuong()+"");

        listLaptop = Activity_ManHinhChinh_KhachHang.listLaptopFromDB;




        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl_kho = GetSLKho(ctgh.getMaLaptop());

                int sl_moi = Integer.parseInt(tvSL.getText().toString())-1;
                if(sl_moi ==0){
                    Toast.makeText(context,"Số lượng đã đạt tối thiểu",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    tvSL.setText(sl_moi+"");
                    GiamSoLuong(ctgh);
                    Activity_ChiTietGioHang.TinhTongTien();
                }

            }
        });

        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl_kho = GetSLKho(ctgh.getMaLaptop());

                int sl_moi = Integer.parseInt(tvSL.getText().toString())+1;
                if(sl_moi>sl_kho){
                    Toast.makeText(context,"Số lượng đã đạt tối đa",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    tvSL.setText(sl_moi+"");
                    TangSoLuong(ctgh);
                    Activity_ChiTietGioHang.TinhTongTien();
                }

            }
        });


        return row;
    }

    private void GiamSoLuong(ChiTietGioHang ct){
        ProgressDialog TempDialog;
        int i=0;

        TempDialog = new ProgressDialog(context);
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setTitle("Đang xử lý");
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        TempDialog.show();

        Dataservice dataservice = APIService.getService();
        dataservice.GiamSLGioHang(ct.getTenTk(),ct.getMaLaptop()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    int sl = ct.getSoLuong()-1;
                    ct.setThanhTien(ct.getGiaBan()*sl);
                    Activity_ChiTietGioHang.adapter_chiTietGioHang.notifyDataSetChanged();
                    Toast.makeText(context,"Giảm số lượng thành công",Toast.LENGTH_SHORT).show();
                    TempDialog.dismiss();
                    Intent intent = context.getIntent();
                    context.finish();
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context,"Giảm số lượng thất bại",Toast.LENGTH_SHORT).show();
                    TempDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context,"Giảm số lượng thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });
    }

    private void TangSoLuong(ChiTietGioHang ct){
        ProgressDialog TempDialog;

        int i=0;
        TempDialog = new ProgressDialog(context);
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setTitle("Đang xử lý");
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        TempDialog.show();

        Dataservice dataservice = APIService.getService();
        dataservice.TangSLGioHang(ct.getTenTk(),ct.getMaLaptop()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    int sl = ct.getSoLuong()+1;
                    ct.setThanhTien(ct.getGiaBan()*sl);
                    Activity_ChiTietGioHang.adapter_chiTietGioHang.notifyDataSetChanged();
                    Toast.makeText(context,"Tăng số lượng thành công",Toast.LENGTH_SHORT).show();


                    Intent intent = context.getIntent();
                    context.finish();
                    context.startActivity(intent);



                    TempDialog.dismiss();
                }else{
                    Toast.makeText(context,"Tăng số lượng thất bại",Toast.LENGTH_SHORT).show();
                    TempDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context,"Tăng số lượng thất bại",Toast.LENGTH_SHORT).show();
                TempDialog.dismiss();
            }
        });
    }

    private int GetSLKho(int maLaptop){
        for(Laptop lt : listLaptop){
            if(lt.getMaLaptop() == maLaptop){
                return lt.getSoLuong();
            }
        }
        return 0;
    }

}

package com.example.laptop_gearx.QuanTriVien.QuanLyDonDatHang.ChucNang_ThanhToanHoaDon;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop_gearx.Models.ChiTietDonDatHang;
import com.example.laptop_gearx.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_ThanhToanHoaDon_ItemsLaptop extends BaseAdapter {
    Activity context;
    ArrayList<ChiTietDonDatHang> list;

    public Adapter_ThanhToanHoaDon_ItemsLaptop(Activity context, ArrayList<ChiTietDonDatHang> list) {
        this.context = context;
        this.list = list;
    }

    public Adapter_ThanhToanHoaDon_ItemsLaptop() {
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
        View row = inflater.inflate(R.layout.row_chitiet_thanhtoan, null);


        ImageView img = row.findViewById(R.id.rowCTTT_img);
        TextView tvTenLaptop = row.findViewById(R.id.rowCTTT_tvTenLaptop);
        TextView tvGiaBan = row.findViewById(R.id.rowCTTT_tvGiaBan);
        TextView tvSoLuong = row.findViewById(R.id.rowCTTT_tvSL);
        TextView tvThanhTien = row.findViewById(R.id.rowCTTT_tvThanhTien);

        ChiTietDonDatHang ct = list.get(position);
        if(ct.getLinkAnh()==""){
            img.setImageResource(R.drawable.ic_laptop);
        }else{
            Picasso.get()
                    .load(ct.getLinkAnh().toString().trim())
                    .into(img);
        }

        tvTenLaptop.setText(ct.getTenLaptop());

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String gia = "₫" +formatter.format(ct.getGiaBan());
        tvGiaBan.setText(gia);

        tvSoLuong.setText(ct.getSoLuong()+"");

        DecimalFormat formatter2 = new DecimalFormat("###,###,###");
        int ttien = ct.getGiaBan()*ct.getSoLuong();
        String thanhtien =  formatter2.format(ttien)+" VNĐ";
        tvThanhTien.setText(thanhtien);


        return row;
    }
}

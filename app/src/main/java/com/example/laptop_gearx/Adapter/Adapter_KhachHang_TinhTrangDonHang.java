package com.example.laptop_gearx.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.laptop_gearx.Models.DonDatHang;
import com.example.laptop_gearx.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Adapter_KhachHang_TinhTrangDonHang extends BaseAdapter {
    Activity context;
    ArrayList<DonDatHang> list;

    public Adapter_KhachHang_TinhTrangDonHang() {
    }

    public Adapter_KhachHang_TinhTrangDonHang(Activity context, ArrayList<DonDatHang> list) {
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
        View row = inflater.inflate(R.layout.row_tinhtrang_donhang, null);

        TextView tvmaDH = row.findViewById(R.id.rowTTDonDatHang_tvMaDon);
        TextView tvTongTien = row.findViewById(R.id.rowTTDonDatHang_tvTongTien);
        TextView tvNgayLay = row.findViewById(R.id.rowTTDonDatHang_tvNgayLay);
        TextView tvTinhTrang = row.findViewById(R.id.rowTTDonDatHang_tvTinhTrang);

        DonDatHang ddh = list.get(position);

        tvmaDH.setText("Mã đơn : "+ddh.getMaDonDatHang());

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String gia = "₫" +formatter.format(ddh.getTongTien());
        tvTongTien.setText("Tổng tiền : "+gia);

        tvNgayLay.setText("Ngày hẹn lấy : "+ddh.getNgayHenLay());

        String tinhtrang = ddh.getTrangThai();
        switch (tinhtrang){
            case "dangxacnhan":
                tvTinhTrang.setText("CHỜ XÁC NHẬN");
                break;
            case "xacnhan":
                tvTinhTrang.setText("ĐÃ XÁC NHẬN");
                break;
            case "thanhtoan":
                tvTinhTrang.setText("ĐÃ THANH TOÁN");
                tvNgayLay.setVisibility(View.GONE);
                break;
            case "huy":
                tvTinhTrang.setText("ĐƠN BỊ HỦY");
                tvNgayLay.setVisibility(View.GONE);
                break;
        }



        return row;
    }
}

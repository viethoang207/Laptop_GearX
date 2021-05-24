package com.example.laptop_gearx.KhachHang.ChucNang_XemTinhTrangDonHang;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop_gearx.Models.ChiTietDonDatHang;
import com.example.laptop_gearx.Models.ChiTietGioHang;
import com.example.laptop_gearx.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_XemChiTietDatHang extends BaseAdapter {
    Activity context;
    ArrayList<ChiTietDonDatHang> list;

    public Adapter_XemChiTietDatHang() {
    }

    public Adapter_XemChiTietDatHang(Activity context, ArrayList<ChiTietDonDatHang> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
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
        View row = inflater.inflate(R.layout.row_chitietdathang, null);

        ImageView img = row.findViewById(R.id.rowCTDH_img);
        TextView tvTenLaptop = row.findViewById(R.id.rowCTDH_tvTenLaptop);
        TextView tvGia = row.findViewById(R.id.rowCTDH_tvGiaBan);

        TextView tvSL = row.findViewById(R.id.rowCTDH_tvSL);

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
        tvGia.setText(gia);

        tvSL.setText(ct.getSoLuong()+"");

        return row;
    }
}

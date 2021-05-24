package com.example.laptop_gearx.KhachHang.ChucNang_DatHangTuGioHang;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop_gearx.Models.ChiTietGioHang;
import com.example.laptop_gearx.Models.Laptop;
import com.example.laptop_gearx.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Adapter_ChiTietDatHang extends BaseAdapter {
    Activity context;
    ArrayList<ChiTietGioHang> list;

    List<Laptop> listLaptop = new ArrayList<>();


    public Adapter_ChiTietDatHang() {
    }

    public Adapter_ChiTietDatHang(Activity context, ArrayList<ChiTietGioHang> list) {
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
        View row = inflater.inflate(R.layout.row_chitietdathang, null);

        ImageView img = row.findViewById(R.id.rowCTDH_img);
        TextView tvTenLaptop = row.findViewById(R.id.rowCTDH_tvTenLaptop);
        TextView tvGia = row.findViewById(R.id.rowCTDH_tvGiaBan);

        TextView tvSL = row.findViewById(R.id.rowCTDH_tvSL);

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

        return row;
    }
}

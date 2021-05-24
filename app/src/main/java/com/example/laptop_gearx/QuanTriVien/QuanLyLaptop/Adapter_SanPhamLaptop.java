package com.example.laptop_gearx.QuanTriVien.QuanLyLaptop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop_gearx.Models.Laptop;
import com.example.laptop_gearx.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_SanPhamLaptop extends BaseAdapter {
    Activity context;
    ArrayList<Laptop> list;

    public Adapter_SanPhamLaptop() {
    }

    public Adapter_SanPhamLaptop(Activity context, ArrayList<Laptop> list) {
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
        View row = inflater.inflate(R.layout.row_laptop, null);

        ImageView img = row.findViewById(R.id.rowLaptop_imgSP);
        TextView tvTenSP = row.findViewById(R.id.rowLaptop_tvTenSP);
        TextView tvSL = row.findViewById(R.id.rowLaptop_tvSL);



        TextView tvGiaBan = row.findViewById(R.id.rowLaptop_tvGiaBan);

        Laptop lt = list.get(position);

        if(lt.getLinkAnh()==""){
            img.setImageResource(R.drawable.ic_laptop);
        }else{
            Picasso.get()
                    .load(lt.getLinkAnh().toString().trim())
                    .into(img);
        }

        tvTenSP.setText(lt.getTenLaptop());

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String gia = "₫ "+formatter.format(lt.getGiaBan());

        tvGiaBan.setText(gia);
        tvSL.setText(lt.getSoLuong()+"");


        return row;
    }
}

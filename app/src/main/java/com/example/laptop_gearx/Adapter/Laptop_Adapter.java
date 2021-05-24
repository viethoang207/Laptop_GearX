package com.example.laptop_gearx.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_gearx.DangNhap.Activity_DangNhap;
import com.example.laptop_gearx.KhachHang.Activity_KhachHang_XemCTSP;
import com.example.laptop_gearx.Models.Laptop;
import com.example.laptop_gearx.OnLaptopClickListener;
import com.example.laptop_gearx.QuanTriVien.QuanLyLaptop.Activity_XemChiTietLaptop;
import com.example.laptop_gearx.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class Laptop_Adapter extends RecyclerView.Adapter<Laptop_Adapter.LaptopViewHolder>{
    private OnLaptopClickListener listener;
    private List<Laptop> mLaptop;
    private Context context;

    public void setData(List<Laptop> list){
        this.mLaptop = list;
        notifyDataSetChanged();
    }

    //them
    public Laptop_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LaptopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laptop,parent,false);

        return new LaptopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaptopViewHolder holder, int position) {
        Laptop laptop = mLaptop.get(position);

        if(laptop==null){
            return;
        }else{
            if(laptop.getLinkAnh()==""){
                holder.img.setImageResource(R.drawable.ic_laptop);
            }else{
                Picasso.get()
                        .load(laptop.getLinkAnh().toString().trim())
                        .into(holder.img);
            }

            if(laptop.getTenLaptop().length()>40){
                String cut = laptop.getTenLaptop().substring(0,39);
                holder.tv.setText(cut+"...");
            }else{
                holder.tv.setText(laptop.getTenLaptop());
            }
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            String gia = formatter.format(laptop.getGiaBan())+" đ";
            holder.tvGia.setText(gia);


            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,"Click : "+laptop.getMaLaptop(),Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(context,Activity_KhachHang_XemCTSP.class);
                    it.putExtra("maLaptopClick",String.valueOf(laptop.getMaLaptop()));
                    context.startActivity(it);


                    //context.startActivity(new Intent(context, Activity_KhachHang_XemCTSP.class));

                }
            });


        }
    }

    @Override
    public int getItemCount() {
        if(mLaptop != null){
            return mLaptop.size();
        }
        return 0;
    }




    public class LaptopViewHolder extends RecyclerView.ViewHolder{



        private ImageView img;
        private TextView tv,tvGia;

        public LaptopViewHolder(@NonNull View itemView) {


            super(itemView);

            img = itemView.findViewById(R.id.itemLaptop_img);
            tv = itemView.findViewById(R.id.itemLaptop_tv);
            tvGia = itemView.findViewById(R.id.itemLaptop_tvGia);


        }
    }
}
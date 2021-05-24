package com.example.laptop_gearx.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptop_gearx.OnLaptopClickListener;
import com.example.laptop_gearx.R;

import java.util.List;

public class Adapter_ObjNhomThuongHieu extends RecyclerView.Adapter<Adapter_ObjNhomThuongHieu.ObjNhomThuongHieuViewHolder> {
    private Context context;
    private List<Obj_NhomThuongHieu> mListObj_NhomThuongHieu;

    public Adapter_ObjNhomThuongHieu(Context context) {
        this.context = context;
    }

    public void setData(List<Obj_NhomThuongHieu> list){
        this.mListObj_NhomThuongHieu = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ObjNhomThuongHieuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhom_thuonghieu,parent, false);
        return new ObjNhomThuongHieuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjNhomThuongHieuViewHolder holder, int position) {
        Obj_NhomThuongHieu  obj_nhomThuongHieu = mListObj_NhomThuongHieu.get(position);
        if(obj_nhomThuongHieu==null){
            return;
        }
        holder.tv.setText(obj_nhomThuongHieu.getNameNhomThuongHieu());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.rcvLaptop.setLayoutManager(linearLayoutManager);

        Laptop_Adapter laptop_adapter = new Laptop_Adapter(context);
        laptop_adapter.setData(obj_nhomThuongHieu.getLaptops());
        holder.rcvLaptop.setAdapter(laptop_adapter);


    }

    @Override
    public int getItemCount() {

        return mListObj_NhomThuongHieu.size();
    }

    public class ObjNhomThuongHieuViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        private RecyclerView rcvLaptop;

        public ObjNhomThuongHieuViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv_name_nhomThuongHieu);
            rcvLaptop = itemView.findViewById(R.id.rcv_laptop);
        }
    }
}

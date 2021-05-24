package com.example.laptop_gearx.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laptop_gearx.Models.ChiTietDonDatHang;
import com.example.laptop_gearx.Models.DonDatHang;
import com.example.laptop_gearx.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ExpandableListViewDonDatHang_Adapter extends BaseExpandableListAdapter {

    private List<DonDatHang> mListDonDatHang;
    private Map<DonDatHang, List<ChiTietDonDatHang>> mListCTDonDatHang;


    public ExpandableListViewDonDatHang_Adapter(List<DonDatHang> mListDonDatHang, Map<DonDatHang, List<ChiTietDonDatHang>> mListCTDonDatHang) {
        this.mListDonDatHang = mListDonDatHang;
        this.mListCTDonDatHang = mListCTDonDatHang;
    }



    @Override
    public int getGroupCount() {
        if(mListDonDatHang != null){
            return mListDonDatHang.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(mListDonDatHang != null && mListCTDonDatHang != null){
            return mListCTDonDatHang.get(mListDonDatHang.get(groupPosition)).size();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListDonDatHang.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListCTDonDatHang.get(mListDonDatHang.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dondathang, parent, false);
        }


        TextView tvMaDon = convertView.findViewById(R.id.rowDonDatHang_tvMaDon);
        TextView tvHoTen = convertView.findViewById(R.id.rowDonDatHang_tvHoTenNguoiDat);
        TextView tvNgayLay = convertView.findViewById(R.id.rowDonDatHang_tvNgayLay);

        DonDatHang ddh = mListDonDatHang.get(groupPosition);

        tvMaDon.setText("Mã đơn : "+ddh.getMaDonDatHang());
        tvHoTen.setText("Người đặt : "+ddh.getHoTen());
        tvNgayLay.setText("Ngày lấy : "+ddh.getNgayHenLay());


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chitietdathang, parent, false);
        }


        ImageView img = convertView.findViewById(R.id.rowCTDH_img);

        TextView tvTenLaptop = convertView.findViewById(R.id.rowCTDH_tvTenLaptop);
        TextView tvGiaBan = convertView.findViewById(R.id.rowCTDH_tvGiaBan);
        TextView tvSoLuong = convertView.findViewById(R.id.rowCTDH_tvSL);

        ChiTietDonDatHang ct = mListCTDonDatHang.get(mListDonDatHang.get(groupPosition)).get(childPosition);

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

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
}

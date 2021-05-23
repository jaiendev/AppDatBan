package com.trungdang.appdatban.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trungdang.appdatban.R;

import java.util.List;

public class DatBanAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Datbanhome> datbanhomes;

    public DatBanAdapter(Context context, int layout, List<Datbanhome> datbanhomes) {
        this.context = context;
        this.layout = layout;
        this.datbanhomes = datbanhomes;
    }

    @Override
    public int getCount() {
        return datbanhomes.size();
    }
    public class ViewHorder{
        TextView Soban,Mota,Giatien,Theloai;
        ImageView imghinhdatban;
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
        ViewHorder viewHorder;
        if(convertView==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(layout,null);
            viewHorder=new ViewHorder();
            viewHorder.Soban= convertView.findViewById(R.id.txtsoban);
            viewHorder.Mota=convertView.findViewById(R.id.txtmota);
            viewHorder.imghinhdatban=convertView.findViewById(R.id.imghinhdatban);
            viewHorder.Giatien=convertView.findViewById(R.id.txtgiatien);
            viewHorder.Theloai=convertView.findViewById(R.id.txtthethuc);
            convertView.setTag(viewHorder);
        }
        else{
            viewHorder= (ViewHorder) convertView.getTag();
        }


        Datbanhome datbanhome= datbanhomes.get(position);
        viewHorder.Soban.setText(datbanhome.getSoban());
        viewHorder.Mota.setText(datbanhome.getMota());
        Glide.with(context)
                .load(datbanhome.getHinhdatban())
                .into(viewHorder.imghinhdatban);
        viewHorder.Giatien.setText("Giá chung: "+datbanhome.getGiatien());
        viewHorder.Theloai.setText(datbanhome.getTheloai());
        return convertView;
    }
}

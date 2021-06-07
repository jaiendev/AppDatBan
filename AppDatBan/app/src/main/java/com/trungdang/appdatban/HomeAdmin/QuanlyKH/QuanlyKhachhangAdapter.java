package com.trungdang.appdatban.HomeAdmin.QuanlyKH;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trungdang.appdatban.R;

import java.util.List;

public class QuanlyKhachhangAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Dongquanlykhachhang> dongquanlykhachhangs;

    public QuanlyKhachhangAdapter(Context context, int layout, List<Dongquanlykhachhang> dongquanlykhachhangs) {
        this.context = context;
        this.layout = layout;
        this.dongquanlykhachhangs = dongquanlykhachhangs;
    }

    @Override
    public int getCount() {
        return dongquanlykhachhangs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHorder{
        TextView Ten,Email;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHorder viewHorder;
        if(convertView==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(layout,null);
            viewHorder=new ViewHorder();
            viewHorder.Ten=convertView.findViewById(R.id.Tenkhachhangdong);
            viewHorder.Email=convertView.findViewById(R.id.Emailkhachhangdong);
            convertView.setTag(viewHorder);
        }
        else{
            viewHorder= (ViewHorder) convertView.getTag();
        }


        Dongquanlykhachhang dongquanlykhachhang= dongquanlykhachhangs.get(position);
        viewHorder.Ten.setText(dongquanlykhachhang.getTen());
        viewHorder.Email.setText(dongquanlykhachhang.getEmail());
        return convertView;
    }
}

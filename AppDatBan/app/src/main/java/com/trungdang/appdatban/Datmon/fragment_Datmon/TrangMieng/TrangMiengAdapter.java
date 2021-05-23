package com.trungdang.appdatban.Datmon.fragment_Datmon.TrangMieng;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.DongMonChinh;
import com.trungdang.appdatban.R;

import java.util.List;

public class TrangMiengAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DongMonChinh> dongMonTrangMieng;
    int tem=0;

    public TrangMiengAdapter(Context context, int layout, List<DongMonChinh> dongMonTrangMieng) {
        this.context = context;
        this.layout = layout;
        this.dongMonTrangMieng = dongMonTrangMieng;
    }

    @Override
    public int getCount() {
        return dongMonTrangMieng.size();
    }

    @Override
    public DongMonChinh getItem(int position) {
        return dongMonTrangMieng.get(position);
    }

    public class ViewHorder{
        TextView Tenmon,Soluong,Giatienmon,Loaimon;
        ImageView imghinhmon,imgAdd,imgSubtract;
        Button btnDatMon;
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
            viewHorder.Tenmon= convertView.findViewById(R.id.txtTenmonchinh);
            viewHorder.Soluong=convertView.findViewById(R.id.txtsoluongmonchinh);
            viewHorder.imghinhmon=convertView.findViewById(R.id.imghinhmonchinh);
            viewHorder.Giatienmon=convertView.findViewById(R.id.txtgiatienmonchinh);
            viewHorder.Loaimon=convertView.findViewById(R.id.txtmonchinhmacdinh);
            viewHorder.imgAdd=convertView.findViewById(R.id.imgAdd);
            viewHorder.imgSubtract=convertView.findViewById(R.id.imgSubtract);
            viewHorder.btnDatMon=convertView.findViewById(R.id.btnDatmonHomeDatmon);

            convertView.setTag(viewHorder);
        }
        else{
            viewHorder= (ViewHorder) convertView.getTag();
        }


        DongMonChinh dongMonChinh= dongMonTrangMieng.get(position);
        viewHorder.Tenmon.setText(dongMonChinh.getTenmonchinh());
        //viewHorder.Soluong.setText(dongMonChinh.getSoluongdat());
        Glide.with(context)
                .load(dongMonChinh.getHinhmonchinh())
                .into(viewHorder.imghinhmon);
        viewHorder.Giatienmon.setText(dongMonChinh.getGiamon());
        viewHorder.Loaimon.setText(dongMonChinh.getLoaimon());
        viewHorder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(position,viewHorder.Soluong,1);
            }
        });
        viewHorder.imgSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubtractQuantity(position,viewHorder.Soluong,1);
            }
        });
        viewHorder.btnDatMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tem==0)
                {
                    viewHorder.btnDatMon.setText("Đã đặt");
                    viewHorder.btnDatMon.setTextColor(Color.parseColor("#26FF00"));
                    tem=1;
                }
                else{
                    viewHorder.btnDatMon.setText("Đặt");
                    viewHorder.btnDatMon.setTextColor(Color.parseColor("#FF000000"));
                    tem=0;
                }
            }
        });
        return convertView;
    }
    private void updateQuantity(int position, TextView edTextQuantity, int value) {
        DongMonChinh dongMonChinh= getItem(position);
        if(value > 0)
        {
            dongMonChinh.soluong=dongMonChinh.soluong+1;
        }
        else
        {
            if(dongMonChinh.soluong>0)
            {
                dongMonChinh.soluong=dongMonChinh.soluong+1;
            }

        }
        edTextQuantity.setText(dongMonChinh.soluong+"");
    }
    private void SubtractQuantity(int position, TextView edTextQuantity, int value) {
        DongMonChinh dongMonChinh= getItem(position);
        if(value > 0)
        {
            if(dongMonChinh.soluong>=0)
            {
                dongMonChinh.soluong=dongMonChinh.soluong-1;
            }
            dongMonChinh.soluong=0;
        }
        else
        {
            if(dongMonChinh.soluong>0)
            {
                dongMonChinh.soluong=dongMonChinh.soluong-1;
            }

        }
        edTextQuantity.setText(dongMonChinh.soluong+"");
    }
}

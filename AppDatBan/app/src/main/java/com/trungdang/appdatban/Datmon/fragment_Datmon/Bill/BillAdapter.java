package com.trungdang.appdatban.Datmon.fragment_Datmon.Bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trungdang.appdatban.R;

import java.util.List;

public class BillAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DongBill> dongBillList;
    int tem=0;

    public BillAdapter(Context context, int layout, List<DongBill> dongBillList) {
        this.context = context;
        this.layout = layout;
        this.dongBillList = dongBillList;
    }

    @Override
    public int getCount() {
        return dongBillList.size();
    }

    @Override
    public DongBill getItem(int position) {
        return dongBillList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHorder{
        TextView Tenmon,Soluong,Giatienmon;
        ImageView imgAdd,imgSubtract;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHorder viewHorder;
        if(convertView==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(layout,null);
            viewHorder=new ViewHorder();
            viewHorder.Tenmon= convertView.findViewById(R.id.txttenmonBill);
            viewHorder.Soluong=convertView.findViewById(R.id.txtsoluongBill);
            viewHorder.Giatienmon=convertView.findViewById(R.id.txttienmonbill);
            viewHorder.imgAdd=convertView.findViewById(R.id.imgAddBill);
            viewHorder.imgSubtract=convertView.findViewById(R.id.imgSubtractBill);
            convertView.setTag(viewHorder);
        }
        else{
            viewHorder= (ViewHorder) convertView.getTag();
        }


        DongBill dongBill= dongBillList.get(position);
        viewHorder.Tenmon.setText(dongBill.getTenmon());
        viewHorder.Giatienmon.setText(dongBill.getGiatien());
        viewHorder.Soluong.setText(dongBill.getSoluong());
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

        return convertView;
    }

    private void updateQuantity(int position, TextView edTextQuantity, int value) {
        DongBill dongBill=getItem(position);
        if(value > 0)
        {
            dongBill.soluong=dongBill.soluong+1;
        }
        else
        {
            if(dongBill.soluong>0)
            {
                dongBill.soluong=dongBill.soluong+1;
            }

        }
        edTextQuantity.setText(dongBill.soluong+"");
    }
    private void SubtractQuantity(int position, TextView edTextQuantity, int value) {
        DongBill dongBill=getItem(position);
        if(value > 0)
        {
            dongBill.soluong=dongBill.soluong-1;
            if(dongBill.soluong<0)
            {

                dongBill.soluong=0;
            }
        }
        else
        {
            if(dongBill.soluong>0)
            {
                dongBill.soluong=dongBill.soluong-1;
            }

        }
        edTextQuantity.setText(dongBill.soluong+"");
    }
}

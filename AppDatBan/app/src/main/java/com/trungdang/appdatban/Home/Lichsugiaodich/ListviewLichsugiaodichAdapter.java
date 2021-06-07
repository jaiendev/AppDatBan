package com.trungdang.appdatban.Home.Lichsugiaodich;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trungdang.appdatban.R;

import java.util.List;

public class ListviewLichsugiaodichAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<dongLichsugiaodich> dongLichsugiaodichList;

    public ListviewLichsugiaodichAdapter(Context context, int layout, List<dongLichsugiaodich> dongLichsugiaodichList) {
        this.context = context;
        this.layout = layout;
        this.dongLichsugiaodichList = dongLichsugiaodichList;
    }

    @Override

    public int getCount() {
        return dongLichsugiaodichList.size();
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
        TextView Madonhang,Ngaydat,Tongtien;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHorder viewHorder;
        if(convertView==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(layout,null);
            viewHorder=new ViewHorder();
            viewHorder.Madonhang= convertView.findViewById(R.id.Madon);
            viewHorder.Ngaydat=convertView.findViewById(R.id.Ngaydat);
            viewHorder.Tongtien=convertView.findViewById(R.id.Tongtien);

            convertView.setTag(viewHorder);
        }
        else{
            viewHorder= (ViewHorder) convertView.getTag();
        }


       dongLichsugiaodich dongLichsugiaodich= dongLichsugiaodichList.get(position);
        viewHorder.Madonhang.setText(dongLichsugiaodich.getMadonhang());
        viewHorder.Ngaydat.setText(dongLichsugiaodich.getNgaydat());
        viewHorder.Tongtien.setText(dongLichsugiaodich.getTongtien());
        return convertView;
    }
}

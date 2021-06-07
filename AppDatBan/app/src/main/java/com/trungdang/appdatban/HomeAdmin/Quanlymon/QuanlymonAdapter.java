package com.trungdang.appdatban.HomeAdmin.Quanlymon;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.DongMonChinh;
import com.trungdang.appdatban.R;

import java.util.ArrayList;

public class QuanlymonAdapter extends RecyclerView.Adapter<QuanlymonAdapter.ViewHolder> {
    ArrayList<DongMonChinh> dongMonChinhArrayList;
    Context context;
    ViewBinderHelper viewBinderHelper=new ViewBinderHelper();

    public QuanlymonAdapter(ArrayList<DongMonChinh> dongMonChinhArrayList, Context context) {
        this.dongMonChinhArrayList = dongMonChinhArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public QuanlymonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.dong_quan_li_mon,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanlymonAdapter.ViewHolder holder, int position) {
        holder.Tenmon.setText(dongMonChinhArrayList.get(position).getTenmonchinh());
        holder.Loaimon.setText(dongMonChinhArrayList.get(position).getLoaimon());
        holder.Giatienmon.setText("Giá: "+dongMonChinhArrayList.get(position).getGiamon() + "VNĐ");
        Glide.with(context)
                .load(dongMonChinhArrayList.get(position).getHinhmonchinh())
                .into(holder.imghinhmon);
        viewBinderHelper.bind(holder.swipeRevealLayout,dongMonChinhArrayList.get(position).getIdMonchinh());
        holder.relativeLayoutphu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection("Eaten").document(dongMonChinhArrayList.get(position).getIdMonchinh())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error deleting document", e);
                            }
                        });
                dongMonChinhArrayList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dongMonChinhArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Tenmon,Giatienmon,Loaimon,Donvi;
        ImageView imghinhmon,imgthungrac;
        RelativeLayout relativeLayoutphu;
        SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tenmon=itemView.findViewById(R.id.txtTenmonquanlymon);
            Giatienmon=itemView.findViewById(R.id.txtgiatienmonquanlymon);
            Loaimon=itemView.findViewById(R.id.txtloaimonquanlymon);
            Donvi=itemView.findViewById(R.id.txtdonviquanlymon);
            relativeLayoutphu=itemView.findViewById(R.id.relativeLayoutphuquanlymon);
            imgthungrac=itemView.findViewById(R.id.imgthungracquanlymon);
            imghinhmon=itemView.findViewById(R.id.imghinhquanlymon);
            swipeRevealLayout=itemView.findViewById(R.id.swiperevealLayoutquanlymon);
        }
    }
}

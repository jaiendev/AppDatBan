package com.trungdang.appdatban.HomeAdmin.Quanlyban;

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
import com.trungdang.appdatban.R;

import java.util.ArrayList;

public class QuanlybanAdapter extends RecyclerView.Adapter<QuanlybanAdapter.ViewHolder> {
    ArrayList<Dongquanlyban> dongquanlybans;
    Context context;
    ViewBinderHelper viewBinderHelper=new ViewBinderHelper();

    public QuanlybanAdapter(ArrayList<Dongquanlyban> dongquanlybans, Context context) {
        this.dongquanlybans = dongquanlybans;
        this.context = context;
    }

    @NonNull
    @Override
    public QuanlybanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemview= inflater.inflate(R.layout.dong_quanly,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanlybanAdapter.ViewHolder holder, int position) {
        holder.Tenban.setText(dongquanlybans.get(position).getSoban());
        holder.Soluong.setText(dongquanlybans.get(position).getMota());
        holder.Gia.setText("Giá: " +dongquanlybans.get(position).getGiatien()+ " VNĐ/3 giờ");
        holder.Loai.setText(dongquanlybans.get(position).getTheloai());
        Glide.with(context)
                .load(dongquanlybans.get(position).getHinhdatban())
                .into(holder.imgHinhban);
        viewBinderHelper.bind(holder.swipeRevealLayout,dongquanlybans.get(position).getIdban());
        holder.relativeLayoutphu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore db=FirebaseFirestore.getInstance();
                db.collection("Products").document(dongquanlybans.get(position).getIdban())
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
                dongquanlybans.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dongquanlybans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Tenban,Soluong,Gia,Loai;
        ImageView imgHinhban,imgthungrac;
        RelativeLayout relativeLayout,relativeLayoutphu;
        SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Tenban=itemView.findViewById(R.id.txtsobanAdmin);
            Soluong=itemView.findViewById(R.id.txtmotaAdmin);
            Gia=itemView.findViewById(R.id.txtgiatienAdmin);
            Loai=itemView.findViewById(R.id.txtthethucAdmin);
            imgHinhban=itemView.findViewById(R.id.imghinhdatbanAdmin);
            relativeLayout=itemView.findViewById(R.id.relativechinh);
            relativeLayoutphu=itemView.findViewById(R.id.relativeLayoutphu);
            imgthungrac=itemView.findViewById(R.id.imgthungrac);
            swipeRevealLayout=itemView.findViewById(R.id.swiperevealLayout);
        }
    }
}

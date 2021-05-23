package com.trungdang.appdatban.Datban;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.trungdang.appdatban.Datmon.Home_Datmon;
import com.trungdang.appdatban.Home.HomeFragment;
import com.trungdang.appdatban.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Datban extends AppCompatActivity {
    TextView txttenban,txtthoigian,txtsoluong,txtloai,txttenkhachhang;
    ImageView imghinhban,imgback;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference storageRef;
    String idban;
    String IDUser;
    Button btnDatmon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datban);
        Intent intent=getIntent();
        String tenban=intent.getStringExtra("soban");
        String loai=intent.getStringExtra("theloai");
        String mota=intent.getStringExtra("mota");

        idban=intent.getStringExtra("id");
        Anhxa();
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");//formating according to my need
        String date = formatter.format(today);
        txtthoigian.setText("Thời gian: "+date);
        txttenban.setText(tenban);
        txtsoluong.setText("Chứa: "+mota);
        txtloai.setText("Loại: "+loai);



        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeFragment.class));
                finish();
            }
        });
        btnDatmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home_Datmon.class));
            }
        });
    }
    private void Anhxa(){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStorage = FirebaseStorage.getInstance();
        storageRef = fStorage.getReference();
        btnDatmon=findViewById(R.id.btnDatmon);
        txttenban=findViewById(R.id.txtban);
        txtthoigian=findViewById(R.id.txtthoigian);
        txtloai=findViewById(R.id.txtLoai);
        txtsoluong=findViewById(R.id.txtsoluong);
        imghinhban=findViewById(R.id.imghinhban);
        txttenkhachhang=findViewById(R.id.txtTenKhachHang);
        imgback=findViewById(R.id.imgback);
        IDUser=fAuth.getCurrentUser().getUid();
        LoadProduct();
        LoadNameUser();
    }
    private void LoadNameUser(){
        DocumentReference documentReference = fStore.collection("Users").document(IDUser);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String name = document.getString("ten");
                        String phone=document.getString("sodienthoai");
                        txttenkhachhang.setText(name + " - " +phone );
                    }
                    else {
                        Log.d("TAG", "No such document");
                    }
                } else
                {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }
    private void LoadProduct() {
        DocumentReference docRef = fStore.collection("Products").document(idban);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        String img       =  document.getString("Hinh");
                        Glide.with(getApplicationContext())
                                .load(img)
                                .into(imghinhban);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }
}
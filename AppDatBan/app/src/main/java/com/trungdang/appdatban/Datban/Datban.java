package com.trungdang.appdatban.Datban;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

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
import com.trungdang.appdatban.Home.Home;
import com.trungdang.appdatban.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Datban extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private int  mYear, mMonth, mDay,mHour, mMinute;
    public static String thoigiandat;
    TextView txttenban,txtthoigian,txtsoluong,txtloai,txttenkhachhang;
    ImageView imghinhban,imgback;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference storageRef;
    String idban;
    String IDUser;
    Button btnDatmon;
    TextView txtsua;
    String dates,times;
    public static String idbans;
    public static String Loaibando;
    public static int giaban;
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
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm - dd/MM/yyyy");//formating according to my need
        String date = formatter.format(today);
        txtthoigian.setText("Thời gian: "+date);
        txttenban.setText(tenban);
        txtsoluong.setText("Chứa: "+mota);
        txtloai.setText("Loại: "+loai);



        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(Datban.this, Home.class);
               startActivity(intent);
            }
        });
        btnDatmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home_Datmon.class));
                Loaibando =loai;
                idbans=idban;
                thoigiandat=times+" - "+dates;
            }
        });
        txtsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                if(txtsua.getText().toString().equals("Chọn ngày"))
                {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Datban.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    txtthoigian.setText("Thời gian: "+dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    dates=dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                    txtsua.setText("Chọn giờ");
                }
                else {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(Datban.this,
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    txtthoigian.setText("Thời gian: "+hourOfDay + ":" + minute +" - "+dates);
                                    times=hourOfDay + ":" + minute;
                                }
                            }, mHour, mMinute, true);
                    timePickerDialog.show();
                    txtsua.setText("Chọn ngày");
                }
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
        imgback=findViewById(R.id.imgbackdatban);
        txtsua=findViewById(R.id.txtsua);
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
                        String Gia          =   document.getString("Gia");
                        giaban=Integer.parseInt(Gia);
                        Log.d("trungdeptrai",Gia);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
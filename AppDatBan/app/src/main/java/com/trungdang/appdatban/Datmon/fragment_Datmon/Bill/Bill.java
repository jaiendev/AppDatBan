package com.trungdang.appdatban.Datmon.fragment_Datmon.Bill;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.trungdang.appdatban.Datban.Datban;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.DongMonChinhAdapter;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.Monchinh;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.OnClickItemTab1;
import com.trungdang.appdatban.Home.Home;
import com.trungdang.appdatban.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bill#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bill extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference storageRef;
    FirebaseAuth fAuth;
    ImageView imgbanBill,imgUser;
    TextView txttenban,txtmota,txtgia,txtloai,txttongtienban,txttongtienmon,txtTongTien,txtTenUser,txtThoigiandat;
    ListView lstBill;
    BillAdapter adapter;
    ArrayList<DongBill> dongBillArrayList;
    OnClickItemTab1 onClickItemTab1;
    String IDUser;
    Button btnXacNhan;
    Monchinh monchinh;
    DongMonChinhAdapter dongMonChinhAdapter;

    public Bill() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bill.
     */
    // TODO: Rename and change types and number of parameters
    public static Bill newInstance(String param1, String param2) {
        Bill fragment = new Bill();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public OnClickItemTab1 getOnClickItemTab1() {
        return onClickItemTab1;
    }
    public static Bill getInstance(){
        return new Bill();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bill, container, false);
        AnhXa(view);
        return view;
    }
    private void AnhXa(View view){
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        fStorage        = FirebaseStorage.getInstance();
        storageRef      = fStorage.getReference();
        IDUser=fAuth.getCurrentUser().getUid();
        imgbanBill=view.findViewById(R.id.imghinhBill);
        txtThoigiandat=view.findViewById(R.id.txtThoigiandat);
        imgUser=view.findViewById(R.id.imgHomeCustomerBill);
        txtTenUser=view.findViewById(R.id.txtTenUserBill);
        txttenban=view.findViewById(R.id.txtsobanBill);
        txtmota=view.findViewById(R.id.txtmotaBill);
        txtgia=view.findViewById(R.id.txtgiatienbill);
        txtloai=view.findViewById(R.id.txtloaibill);
        txttongtienban=view.findViewById(R.id.txtTongtienban);
        txttongtienmon=view.findViewById(R.id.txtTongtienmon);
        txtTongTien=view.findViewById(R.id.txtTongtien);
        lstBill=view.findViewById(R.id.lstbill);
        dongBillArrayList=new ArrayList<>();
        adapter=new BillAdapter(getActivity(),R.layout.dong_bill,dongBillArrayList);
        getNameUser();
        lstBill.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getIDban();
        GetProducts();
        txttongtienmon.setText(DongMonChinhAdapter.tonggiamon+" VNĐ");
        int gia=DongMonChinhAdapter.tonggiaban+DongMonChinhAdapter.tonggiamon;
        txtTongTien.setText(gia+" VNĐ");
        txtThoigiandat.setText(Datban.thoigiandat);
        btnXacNhan=view.findViewById(R.id.btnXacNhan);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct();
                startActivity(new Intent(getActivity(), Home.class));
                DongMonChinhAdapter.Monchinhdachon.clear();
                DongMonChinhAdapter.TrangMiengdachon.clear();
                DongMonChinhAdapter.Giaikhatdachon.clear();
                DongMonChinhAdapter.idMons.clear();
                DongMonChinhAdapter.soluongdat.clear();
                DongMonChinhAdapter.tonggiaban=0;
                DongMonChinhAdapter.tonggiamon=0;
            }
        });

    }
    public void getNameUser(){
        DocumentReference documentReference = fStore.collection("Users").document(IDUser);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String Img  = document.getString("Avatar");
                        String name = document.getString("ten");
                        txtTenUser.setText(name);
                        Glide.with(getActivity())
                                .load(Img)
                                .into(imgUser);
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
    public void getIDban(){
        DocumentReference documentReference = fStore.collection("Products").document(Datban.idbans);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String Tenban  = document.getString("Tenban");
                        String Soluong = document.getString("Soluong");
                        String Loai=document.getString("Loai");
                        String Gia=document.getString("Gia");;
                        String Hinh=document.getString("Hinh");;
                        txttenban.setText(Tenban);
                        txtgia.setText("Giá chung: "+Gia +" VNĐ/3 giờ");

                        txtmota.setText(Soluong);
                        txtloai.setText(Loai);
                        txttongtienban.setText(Gia + " VNĐ");
//                        int a=Integer.parseInt(Gia)+tonggia;
//                        txtTongTien.setText( a+" VNĐ");
                        Glide.with(getActivity())
                                .load(Hinh)
                                .into(imgbanBill);
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
    public void GetProducts() {
        for(int i=0;i<DongMonChinhAdapter.idMons.size();i++)
        {
            DocumentReference documentReference = fStore.collection("Eaten").document(DongMonChinhAdapter.idMons.get(i));
            int finalI = i;
            final Task<DocumentSnapshot> documentSnapshotTask = documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String Tenmon = document.getString("TenMon");
                            String Gia = document.getString("GiaTien");
                            String soluong = DongMonChinhAdapter.soluongdat.get(finalI);
                            dongBillArrayList.add(new DongBill(DongMonChinhAdapter.idMons.get(finalI), Tenmon, Gia, soluong,0));

                        } else {
                            Log.d("TAG", "No such document");
                        }

                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                    adapter.notifyDataSetChanged();
                }

            });
        }


    }
    private void AddProduct() {
        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");//formating according to my need
        String date = formatter.format(today);
        String IdUser         = IDUser;
        String TongTien        = txtTongTien.getText().toString();
        String NgayDat  = date;
        String Loaiban = Datban.Loaibando;
        String idban=Datban.idbans;
       BillConstructor bill=new BillConstructor(idban,Datban.thoigiandat,IdUser,TongTien,Loaiban,DongMonChinhAdapter.Monchinhdachon,DongMonChinhAdapter.TrangMiengdachon, DongMonChinhAdapter.Giaikhatdachon);
        fStore.collection("Bills")
                .add(bill)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }
}
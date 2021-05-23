package com.trungdang.appdatban.Datmon.fragment_Datmon.Bill;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.DongMonChinhAdapter;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.Monchinh;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.OnClickItemTab1;
import com.trungdang.appdatban.R;

import java.util.ArrayList;
import java.util.List;

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
    public static List<String> idMons = new ArrayList<String>();
    public static String idbans;
    public static List<String> soluongdat=new ArrayList<String>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference storageRef;
    FirebaseAuth fAuth;
    ImageView imgbanBill,imgUser;
    TextView txttenban,txtmota,txtgia,txtloai,txttongtienban,txttongtienmon,txtTongTien,txtTenUser;
    ListView lstBill;
    BillAdapter adapter;
    ArrayList<DongBill> dongBillArrayList;
    OnClickItemTab1 onClickItemTab1;
    String IDUser;
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
        Log.d("AAA", soluongdat.toString());
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
        DocumentReference documentReference = fStore.collection("Products").document(idbans);
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
                        txtgia.setText("Giá chung: "+Gia);
                        txtmota.setText(Soluong);
                        txtloai.setText(Loai);
                        txttongtienban.setText(Gia);
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
        for(int i=0;i<idMons.size();i++)
        {
            DocumentReference documentReference = fStore.collection("Eaten").document(idMons.get(i));
            int finalI = i;
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String Tenmon  = document.getString("TenMon");
                            String Gia=document.getString("GiaTien");
                            String soluong=soluongdat.get(finalI);
                            dongBillArrayList.add(new DongBill(idMons.get(finalI),Tenmon,Gia, soluong));
                        }

                        else {
                            Log.d("TAG", "No such document");
                        }
                        adapter.notifyDataSetChanged();
                    } else
                    {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });
        }

    }

}
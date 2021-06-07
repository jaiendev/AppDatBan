package com.trungdang.appdatban.HomeAdmin.QuanlyKH;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.trungdang.appdatban.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuanlyKH#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanlyKH extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView listView;
    QuanlyKhachhangAdapter adapter;
    ArrayList<Dongquanlykhachhang> dongquanlykhachhangs;
    FirebaseFirestore fStore;
    ImageView imgHinhadmin;
    TextView txtTenAdmin;
    String IDUser;
    FirebaseAuth fAuth=FirebaseAuth.getInstance();
    public QuanlyKH() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuanlyKH.
     */
    // TODO: Rename and change types and number of parameters
    public static QuanlyKH newInstance(String param1, String param2) {
        QuanlyKH fragment = new QuanlyKH();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_quanly_k_h, container, false);
        AnhXa(view);
        return view;
    }
    private void AnhXa(View view)
    {
        listView=view.findViewById(R.id.lstkh);
        dongquanlykhachhangs=new ArrayList<>();
        adapter=new QuanlyKhachhangAdapter(getActivity(),R.layout.dong_khach_hang,dongquanlykhachhangs);
        listView.setAdapter(adapter);
        fStore=FirebaseFirestore.getInstance();
        IDUser=fAuth.getCurrentUser().getUid();
        imgHinhadmin=view.findViewById(R.id.imgAdminquanlykhachhang);
        txtTenAdmin=view.findViewById(R.id.txtTenAdminquanlykhachhang);
        getNameUser();
        getKH();
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
                        txtTenAdmin.setText(name);
                        Glide.with(getActivity())
                                .load(Img)
                                .into(imgHinhadmin);
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
    private void getKH(){
        CollectionReference productRefs = fStore.collection("Users");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String Ten=document.getString("ten");
                        String Email=document.getString("email");
                        String loai=document.getString("loai");
                        if(loai.equals("Customer"))
                        {
                            dongquanlykhachhangs.add(new Dongquanlykhachhang(Ten,Email));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","Error");
            }
        });
    }
}
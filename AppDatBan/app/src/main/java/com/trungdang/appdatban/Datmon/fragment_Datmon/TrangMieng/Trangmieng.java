package com.trungdang.appdatban.Datmon.fragment_Datmon.TrangMieng;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.DongMonChinh;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh.DongMonChinhAdapter;
import com.trungdang.appdatban.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Trangmieng#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Trangmieng extends Fragment {

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
    ListView listViewMonTrangMieng;
    DongMonChinhAdapter TrangMiengadapter;
    ArrayList<DongMonChinh> TrangmiengArratlist;
    String IDUser;
    TextView txtTenUser;
    ImageView imageViewHinhUser,imgsearch;
    EditText edtSearch;
    public Trangmieng() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Trangmieng.
     */
    // TODO: Rename and change types and number of parameters
    public static Trangmieng newInstance(String param1, String param2) {
        Trangmieng fragment = new Trangmieng();
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
        View view= inflater.inflate(R.layout.fragment_trangmieng, container, false);
        AnhXa(view);
        return view;
    }
    private void AnhXa(View view){
        listViewMonTrangMieng=view.findViewById(R.id.listviewhomeTrangmieng);
        txtTenUser=view.findViewById(R.id.txtTenUserTrangmieng);
        imageViewHinhUser=view.findViewById(R.id.imgHomeCustomerTrangmieng);
        TrangmiengArratlist=new ArrayList<>();
        TrangMiengadapter=new DongMonChinhAdapter(getActivity(),R.layout.dong_mon_chinh,TrangmiengArratlist);
        listViewMonTrangMieng.setAdapter(TrangMiengadapter);
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        fStorage        = FirebaseStorage.getInstance();
        storageRef      = fStorage.getReference();
        IDUser=fAuth.getCurrentUser().getUid();
        imgsearch=view.findViewById(R.id.imgsearchTrangMiengCustomer);
        edtSearch=view.findViewById(R.id.editsearchTrangmiengCustomer);
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchTrangMieng();
            }
        });
        GetProducts();
        getNameUser();
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
                                .into(imageViewHinhUser);
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
        TrangmiengArratlist.clear();
        CollectionReference productRefs = fStore.collection("Eaten");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id           = document.getId();
                        String tenmon         = (String) document.get("TenMon");
                        String gia       = (String) document.get("GiaTien");
                        String Tenloai  = (String) document.get("TenLoai");
                        String hinh    =  (String) document.get("Hinh");
                        String idloai =(String) document.get("idLoai");
                        if(idloai.equals("2"))
                        {
                            DongMonChinh Mon = new DongMonChinh(idloai,id,hinh,tenmon,"1",gia,Tenloai);
                            TrangmiengArratlist.add(Mon);
                        }

                    }
                    TrangMiengadapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","Error");
            }
        });
    }
    public void SearchTrangMieng() {
        TrangmiengArratlist.clear();
        CollectionReference productRefs = fStore.collection("Eaten");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id           = document.getId();
                        String tenmon         = (String) document.get("TenMon");
                        String gia       = (String) document.get("GiaTien");
                        String Tenloai  = (String) document.get("TenLoai");
                        String hinh    =  (String) document.get("Hinh");
                        String idloai =(String) document.get("idLoai");
                        if(idloai.equals("2"))
                        {
                            if(edtSearch.getText().toString().toLowerCase().equals(tenmon.toLowerCase()))
                            {
                                DongMonChinh Mon = new DongMonChinh(idloai,id,hinh,tenmon,"1",gia,Tenloai);
                                TrangmiengArratlist.add(Mon);
                            }
                            if(edtSearch.getText().toString().equals(""))
                            {
                                DongMonChinh Mon = new DongMonChinh(idloai,id,hinh,tenmon,"1",gia,Tenloai);
                                TrangmiengArratlist.add(Mon);
                            }
                        }

                    }
                    TrangMiengadapter.notifyDataSetChanged();
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
package com.trungdang.appdatban.Datmon.fragment_Datmon.Monchinh;

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
import com.trungdang.appdatban.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Monchinh#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Monchinh extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    OnClickItemTab1 onClickItemTab1;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference storageRef;
    FirebaseAuth fAuth;
    ListView listViewMon;
    DongMonChinhAdapter adapter;
    ArrayList<DongMonChinh> dongMonChinhArrayList;
    String IDUser;
    TextView txtTenUser;
    ImageView imageViewHinhUser,imgsearch;
    EditText edtSearch;

    public Monchinh() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Monchinh.
     */
    // TODO: Rename and change types and number of parameters
    public static Monchinh newInstance(String param1, String param2) {
        Monchinh fragment = new Monchinh();
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
    public void setOnClickItemTab1(OnClickItemTab1 onClickItemTab1) {
        this.onClickItemTab1 = onClickItemTab1;
    }
    public OnClickItemTab1 getOnClickItemTab1() {
        return onClickItemTab1;
    }
    public static Monchinh getInstance(){
        return new Monchinh();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_monchinh, container, false);
        AnhXa(view);
        return view;
    }
    private void AnhXa(View view){
        listViewMon=view.findViewById(R.id.listviewhomemonchinh);
        txtTenUser=view.findViewById(R.id.txtTenUserDatmon);
        imageViewHinhUser=view.findViewById(R.id.imgHomeCustomerDatmon);
        dongMonChinhArrayList=new ArrayList<>();
        adapter=new DongMonChinhAdapter(getActivity(),R.layout.dong_mon_chinh,dongMonChinhArrayList);
        listViewMon.setAdapter(adapter);
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        fStorage        = FirebaseStorage.getInstance();
        storageRef      = fStorage.getReference();
        IDUser=fAuth.getCurrentUser().getUid();
        imgsearch=view.findViewById(R.id.imgsearchmonCustomer);
        edtSearch=view.findViewById(R.id.editsearchmonCustomer);
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMonChinh();
            }
        });
        GetProducts();
        getNameUser();
        getBill();
    }
    public void getBill(){
        List<String> arraybill = null;
        for(int i=0;i<dongMonChinhArrayList.size();i++)
        {
            arraybill.add(dongMonChinhArrayList.get(i).bill);
        }
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
        dongMonChinhArrayList.clear();
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
                        if(idloai.equals("1"))
                        {
                            DongMonChinh Mon = new DongMonChinh(idloai,id,hinh,tenmon,"1",gia,Tenloai);
                            dongMonChinhArrayList.add(Mon);
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
    public void SearchMonChinh() {
        dongMonChinhArrayList.clear();
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
                        if(idloai.equals("1"))
                        {
                            if(edtSearch.getText().toString().toLowerCase().equals(tenmon.toLowerCase()))
                            {
                                DongMonChinh Mon = new DongMonChinh(idloai,id,hinh,tenmon,"1",gia,Tenloai);
                                dongMonChinhArrayList.add(Mon);
                            }
                            if(edtSearch.getText().toString().equals(""))
                            {
                                DongMonChinh Mon = new DongMonChinh(idloai,id,hinh,tenmon,"1",gia,Tenloai);
                                dongMonChinhArrayList.add(Mon);
                            }
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
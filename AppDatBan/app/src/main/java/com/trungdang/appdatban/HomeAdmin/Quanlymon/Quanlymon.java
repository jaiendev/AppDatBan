package com.trungdang.appdatban.HomeAdmin.Quanlymon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.trungdang.appdatban.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Quanlymon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Quanlymon extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    QuanlymonAdapter adapter;
    ArrayList<DongMonChinh> dongMonChinhArrayList;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference storageRef;
    FirebaseAuth fAuth;
    String IDUser;
    TextView txttenuser,btnChonmonAdmin;
    ImageView imgUser,imgSearch,imgadd;
    EditText edtSearch;
    public Quanlymon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Quanlymon.
     */
    // TODO: Rename and change types and number of parameters
    public static Quanlymon newInstance(String param1, String param2) {
        Quanlymon fragment = new Quanlymon();
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
        View view= inflater.inflate(R.layout.fragment_quanlymon, container, false);
        AnhXa(view);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMonChinh();
            }
        });
        imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Addmon.class));
            }
        });
        return view;
    }
    private void AnhXa(View view){
        imgadd=view.findViewById(R.id.imgButtonAddmonAdmin);
        imgSearch=view.findViewById(R.id.imgsearchmonAdmin);
        edtSearch=view.findViewById(R.id.editsearchmonAdmin);
        recyclerView=view.findViewById(R.id.recyclerViewquanlymon);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        dongMonChinhArrayList=new ArrayList<>();
        adapter=new QuanlymonAdapter(dongMonChinhArrayList,getActivity());
        recyclerView.setAdapter(adapter);
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        fStorage        = FirebaseStorage.getInstance();
        storageRef      = fStorage.getReference();
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        IDUser=fAuth.getCurrentUser().getUid();
        txttenuser=view.findViewById(R.id.txtTenUserQuanlymon);
        imgUser=view.findViewById(R.id.imgUserQuanlymon);
        GetProducts();
        getNameUser();
        btnChonmonAdmin=view.findViewById(R.id.btnChonmonAdmin);
        btnChonmonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPopup();
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
                        txttenuser.setText(name);
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
                        String tenmon        = (String) document.get("TenMon");
                        String gia       = (String) document.get("GiaTien");
                        String theloai  = (String) document.get("TenLoai");
                        String hinh    =  (String) document.get("Hinh");
                        DongMonChinh product = new DongMonChinh(id,id,hinh,tenmon,"soluong",gia,theloai);
                        dongMonChinhArrayList.add(product);
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
    public void Getmontheoyeucau(String yeucau) {
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
                        String tenmon        = (String) document.get("TenMon");
                        String gia       = (String) document.get("GiaTien");
                        String theloai  = (String) document.get("TenLoai");
                        String hinh    =  (String) document.get("Hinh");
                        if(yeucau.equals(theloai))
                        {
                            DongMonChinh product = new DongMonChinh(id,id,hinh,tenmon,"soluong",gia,theloai);
                            dongMonChinhArrayList.add(product);
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
    private void MenuPopup()
    {
        PopupMenu popupMenu=new PopupMenu(getActivity(),btnChonmonAdmin);
        popupMenu.getMenuInflater().inflate(R.menu.menu_admin_mon_an,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tatcamonan:
                        btnChonmonAdmin.setText("Tất cả");
                        GetProducts();
                        break;
                    case R.id.monchinh:
                        btnChonmonAdmin.setText("Món chính");
                        Getmontheoyeucau(btnChonmonAdmin.getText().toString());
                        break;
                    case R.id.trangmieng:
                        btnChonmonAdmin.setText("Tráng miệng");
                        Getmontheoyeucau(btnChonmonAdmin.getText().toString());
                        break;
                    case R.id.giaikhat:
                        btnChonmonAdmin.setText("Nước");
                        Getmontheoyeucau(btnChonmonAdmin.getText().toString());
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
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
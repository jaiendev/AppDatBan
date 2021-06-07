package com.trungdang.appdatban.HomeAdmin.Quanlyban;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.trungdang.appdatban.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Quanlyban#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Quanlyban extends Fragment implements itemswipe {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    QuanlybanAdapter quanlybanAdapter;
    ArrayList<Dongquanlyban> dongquanlybans;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference storageRef;
    FirebaseAuth fAuth;
    String IDUser;
    TextView txttenuser,btnChonbanAdmin;
    ImageView imgUser,imgAdd;
    public Quanlyban() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Quanlyban.
     */
    // TODO: Rename and change types and number of parameters
    public static Quanlyban newInstance(String param1, String param2) {
        Quanlyban fragment = new Quanlyban();
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
        View view= inflater.inflate(R.layout.fragment_quanlyban, container, false);
        Anhxa(view);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Addban.class));
            }
        });
        return view;
    }
    private void Anhxa(View view)
    {
        imgAdd=view.findViewById(R.id.imgButtonAddbanAdmin);
        btnChonbanAdmin=view.findViewById(R.id.btnChonbanAdmin);
        recyclerView=view.findViewById(R.id.recyclerViewAdmin);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        dongquanlybans=new ArrayList<>();
        quanlybanAdapter=new QuanlybanAdapter(dongquanlybans,getActivity());
        recyclerView.setAdapter(quanlybanAdapter);
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        fStorage        = FirebaseStorage.getInstance();
        storageRef      = fStorage.getReference();
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        IDUser=fAuth.getCurrentUser().getUid();
        txttenuser=view.findViewById(R.id.txtTenQuanlyban);
        imgUser=view.findViewById(R.id.imgHomeQuanlyban);
        GetProducts();
        getNameUser();
        btnChonbanAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPopup();
            }
        });
    }

    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder) {

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
        dongquanlybans.clear();
        CollectionReference productRefs = fStore.collection("Products");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id           = document.getId();
                        String tenban         = (String) document.get("Tenban");
                        String gia       = (String) document.get("Gia");
                        String theloai  = (String) document.get("Loai");
                        String soluong     = (String) document.get("Soluong");
                        String hinh    =  (String) document.get("Hinh");
                        Dongquanlyban product = new Dongquanlyban(id,hinh,tenban,soluong,gia,theloai);
                        dongquanlybans.add(product);
                    }
                    quanlybanAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","Error");
            }
        });
    }
    public void GetTableTheoyeucau(String yeucau) {
        dongquanlybans.clear();
        CollectionReference productRefs = fStore.collection("Products");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String id           = document.getId();
                        String tenban         = (String) document.get("Tenban");
                        String gia       = (String) document.get("Gia");
                        String theloai  = (String) document.get("Loai");
                        String soluong     = (String) document.get("Soluong");
                        String hinh    =  (String) document.get("Hinh");
                        if(yeucau.equals(theloai))
                        {
                            Dongquanlyban product = new Dongquanlyban(id,hinh,tenban,soluong,gia,theloai);
                            dongquanlybans.add(product);
                        }
                    }
                    quanlybanAdapter.notifyDataSetChanged();
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
        PopupMenu popupMenu=new PopupMenu(getActivity(),btnChonbanAdmin);
        popupMenu.getMenuInflater().inflate(R.menu.menu_home_dat_ban,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tatca:
                        btnChonbanAdmin.setText("Tất cả");
                        GetProducts();
                        break;
                    case R.id.giadinh:
                        btnChonbanAdmin.setText("Gia đình");
                        GetTableTheoyeucau(btnChonbanAdmin.getText().toString());
                        break;
                    case R.id.banbe:
                        btnChonbanAdmin.setText("Bạn bè");
                        GetTableTheoyeucau(btnChonbanAdmin.getText().toString());
                        break;
                    case R.id.capdoi:
                        btnChonbanAdmin.setText("Cặp đôi");
                        GetTableTheoyeucau(btnChonbanAdmin.getText().toString());
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),123);
    }
  
}
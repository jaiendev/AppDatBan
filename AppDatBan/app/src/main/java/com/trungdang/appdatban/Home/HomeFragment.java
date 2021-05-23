package com.trungdang.appdatban.Home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
import com.trungdang.appdatban.Datban.Datban;
import com.trungdang.appdatban.Datmon.fragment_Datmon.Bill.Bill;
import com.trungdang.appdatban.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends androidx.fragment.app.Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore   fStore;
    FirebaseStorage     fStorage;
    StorageReference    storageRef;
    FirebaseAuth        fAuth;
    ListView listView;
    DatBanAdapter datBanAdapter;
    ArrayList<Datbanhome> datbanhomeArrayList;
    String IDUser;
    TextView txtTenUser;
    String idban;
    ImageView imageViewHinhUser;
    public HomeFragment() {
        String idBan=idban;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        AnhXa(view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), Datban.class);
                String tenban=((TextView) view.findViewById(R.id.txtsoban)).getText().toString();
                String mota=((TextView) view.findViewById(R.id.txtmota)).getText().toString();
                String giaban=((TextView) view.findViewById(R.id.txtgiatien)).getText().toString();
                String theloai=((TextView) view.findViewById(R.id.txtthethuc)).getText().toString();
                idban=datbanhomeArrayList.get(position).idban;
                String Hinh=datbanhomeArrayList.get(position).getHinhdatban();
                Log.d("ddd",Hinh);
                intent.putExtra("soban",tenban);
                intent.putExtra("mota",mota);
                intent.putExtra("giaban",giaban);
                intent.putExtra("theloai",theloai);
                intent.putExtra("id",idban);
                Bill.idbans=idban;
                intent.putExtra("hinh",Hinh);
                getContext().startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
    private void AnhXa(View view){
        listView=view.findViewById(R.id.listviewhome);
        txtTenUser=view.findViewById(R.id.txtTenUser);
        imageViewHinhUser=view.findViewById(R.id.imgHomeCustomer);
        datbanhomeArrayList=new ArrayList<>();
        datBanAdapter=new DatBanAdapter(getActivity(),R.layout.dong_dat_ban,datbanhomeArrayList);
        listView.setAdapter(datBanAdapter);
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        fStorage        = FirebaseStorage.getInstance();
        storageRef      = fStorage.getReference();
        IDUser=fAuth.getCurrentUser().getUid();
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
                        Datbanhome product = new Datbanhome(id,hinh,tenban,soluong,gia,theloai);
                        datbanhomeArrayList.add(product);
                    }
                    datBanAdapter.notifyDataSetChanged();
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
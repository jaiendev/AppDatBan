package com.trungdang.appdatban.Home.Lichsugiaodich;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.trungdang.appdatban.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichsugiaodichFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichsugiaodichFragment extends androidx.fragment.app.Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String idbill;
    public static String IdUser;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore fStore;
    FirebaseStorage fStorage;
    StorageReference storageRef;
    FirebaseAuth fAuth;
    ListView listViewLichsugiaodich;
    ListviewLichsugiaodichAdapter adapter;
    ArrayList<dongLichsugiaodich> dongLichsugiaodiches;
    ImageView imgreload;
    public LichsugiaodichFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LichsugiaodichFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LichsugiaodichFragment newInstance(String param1, String param2) {
        LichsugiaodichFragment fragment = new LichsugiaodichFragment();
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
    public static Fragment newInstance()
    {
        LichsugiaodichFragment chitietdonhangFragment = new LichsugiaodichFragment();
        return chitietdonhangFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lichsugiaodich, container, false);
        AnhXa(view);
        listViewLichsugiaodich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fg;
                fg= ChitietdonhangFragment.newInstance();
                replaceFragment(fg);
                idbill=dongLichsugiaodiches.get(position).getMadonhang();
            }
        });
        return view;
    }
    private void AnhXa(View view){
        listViewLichsugiaodich=view.findViewById(R.id.lstLichsugiaodich);
        dongLichsugiaodiches=new ArrayList<>();
        adapter=new ListviewLichsugiaodichAdapter(getActivity(),R.layout.dong_lich_su_giao_dich,dongLichsugiaodiches);
        listViewLichsugiaodich.setAdapter(adapter);
        fAuth           = FirebaseAuth.getInstance();
        fStore          = FirebaseFirestore.getInstance();
        fStorage        = FirebaseStorage.getInstance();
        storageRef      = fStorage.getReference();
        imgreload=view.findViewById(R.id.Buttonreload);
        imgreload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetProducts();
            }
        });
        GetProducts();
        listViewLichsugiaodich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fg;
                fg= ChitietdonhangFragment.newInstance();
                replaceFragment(fg);
                idbill=dongLichsugiaodiches.get(position).getMadonhang();
            }
        });
    }
    public void GetProducts() {
        dongLichsugiaodiches.clear();
        CollectionReference productRefs = fStore.collection("Bills");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        String idMadon           = document.getId();
                        String idU              =(String) document.getString("idUser");
                        String Ngaydat         = (String) document.get("date");
                        String tongtien       = (String) document.get("tongTien");
                        if(idU.equals(IdUser))
                        {
                            dongLichsugiaodiches.add(new dongLichsugiaodich(idMadon,Ngaydat,tongtien));
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
    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction trasection = getFragmentManager().beginTransaction();

        if(!newFragment.isAdded()) {
            try {
                //FragmentTransaction trasection =
                getFragmentManager().beginTransaction();
                trasection.replace(R.id.liner1, newFragment);
                trasection.addToBackStack(null);
                trasection.commit();
            } catch (Exception e) {
                // TODO: handle exception
                // AppConstants.printLog(e.getMessage());
            }

        }
        else{
            trasection.show(newFragment);
        }
    }
}
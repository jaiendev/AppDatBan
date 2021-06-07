package com.trungdang.appdatban.Home.Lichsugiaodich;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.trungdang.appdatban.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChitietdonhangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChitietdonhangFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView maban,ngaydat,loaiban,monchinh,trangmieng,giaikhat,tongtien;
    List<String> monchinhs;
    ImageView imgback;
    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    public ChitietdonhangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChitietdonhangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChitietdonhangFragment newInstance(String param1, String param2) {
        ChitietdonhangFragment fragment = new ChitietdonhangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static Fragment newInstance()
    {
        ChitietdonhangFragment chitietdonhangFragment = new ChitietdonhangFragment();
        return chitietdonhangFragment;
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
        View view= inflater.inflate(R.layout.fragment_chitietdonhang, container, false);
        Init(view);
        return view;
    }
    private void Init(View view)
    {
        maban=view.findViewById(R.id.txtmabanChitietdonhang);
        ngaydat=view.findViewById(R.id.txtNgaydatChitietdonhang);
        loaiban=view.findViewById(R.id.txtLoaibanChitietdonhang);
        monchinh=view.findViewById(R.id.txtMonchinhChitietdonhang);
        trangmieng=view.findViewById(R.id.txtTrangmiengChitietdonhang);
        giaikhat=view.findViewById(R.id.txtGiaikhatChitietdonhang);
        tongtien=view.findViewById(R.id.txtTongtienChitietdonhang);
        imgback=view.findViewById(R.id.imgbackchitietdonhang);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fg;
                fg= LichsugiaodichFragment.newInstance();
                replaceFragment(fg);
            }
        });
        GetBill();
        getMonchinh();
    }
    private void GetBill(){
        CollectionReference productRefs = fStore.collection("Bills");
        productRefs.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public  void onSuccess(QuerySnapshot documentSnapshots) {
                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else {
                    for (DocumentSnapshot document : documentSnapshots) {
                        maban.setText(document.getString("idban"));
                        ngaydat.setText(document.getString("date"));
                        loaiban.setText(document.getString("loaiban"));
                        tongtien.setText(document.getString("tongTien"));
                        monchinhs=(List<String>) document.get("monchinh");
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","Error");
            }
        });
    }
    private void getMonchinh() {
        fStore.collection("Bills")
                .document(LichsugiaodichFragment.idbill).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            ArrayList<String> list = (ArrayList<String>) document.get("monchinh");
                            monchinh.setText(Arrays.toString(list.toArray()));

                            ArrayList<String> listgiaikhat = (ArrayList<String>) document.get("giaikhat");
                            giaikhat.setText(Arrays.toString(listgiaikhat.toArray()));

                            ArrayList<String> listtrangmieng = (ArrayList<String>) document.get("trangmieng");
                            trangmieng.setText(Arrays.toString(listtrangmieng.toArray()));
                        }
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
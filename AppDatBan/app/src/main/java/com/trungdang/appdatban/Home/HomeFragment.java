package com.trungdang.appdatban.Home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.trungdang.appdatban.Datban.Datban;
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
    ListView listView;
    DatBanAdapter datBanAdapter;
    ArrayList<Datbanhome> datbanhomeArrayList;
    public HomeFragment() {
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
        datBanAdapter=new DatBanAdapter(getActivity(),R.layout.dong_dat_ban,datbanhomeArrayList);
        listView.setAdapter(datBanAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), Datban.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void AnhXa(View view){
        listView=view.findViewById(R.id.listviewhome);
        datbanhomeArrayList=new ArrayList<>();
        datbanhomeArrayList.add(new Datbanhome(R.drawable.ban1,"Bàn 1","2 Người lớn, 0 Trẻ em","Giá chung: 500.000vnđ/3 giờ","Cặp đôi"));
        datbanhomeArrayList.add(new Datbanhome(R.drawable.ban1,"Bàn 1","2 Người lớn, 0 Trẻ em","Giá chung: 500.000vnđ/3 giờ","Cặp đôi"));
        datbanhomeArrayList.add(new Datbanhome(R.drawable.ban1,"Bàn 1","2 Người lớn, 0 Trẻ em","Giá chung: 500.000vnđ/3 giờ","Cặp đôi"));
        datbanhomeArrayList.add(new Datbanhome(R.drawable.ban1,"Bàn 1","2 Người lớn, 0 Trẻ em","Giá chung: 500.000vnđ/3 giờ","Cặp đôi"));
        datbanhomeArrayList.add(new Datbanhome(R.drawable.ban1,"Bàn 1","2 Người lớn, 0 Trẻ em","Giá chung: 500.000vnđ/3 giờ","Cặp đôi"));
        datbanhomeArrayList.add(new Datbanhome(R.drawable.ban1,"Bàn 1","2 Người lớn, 0 Trẻ em","Giá chung: 500.000vnđ/3 giờ","Cặp đôi"));
        datbanhomeArrayList.add(new Datbanhome(R.drawable.ban1,"Bàn 1","2 Người lớn, 0 Trẻ em","Giá chung: 500.000vnđ/3 giờ","Cặp đôi"));
        datbanhomeArrayList.add(new Datbanhome(R.drawable.ban1,"Bàn 1","2 Người lớn, 0 Trẻ em","Giá chung: 500.000vnđ/3 giờ","Cặp đôi"));
    }
}
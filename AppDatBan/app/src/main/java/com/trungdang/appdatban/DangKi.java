package com.trungdang.appdatban;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.trungdang.appdatban.Home.Home;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangKi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangKi extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText edtten,edtEmail,edtpass1,edtpass2;
    Button btnDangky;
    FirebaseAuth fAuth;
    public DangKi() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangKi.
     */
    // TODO: Rename and change types and number of parameters
    public static DangKi newInstance(String param1, String param2) {
        DangKi fragment = new DangKi();
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
        View view = inflater.inflate(R.layout.fragment_dang_ki, container, false);
        Init(view);
        return view;
    }
    private void Init(View view){
        edtten=(EditText) view.findViewById(R.id.editten);
        edtEmail=(EditText) view.findViewById(R.id.editsodidongemail);
        edtpass1=(EditText) view.findViewById(R.id.editmatkhau1);
        edtpass2=(EditText) view.findViewById(R.id.editmatkhau2);
        fAuth= FirebaseAuth.getInstance();
        btnDangky=(Button) view.findViewById(R.id.btnDangKy);

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=edtEmail.getText().toString().trim();
                String Password1=edtpass1.getText().toString().trim();
                String Password2=edtpass2.getText().toString().trim();
                if(Password1.compareTo(Password2)!=0){
                    edtpass2.setError("Không giống mật khẩu 1");
                    return;
                }
                if(TextUtils.isEmpty(Email)){
                    edtEmail.setError("Hãy điền Email");
                    return;
                }
                if(TextUtils.isEmpty(Password1))
                {
                    edtpass1.setError("Hãy nhập mật khẩu");
                    return;
                }
                if(Password1.length()<=6)
                {
                    edtpass1.setError("Mật khẩu cần hơn 6 ký tự");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(Email,Password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"Tạo tài khoản thành công",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getActivity().getApplicationContext(), Home.class));
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(),"Lỗi ! ",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }
}
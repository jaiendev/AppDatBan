package com.trungdang.appdatban;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


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
    ImageView imgpass1,imgpass2;
    FirebaseAuth fAuth;
    private int passwordNotVisible1 = 1;

    private int passwordNotVisible2 = 1;
    private FirebaseFirestore fStore;
    private String
            vip=null,
            ngaysinh=null,
            loai=null,
            ten = null,
            password = null,
            email = null,
            sdt = null,
            userID = null;
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
        imgpass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordNotVisible1 == 1) {
                    imgpass1.setColorFilter(getResources().getColor(R.color.primarycolor), PorterDuff.Mode.SRC_IN);
                    edtpass1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordNotVisible1 = 0;
                } else {
                    imgpass1.setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);
                    edtpass1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordNotVisible1 = 1;
                }
                edtpass1.setSelection(edtpass1.length());
            }
        });
        imgpass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordNotVisible2== 1) {
                    imgpass2.setColorFilter(getResources().getColor(R.color.primarycolor), PorterDuff.Mode.SRC_IN);
                    edtpass2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordNotVisible2 = 0;
                } else {
                    imgpass2.setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);
                    edtpass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordNotVisible2 = 1;
                }
                edtpass2.setSelection(edtpass2.length());
            }
        });
        return view;
    }
    private void Init(View view){
        edtten=(EditText) view.findViewById(R.id.editten);
        edtEmail=(EditText) view.findViewById(R.id.editsodidongemail);
        edtpass1=(EditText) view.findViewById(R.id.editmatkhau1);
        edtpass2=(EditText) view.findViewById(R.id.editmatkhau2);
        imgpass1= view.findViewById(R.id.imgshowpassword1);
        imgpass2=view.findViewById(R.id.imgshowpassword2);
        fAuth= FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
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
                            email=edtEmail.getText().toString().trim();
                            ten=edtten.getText().toString().trim();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users").document(userID);
                            Map<String, Object> User = new HashMap<>();
                            User.put("ten", ten);
                            User.put("email", email);
                            User.put("sodienthoai", "");
                            User.put("loai","Customer");
                            User.put("ngaysinh", "29/12/2000");
                            User.put("Avatar","https://firebasestorage.googleapis.com/v0/b/appdatban.appspot.com/o/Users%2FImage%2Fman.png?alt=media&token=7e71f6c8-52bb-4e97-9337-1b8783568e0e");
                            User.put("vip","no");
                            User.put("idUser",userID);
                            documentReference.set(User).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: user profile is create for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: "+ e.toString());
                                }
                            });
                            Toast.makeText(getActivity(),"Tạo tài khoản thành công",Toast.LENGTH_LONG).show();
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
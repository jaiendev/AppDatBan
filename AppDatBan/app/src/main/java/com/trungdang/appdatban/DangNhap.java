package com.trungdang.appdatban;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trungdang.appdatban.Home.Home;
import com.trungdang.appdatban.HomeAdmin.HomeAdmin;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DangNhap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DangNhap extends androidx.fragment.app.Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText editusername,editpassword;
    TextView txtQuenMatKhau;
    Button btnDangNhap;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    FirebaseFirestore fStore;
    private String password="",
                    email="",
                    UserID="";
    AlertDialog.Builder reset_alert;
    ImageView imgshowpassword;
    private int passwordNotVisible = 1;
    public DangNhap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DangNhap.
     */
    // TODO: Rename and change types and number of parameters
    public static DangNhap newInstance(String param1, String param2) {
        DangNhap fragment = new DangNhap();
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
        View view= inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        editusername=(EditText) view.findViewById(R.id.editusername);
        editpassword=(EditText) view.findViewById(R.id.editpassword);
        txtQuenMatKhau=view.findViewById(R.id.txtquenmatkhau);
        btnDangNhap=(Button) view.findViewById(R.id.btnDangNhap);
        fAuth= FirebaseAuth.getInstance();
        fUser=FirebaseAuth.getInstance().getCurrentUser();
        fStore=FirebaseFirestore.getInstance();
        reset_alert=new AlertDialog.Builder(getActivity());
        imgshowpassword=view.findViewById(R.id.imgshowpassword);
        inflater=this.getLayoutInflater();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username=editusername.getText().toString().trim();
                String Password=editpassword.getText().toString().trim();
                if(TextUtils.isEmpty(Username)){
                    editusername.setError("Email chưa điền");
                    return;
                }
                if(TextUtils.isEmpty(Password))
                {
                    editpassword.setError("Hãy nhập mật khẩu");
                    return;
                }
                if(Password.length()<=6)
                {
                    editpassword.setError("Mật khẩu cần hơn 6 ký tự");
                    return;
                }
                fAuth.signInWithEmailAndPassword(Username,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                            CheckRole();
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(),"Lỗi ! ",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        LayoutInflater finalInflater = inflater;
        txtQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewroof= finalInflater.inflate(R.layout.quenmatkhau,null);
                reset_alert.setTitle("Đặt lại mật khẩu ?").setMessage("Nhập Email của bạn để lấy lại mật khẩu")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText email=viewroof.findViewById(R.id.editquenmatkhauemail);
                                if(email.getText().toString().trim().isEmpty())
                                {
                                    email.setError("Nhập Email");
                                    return;
                                }
                                fAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getActivity().getApplicationContext(),"Đã gửi link cho Email",Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity().getApplicationContext(),"Lỗi! ",Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }).setNegativeButton("Cancel",null).setView(viewroof).create().show();

            }
        });
        imgshowpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordNotVisible == 1) {
                    imgshowpassword.setColorFilter(getResources().getColor(R.color.primarycolor), PorterDuff.Mode.SRC_IN);
                    editpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordNotVisible = 0;
                } else {
                    imgshowpassword.setColorFilter(Color.parseColor("#FF000000"), PorterDuff.Mode.SRC_IN);
                    editpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordNotVisible = 1;
                }
                editpassword.setSelection(editpassword.length());
            }
        });
        return view;

    }
    private void CheckRole() {
        UserID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Users").document(UserID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        String role = document.getString("loai");
                        Intent intent = new Intent();
                        switch (role) {
                            case "Customer":
                                intent = new Intent(getContext(), Home.class);
                                break;
                            case "Admin":
                                intent = new Intent(getActivity(), HomeAdmin.class);
                                break;
                            default:
                                break;
                        }
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }

        });
    }
}
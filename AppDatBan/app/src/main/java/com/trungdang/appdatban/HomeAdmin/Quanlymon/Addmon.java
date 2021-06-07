package com.trungdang.appdatban.HomeAdmin.Quanlymon;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.trungdang.appdatban.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class Addmon extends AppCompatActivity {
    ImageView imghinhmon;
    TextView txtchonanh;
    EditText edttenmon,edtloai,edtgia;
    Button btnThem;
    FirebaseFirestore fStore;
    Bitmap bitmaps;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String imghinh;
    Uri file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmon);
        imghinhmon=findViewById(R.id.Chonhinhthemmon);
        txtchonanh=findViewById(R.id.txtchonanhmon);
        edttenmon=findViewById(R.id.edtthemtenmonquanlymon);
        edtloai=findViewById(R.id.edtthemLoaimonquanlymon);
        edtgia=findViewById(R.id.edtthemgiaquanlymon);
        btnThem=findViewById(R.id.Btnchuthemquanlymon);
        edtloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuPopup();
            }
        });
        fStore=FirebaseFirestore.getInstance();
        txtchonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Permission();

            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addban();
                ClearForm();

            }
        });
    }
    private void Permission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(Addmon.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(Addmon.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }
    private void openImagePicker(){
        TedBottomPicker.with(Addmon.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected image uri
                        Bitmap bitmap= null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imghinhmon.setImageBitmap(bitmap);
                            file=uri;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    //    private void UploadImage(){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmaps.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//        StorageReference mountainsRef = storageRef.child("Products/hinhban/"+edtTenhinh.getText().toString()+".jpg");
//        imghinh="Products/hinhban/"+edtTenhinh.getText().toString()+".jpg";
//        UploadTask uploadTask = mountainsRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                // ...
//            }
//        });
//
//    }
    private void UploadImage(String productID) {
        if (file != null) {

            StorageReference ref = storageRef.child("Products/Mon/" + productID);
            ref.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
            GetImageUrl(ref, productID);
        }
    }
    private void GetImageUrl(StorageReference ref, String productID) {
        UploadTask uploadTask = ref.putFile(file);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String imgUrl = downloadUri.toString();
                    fStore.collection("Eaten").document(productID).update("Hinh", imgUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "DocumentSnapshot successfully updated!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("TAG", "Error updating document", e);
                        }
                    });
                }
                else {
                }
            }
        });
    }
    private void Addban(){
        Map<String, Object> table = new HashMap<>();
        table.put("TenMon", edttenmon.getText().toString());
        table.put("TenLoai", edtloai.getText().toString());
        table.put("GiaTien",edtgia.getText().toString());
        if(edtloai.getText().toString().equals("Món chính"))
        {
            table.put("idLoai","1");
        }
        if(edtloai.getText().toString().equals("Tráng Miệng"))
        {
            table.put("idLoai","2");
        }
        if(edtloai.getText().toString().equals("Nước"))
        {
            table.put("idLoai","3");
        }
        fStore.collection("Eaten")
                .add(table)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                        UploadImage(documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error writing document", e);
            }
        });
    }
    private void ClearForm(){
        edtloai.setText("");
        edttenmon.setText("");
        edtgia.setText("");
    }
    private void MenuPopup()
    {
        PopupMenu popupMenu=new PopupMenu(getApplicationContext(),edtloai);
        popupMenu.getMenuInflater().inflate(R.menu.menu_them_mon,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Monchinhthemmon:
                        edtloai.setText("Món chính");
                        break;
                    case R.id.TrangMiengthemmon:
                        edtloai.setText("Tráng miệng");
                        break;
                    case R.id.Nuocthemmon:
                        edtloai.setText("Nước");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

}
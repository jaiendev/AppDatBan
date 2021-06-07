package com.trungdang.appdatban.HomeAdmin.Quanlyban;

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

public class Addban extends AppCompatActivity {
    ImageView imghinhban;
    TextView txtchonanh;
    EditText edttenban,edtloai,edtgia,edtsonguoilon,edtsotreem,edtTenhinh;
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
        setContentView(R.layout.activity_addban);
        imghinhban=findViewById(R.id.Chonhinhdialogban);
        txtchonanh=findViewById(R.id.txtchonanhmo);
        edttenban=findViewById(R.id.edttenbandialogquanlyban);
        edtloai=findViewById(R.id.edtDanhchodialogquanlyban);
        edtgia=findViewById(R.id.edtgiabandialogquanlyban);
        edtsonguoilon=findViewById(R.id.edtnguoilondialogquanlyban);
        edtsotreem=findViewById(R.id.edttreemdialogquanlyban);
        btnThem=findViewById(R.id.Btnthemdialogquanlyban);
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
                Toast.makeText(Addban.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(Addban.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }
    private void openImagePicker(){
        TedBottomPicker.with(Addban.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected image uri
                        Bitmap bitmap= null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imghinhban.setImageBitmap(bitmap);
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

        StorageReference ref = storageRef.child("Products/hinhban/" + productID);
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
                    fStore.collection("Products").document(productID).update("Hinh", imgUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        table.put("Tenban", edttenban.getText().toString());
        table.put("Loai", edtloai.getText().toString());
        table.put("Gia",edtgia.getText().toString());
        table.put("Soluong",edtsonguoilon.getText().toString()+" người lớn, " +edtsotreem.getText().toString()+" trẻ em");
        fStore.collection("Products")
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
        edtsotreem.setText("");
        edttenban.setText("");
        edtsonguoilon.setText("");
        edtgia.setText("");
    }
    private void MenuPopup()
    {
        PopupMenu popupMenu=new PopupMenu(getApplicationContext(),edtloai);
        popupMenu.getMenuInflater().inflate(R.menu.menu_them_ban,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Giadinhthemban:
                        edtloai.setText("Gia đình");
                        break;
                    case R.id.Banebthemban:
                        edtloai.setText("Bạn bè");
                        break;
                    case R.id.capdoithemban:
                        edtloai.setText("Cặp đôi");
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

}
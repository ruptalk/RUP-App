package org.techtown.reducetheuseofplastic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.journeyapps.barcodescanner.camera.FitXYStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UserInformationActivity extends AppCompatActivity {

    private String TAG="UserInformationActivity";
    //private ImageButton img_modify_user;
    private ImageView img_modify_user;
    private EditText edit_name, edit_account, edit_pw, edit_pw2;
    private Button btn_user_modify;
    private Spinner spinner_bank;
    private String uid, email, name, nname, account, naccount, npw, npw2;
    private DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private File tempFile;//갤러리로 부터 받아온 이미지를 저장
    private Uri photoUri;
    FirebaseStorage storage =FirebaseStorage.getInstance();
    StorageReference reference=storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information);

        img_modify_user=(ImageView)findViewById(R.id.imgbtn_userprofile);
        btn_user_modify=(Button)findViewById(R.id.btn_user_modify);
        //btn_pw_modify=(Button)findViewById(R.id.btn_pw_modify);
        edit_name=(EditText) findViewById(R.id.edit_modify_nickname);
        edit_account=(EditText)findViewById(R.id.edit_modify_account);
        edit_pw=(EditText)findViewById(R.id.edit_modify_password);
        edit_pw2=(EditText)findViewById(R.id.edit_modify_password2);
        uid=user.getUid();

        spinner_bank=(Spinner)findViewById(R.id.spinner_bank);
        ArrayAdapter bankAdapter=ArrayAdapter.createFromResource(this, R.array.select_bank, android.R.layout.simple_spinner_dropdown_item);
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_bank.setAdapter(bankAdapter);

        spinner_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if(user!=null){
            rootRef.child("Users2").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserInfo userInfo=dataSnapshot.getValue(UserInfo.class);
                    email=userInfo.getEmail();
                    name=userInfo.getName();
                    account=userInfo.getAccount();

                    edit_name.setText(name);
                    edit_account.setText(account);
                    Log.d(TAG,"email: "+email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            System.out.println("왜 user 연결이 안될까나");
        }


        //유저프로필 설정
        //권한 요청(참고사이트 : https://bottlecok.tistory.com/49, https://onlyfor-me-blog.tistory.com/148 )
        img_modify_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    //권한이 있을 경우
                    startPermissionRequest();
                    System.out.println("권한이 있숨당");
                }
                else{
                    //권한이 없는 경우
                    requestPermission();
                }
            }
        });
/*
        btn_pw_modify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String newPassword="12341234";
                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            rootRef.child("Users2").child(uid).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    UserInfo userInfo=dataSnapshot.getValue(UserInfo.class);
                                    //userInfo.setPw(newPassword);
                                    rootRef.child("Users2").child(uid).child("pw").setValue(newPassword);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Log.d(TAG,"User password updated.");
                        }
                        else{
                            Log.d(TAG,"User password update failed.");
                        }
                    }
                });


*/
        //수정하기 버튼
        btn_user_modify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserInfo userInfo=new UserInfo();
                nname=edit_name.getText().toString();
                naccount=edit_account.getText().toString();
                npw=edit_pw.getText().toString();
                npw2=edit_pw2.getText().toString();
                //사용자 비밀번호설정과 체크하는 란이 동일한지 검사
                if(!npw.isEmpty()&&!npw2.isEmpty()){
                    if(npw.equals(npw2)){
                        user.updatePassword(npw).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //사용자 정보 갱신
                                    rootRef.child("Users2").child(uid).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            UserInfo userInfo=dataSnapshot.getValue(UserInfo.class);
                                            rootRef.child("Users2").child(uid).child("pw").setValue(npw);
                                            rootRef.child("Users2").child(uid).child("name").setValue(nname);
                                            rootRef.child("Users2").child(uid).child("account").setValue(naccount);
                                            userInfo.setName(nname);
                                            userInfo.setAccount(naccount);
                                            Toast.makeText(UserInformationActivity.this,"정보가 수정되었습니다1.",Toast.LENGTH_SHORT).show();
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Log.d(TAG,"User password updated.");
                                }
                                else{
                                    Log.e(TAG,"User password update failed.");
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(UserInformationActivity.this,"비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    rootRef.child("Users2").child(uid).child("name").setValue(nname);
                    rootRef.child("Users2").child(uid).child("account").setValue(naccount);
                    //사용자 프로필 저장 경로 설정(참고 사이트 : https://daldalhanstory.tistory.com/201)
                    if(photoUri!=null) {
                        String filename = "Userprofile.jpg";
                        reference.child("Userprofile").child(uid);
                        UploadTask uploadTask = reference.putFile(photoUri);
                        //새로운 프로필 이미지 storage에 저장
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "프로필 이미지에 실패했습니다..", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(), "프로필 이미지가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    userInfo.setName(nname);
                    userInfo.setAccount(naccount);
                    Toast.makeText(UserInformationActivity.this,"정보가 수정되었습니다2.",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
    //권한 보유 여부 확인
    private boolean checkPermission(){
        int permissionState= ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionState == PackageManager.PERMISSION_GRANTED;//앱의 권한이 있는 경우 True, 없는 경우 False
    }
    //앱에 권한이 없는 경우 필요한 권한을 요청
    private void startPermissionRequest(){
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
    }
    //사용자가 설명이 필요할 수 도 있는 상황을 찾도록하는 android 유틸리티 메서드
    private void requestPermission(){
        boolean shouldProviceRationale=ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(shouldProviceRationale){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("알림");
            builder.setMessage("저장소 권한이 필요합니다.");
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startPermissionRequest();
                }
            });
            builder.show();
        }
        else{
            startPermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    System.out.println("여기에 이제 앨범나와야지");
//                    Intent intent=new Intent();
//                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                    startActivity(intent);
                    goToAlbum();
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("알림");
                    builder.setMessage("저장소 권한이 필요합니다. 환경설정에서 저장소권한을 허가해주세요");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri=Uri.fromParts("package", BuildConfig.APPLICATION_ID,null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }
        }
    }
    //앨범에서 이미지를 가져오는 함수
    private void goToAlbum(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,1);
    }
    //결과처리함수

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            photoUri=data.getData();//선택한 이미지의 Uri를 받아옴
            Cursor cursor=null;

            try{
                //Uri스키마를(Uri스키마가 뭔데?)cotent:///에서 file:///으로 변경한다.
                String[] proj={MediaStore.Images.Media.DATA};
                assert photoUri!=null;//assert 가 뭔디?
                cursor=getContentResolver().query(photoUri,proj,null,null,null);
                assert cursor !=null;//cursor를 통해 스키마를 content://에서 file://로 변경 이는 사진이 저장된 절대경로를 받아오는 과정
                int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                tempFile=new File(cursor.getString(column_index));
            }finally {
                //finally는 뭐야?
                if(cursor!=null){
                    cursor.close();
                }
            }
            setImage();
        }
    }
    //갤러리에서 받아온 이미지 넣기(참고사이트 : https://black-jin0427.tistory.com/120)
    private void setImage(){
        System.out.println("여기도 실행");
        BitmapFactory.Options options=new BitmapFactory.Options();
        Bitmap originalBm=BitmapFactory.decodeFile(tempFile.getAbsolutePath(),options);
        FitXYStrategy fitXYStrategy=new FitXYStrategy();
        img_modify_user.setImageBitmap(originalBm);
        //가로세로 비율에 상관없이 ImageViewdml 레이아웃의 각면에 꽉차게 출력(참고사이트 : https://sharp57dev.tistory.com/23, https://recipes4dev.tistory.com/105)
        //img_modify_user.setScaleType(ImageView.ScaleType.FIT_XY);
        //이미지 원본의 크기와 비율을 유지하며 이미지 원본대로 왼쪽상단을 기준으로 출력된다.
        //이미지가 ImageView의 레이아웃보다 크다면 나머지 이미지는 출력되지 않음
        img_modify_user.setScaleType(ImageView.ScaleType.MATRIX);//만약에 이거를 할거면 뒤에 배경도 바꿔주는 명령어필요
        img_modify_user.setBackground(ContextCompat.getDrawable(this,R.drawable.background));

        //파이어베이스에 사용자 이미지 경로 설정하기
    }

}
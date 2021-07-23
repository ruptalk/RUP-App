package org.techtown.reducetheuseofplastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInformationActivity extends AppCompatActivity {

    private String TAG="UserInformationActivity";
    private ImageButton img_modify_user;
    private EditText edit_name, edit_account, edit_pw, edit_pw2;
    private Button btn_user_modify;
    private Spinner spinner_bank;
    private String uid, email, name, nname, account, naccount, npw, npw2;
    private DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information);

        img_modify_user=(ImageButton)findViewById(R.id.img_modifiy_user);
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


        img_modify_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //개인 폰의 갤러리로 이동하게
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
        btn_user_modify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                UserInfo userInfo=new UserInfo();
                nname=edit_name.getText().toString();
                naccount=edit_account.getText().toString();
                npw=edit_pw.getText().toString();
                npw2=edit_pw2.getText().toString();

                if(!npw.isEmpty()&&!npw2.isEmpty()){
                    if(npw.equals(npw2)){
                        user.updatePassword(npw).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
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
                    userInfo.setName(nname);
                    userInfo.setAccount(naccount);
                    Toast.makeText(UserInformationActivity.this,"정보가 수정되었습니다2.",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
}
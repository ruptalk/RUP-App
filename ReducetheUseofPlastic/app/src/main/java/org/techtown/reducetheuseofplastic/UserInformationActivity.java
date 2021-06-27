package org.techtown.reducetheuseofplastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInformationActivity extends AppCompatActivity {

    private ImageButton img_modify_user;
    private EditText edit_ninkname, edit_pw, edit_pw2, edit_phone;
    private Button btn_user_modify;
    private Spinner spinner_bank;
    private String nnickname, npw, npw2, nphone; // naccount;
    private String nickname, phone;
    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information);

        img_modify_user=(ImageButton)findViewById(R.id.img_modifiy_user);
        btn_user_modify=(Button)findViewById(R.id.btn_user_modify);
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

        edit_ninkname=(EditText) findViewById(R.id.edit_modify_nickname);
        edit_pw=(EditText)findViewById(R.id.edit_modify_password);
        edit_pw2=(EditText)findViewById(R.id.edit_modify_password2);
        edit_phone=(EditText)findViewById(R.id.edit_modify_phone);

        databaseReference=database.getReference();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            databaseReference.child("Users").child("yunjeong").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserInfo userInfo=dataSnapshot.getValue(UserInfo.class);
                    nickname=userInfo.getName();

                    edit_ninkname.setText(nickname);
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

        btn_user_modify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                npw=edit_pw.getText().toString();
                npw2=edit_pw2.getText().toString();
                nnickname=edit_ninkname.getText().toString();
                nphone=edit_phone.getText().toString();

                if(!npw.isEmpty() &&!npw.isEmpty()){
                    if(npw.equals(npw2)){
                        databaseReference.child("yunjeong").child("pw").setValue(npw);
                        databaseReference.child("yunjeong").child("name").setValue(nnickname);
                        databaseReference.child("yunjeong").child("phone").setValue(nphone);
                        Toast.makeText(UserInformationActivity.this,"정보가 수정되었습니다.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(UserInformationActivity.this,"비밀번호가 다릅니다. 다시 입력해주세요.",Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    databaseReference.child("yunjeong").child("name").setValue(nnickname);
                    databaseReference.child("yunjeong").child("phone").setValue(nphone);
                    Toast.makeText(UserInformationActivity.this,"정보가 수정되었습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                }

/*
                Intent intent=new Intent(UserInformationActivity.this, MyPageActivity.class);
                startActivity(intent);

 */
            }
        });
    }
}
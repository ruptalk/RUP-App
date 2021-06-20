package org.techtown.reducetheuseofplastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class UserInformationActivity extends AppCompatActivity {

    private ImageButton img_modify_user;
    private EditText edit_modify_nickname, edit_modify_password, edit_modify_password2,
                    edit_modify_phone, edit_modify_account;
    private Button btn_user_modify;
    private Spinner spinner_bank;

    private String nickname, pw, pw2, phone, account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information);

        img_modify_user=(ImageButton)findViewById(R.id.img_modifiy_user);
        edit_modify_nickname=(EditText)findViewById(R.id.edit_modify_nickname);
        edit_modify_password=(EditText)findViewById(R.id.edit_modify_password);
        edit_modify_password2=(EditText)findViewById(R.id.edit_modify_password2);
        edit_modify_phone=(EditText)findViewById(R.id.edit_modify_phone);
        edit_modify_account=(EditText)findViewById(R.id.edit_modify_account);
        btn_user_modify=(Button)findViewById(R.id.btn_user_modify);
        spinner_bank=(Spinner)findViewById(R.id.spinner_bank);

        nickname=edit_modify_nickname.getText().toString();
        pw=edit_modify_password.getText().toString();
        pw2=edit_modify_password2.getText().toString();
        phone=edit_modify_phone.getText().toString();
        account=edit_modify_account.getText().toString();

        img_modify_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //개인 폰의 갤러리로 이동하게
            }
        });

        btn_user_modify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(pw.equals(pw2)){

                }

                Intent intent=new Intent(UserInformationActivity.this, MyPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
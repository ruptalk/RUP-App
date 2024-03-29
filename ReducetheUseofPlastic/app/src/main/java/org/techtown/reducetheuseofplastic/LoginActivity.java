package org.techtown.reducetheuseofplastic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText edt_id,edt_pw;
    private Button btn_login,btn_reg;
    public String uid;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //FirebaseAuth인스턴스를 초기화 한다.
        firebaseAuth=FirebaseAuth.getInstance();
        edt_id=findViewById(R.id.edt_id);
        edt_pw=findViewById(R.id.edt_pw);
        btn_login=findViewById(R.id.btn_login);
        btn_reg=findViewById(R.id.btn_reg);



        /*
        if(firebaseAuth.getCurrentUser()!=null){
            //이미 로그인이 되어 있다면 액티비티를 종료 하고 main액티비티를 연다.
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

         */

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=edt_id.getText().toString().trim();
                String pw=edt_pw.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(id,pw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            firebaseAuth=FirebaseAuth.getInstance();
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            uid=user.getUid();
                            System.out.print(uid);
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            //intent.putExtra("userUid",uid);
                            startActivity(intent);

                        }else{
                            Toast.makeText(LoginActivity.this,"로그인 오류",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegisterActivity2.class);
                startActivity(intent);
            }
        });

    }




}

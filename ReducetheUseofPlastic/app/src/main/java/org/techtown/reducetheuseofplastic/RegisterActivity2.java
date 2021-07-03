package org.techtown.reducetheuseofplastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity2 extends AppCompatActivity {

    EditText edt_name,edt_email,edt_pw,edt_rpw;
    Button btn_reg;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        edt_name=(EditText)findViewById(R.id.edt_name2);
        edt_email=(EditText)findViewById(R.id.edt_email2);
        edt_pw=(EditText)findViewById(R.id.edt_pw2);
        edt_rpw=(EditText)findViewById(R.id.edt_rpw2);
        btn_reg=(Button)findViewById(R.id.btn_reg2);

        firebaseAuth=FirebaseAuth.getInstance();

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edt_name.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String pw=edt_pw.getText().toString().trim();
                String rpw=edt_rpw.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    edt_name.setError("이름이 비었습니다.");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    edt_email.setError("email이 비었습니다.");
                    return;
                }
                if(TextUtils.isEmpty(pw)){
                    edt_pw.setError("pw가 비었습니다.");
                    return;
                }
                if(TextUtils.isEmpty(rpw)){
                    edt_rpw.setError("비밀번호를 다시한번 입력해주세요.");
                    return;
                }
                if(pw.length()<6){
                    edt_pw.setError("비밀번호는 6자리 이상입니다.");
                    return;
                }

                if(pw.equals(rpw)){
                    final ProgressDialog dialog=new ProgressDialog(RegisterActivity2.this);
                    dialog.setMessage("RUP에 가입하는 중입니다~");
                    dialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                FirebaseUser user=firebaseAuth.getCurrentUser();
                                String uid=user.getUid();
                                UserInfo userinfo = new UserInfo();
                                userinfo.setEmail(email);
                                userinfo.setName(name);
                                userinfo.setPw(pw);
                                userinfo.setPoint("0");
                                userinfo.setAccount("");

                                /*HashMap<Object,Class> result=new HashMap<>();
                                result.put("name",name);
                                result.put("email",email);
                                result.put("pw",pw);
                                result.put("point","0");

                                 */
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                DatabaseReference reference=database.getReference("Users2");
                                reference.child(uid).setValue(userinfo);
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                Toast.makeText(getApplicationContext(),"RUP회원이 되셨습니다~",Toast.LENGTH_SHORT).show();

                            }
                            else{
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"이미 존재하는 아이디 입니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"비밀번호가 다릅니다.",Toast.LENGTH_SHORT).show();
                    edt_rpw.setError("비밀번호가 다릅니다.");
                    return;
                }
            }
        });

    }
}
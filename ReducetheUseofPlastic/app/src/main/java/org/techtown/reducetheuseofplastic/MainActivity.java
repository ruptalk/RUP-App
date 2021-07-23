package org.techtown.reducetheuseofplastic;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private ImageButton btn_home, btn_rank, btn_alarm;
    public String email="null",name,pw,userEmail,uid;
    public int point,account;
    public FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    public DatabaseReference databaseReference;
    MainFragment fragment_main;
    RankFragment fragment_rank;
    AlarmFragment fragment_alarm;

    Object value;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TextView test=(TextView)findViewById(R.id.tv_email);

        uid=firebaseUser.getUid();
        System.out.println(":)");
        System.out.println(firebaseUser);

       databaseReference=FirebaseDatabase.getInstance().getReference();
       databaseReference.child("Users2").child(uid).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               UserInfo userInfo=snapshot.getValue(UserInfo.class);
               email=userInfo.getEmail();
               pw=userInfo.getPw();
               name=userInfo.getName();
               point=Integer.parseInt(userInfo.getPoint());
               //account=Integer.parseInt(userInfo.getAccount());->default값 처리 해줘야 함
               Log.d("firebase",String.valueOf(snapshot.getValue()));
               Log.d("firebase",email);
               test.setText(email+"님 환영합니다.");

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
       System.out.println(email);





        fragment_main=new MainFragment();
        fragment_rank=new RankFragment();
        fragment_alarm=new AlarmFragment();

        btn_home=(ImageButton)findViewById(R.id.btn_home);
        btn_rank=(ImageButton)findViewById(R.id.btn_rank);
        btn_alarm=(ImageButton)findViewById(R.id.btn_alarm);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        btn_home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setFrag(0);
            }
        });


        btn_rank.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setFrag(1);
            }
        });

        btn_alarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { setFrag(2); }
        });



        Bundle bundle=new Bundle();
        bundle.putString("userEmail",userEmail);
        fragment_main.setArguments(bundle);
        fragment_rank.setArguments(bundle);
        setFrag(0);

    }
    public void setFrag(int n){
        switch (n){
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_main).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_rank).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_alarm).commit();
                break;

        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();
    }
}
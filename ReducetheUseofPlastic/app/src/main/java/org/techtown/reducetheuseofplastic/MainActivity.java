package org.techtown.reducetheuseofplastic;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    public String email,name;
    public int point,account;
    ImageButton btn_home, btn_rank, btn_alarm;
    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference;
    MainFragment fragment_main;
    RankFragment fragment_rank;
    AlarmFragment fragment_alarm;

    private long lastTimeBackPressed;



    //뒤로가기 버튼 두번누르면 종료 참고사이트:https://ddangeun.tistory.com/73
    @Override
    public void onBackPressed() {
        //이전화면으로 못돌아가게 하려면 부모코드 상속 못 받게 하기
        //super.onBackPressed();

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("take-out컵을 쓰는 날 또 봐요 :)");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);//테스크를 백그라운드로 이동
                finishAndRemoveTask();//액티비티 종료+테스크리스크에서 지우기
                System.exit(0);

                //이거 읽어보기: https://arabiannight.tistory.com/153
                //읽고 지우기기
            }
       });
        builder.setNegativeButton("앗 잠시만요!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        builder.show();

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final String uid=firebaseUser.getUid();
        fragment_main=new MainFragment();
        fragment_rank=new RankFragment();
        fragment_alarm=new AlarmFragment();
        setFrag(0);//여기까지


        btn_home=(ImageButton)findViewById(R.id.btn_home);
        btn_rank=(ImageButton)findViewById(R.id.btn_rank);
        btn_alarm=(ImageButton)findViewById(R.id.btn_alarm);



        btn_home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {setFrag(0);}
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




    }
    public void setFrag(int n){
        switch (n){
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_main).commitAllowingStateLoss();
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
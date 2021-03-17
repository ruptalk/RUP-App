package org.techtown.reducetheuseofplastic;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_home, btn_rank, btn_mypage; //프레그먼트 전환 버튼
    Button btn_dialog;
    public String userEmail;

    Dialog dialog1, dialog2, dialog3;

    private long lastTimeBackPressed;
    MainFragment fragment_main;
    RankFragment fragment_rank;
    MyPageFragment fragment_mypage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent= getIntent();
        userEmail=intent.getStringExtra("userEmail");

        dialog1=new Dialog(MainActivity.this); //Dialog 초기화
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 제거
        dialog1.setContentView(R.layout.dialog_1);
        dialog2=new Dialog(MainActivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.dialog_2);
        dialog3=new Dialog(MainActivity.this);
        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog3.setContentView(R.layout.dialog_3);


        //프레그먼트 전환
        btn_home=(Button)findViewById(R.id.btn_home);
        btn_rank=(Button)findViewById(R.id.btn_rank);
        btn_mypage=(Button)findViewById(R.id.btn_mypage);

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

        btn_mypage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MyPageFragment myPageFragment=new MyPageFragment();
                Bundle bundle=new Bundle();
                bundle.putString("userEmail",userEmail);
                myPageFragment.setArguments(bundle);
                setFrag(2);
            }
        });

        fragment_main=new MainFragment();
        fragment_rank=new RankFragment();
        fragment_mypage=new MyPageFragment();
        Bundle bundle=new Bundle();
        bundle.putString("userEmail",userEmail);
        fragment_main.setArguments(bundle);
        fragment_mypage.setArguments(bundle);
        setFrag(0);

        btn_dialog=(Button)findViewById(R.id.btn_scanQR);
        btn_dialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });

    }

    public void showDialog1(){
        dialog1.show();

        Button btn_next=dialog1.findViewById(R.id.btn_next_dialog1);
        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                showDialog2();
            }
        });
    }

    public void showDialog2(){
        dialog2.show();

        Button btn_prev=dialog2.findViewById(R.id.btn_prev_dialog2);
        Button btn_next=dialog2.findViewById(R.id.btn_next_dialog2);

        btn_prev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                showDialog1();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                showDialog3();
            }
        });
    }

    public void showDialog3(){
        dialog3.show();

        Button btn_prev=dialog3.findViewById(R.id.btn_prev_dialog3);
        Button btn_fin=dialog3.findViewById(R.id.btn_fin_dialog3);

        btn_prev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
                showDialog2();
            }
        });

        btn_fin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog3.dismiss(); //다이얼로그 닫기
            }
        });
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
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment_mypage).commit();
                break;
        }
    }
}
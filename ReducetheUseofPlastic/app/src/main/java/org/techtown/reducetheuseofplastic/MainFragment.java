package org.techtown.reducetheuseofplastic;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainFragment extends Fragment {

    String username,uid,TAG="MainFragment";
    MainActivity mainActivity;
    Context context;
    float MyCupPoint= 0.0f;
    Button btn_test;
    TextView tv_cuppoint,tv_username;
    public int cuppoint=0;
    private ImageButton btn_point_alarm, btn_mypage;
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    LottieAnimationView animationView;


    String month, day;
    public int changed_point;
    public Date currentTime;
    public SimpleDateFormat dayFormat, monthFormat;
    ArrayList<AlarmItem> arrayList=new ArrayList<>();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity=(MainActivity)getActivity();
        System.out.println("프래그먼트 홈붙었다");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity=null;
        System.out.println("프래그먼트 홈 떨어졌다");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.main_fragment, container, false);

        btn_test=(Button)rootView.findViewById(R.id.btn_test);
        btn_mypage=(ImageButton)rootView.findViewById((R.id.btn_mypage));
        btn_point_alarm=(ImageButton)rootView.findViewById(R.id.btn_alarm);
        tv_cuppoint=(TextView)rootView.findViewById(R.id.tv_cuppoint);
        tv_username=(TextView)rootView.findViewById(R.id.tv_username);

        context=container.getContext();

        Bundle bundle=getArguments();

        if(bundle!=null) {
            username = bundle.getString("name");
            cuppoint=bundle.getInt("point");
            uid=bundle.getString("uid");
            //int->String 형으로 변환
            String cuppoint_str="cuppoint:"+Integer.toString(cuppoint);
            String username_str=username+"님의 환경포인트";
            tv_cuppoint.setText(cuppoint_str);
            tv_username.setText(username_str);
            System.out.println("----------------" + bundle);

        }
        else{
            System.out.println("번들값 없다.");
        }

        uid=firebaseUser.getUid();

        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyPageActivity.class);
                startActivity(intent);
            }
        });

        animationView=(LottieAnimationView) rootView.findViewById(R.id.like_btn);

        //컵포인트에따라 식물성장 표현정도
        if(cuppoint<=10&&cuppoint>0) {
            MyCupPoint=0.1f;
            plant();
        }
        else if(cuppoint<=20&&cuppoint>10) {
            MyCupPoint=0.2f;
            plant();
        }
        else if(cuppoint<=30&&cuppoint>20) {
            MyCupPoint=0.3f;
            plant();
        }
        else if(cuppoint<=40&&cuppoint>30) {
            MyCupPoint=0.4f;
            plant();
        }
        else if(cuppoint<=50&&cuppoint>40) {
            MyCupPoint=0.5f;
            plant();
        }
        else if(cuppoint<=60&&cuppoint>50) {
            MyCupPoint=0.6f;
            plant();
        }
        else if(cuppoint<=70&&cuppoint>60) {
            MyCupPoint=0.7f;
            plant();
        }
        else if(cuppoint<=80&&cuppoint>70) {
            MyCupPoint=0.8f;
            plant();
        }
        else if(cuppoint<=90&&cuppoint>80) {
            MyCupPoint=0.9f;
            plant();
        }
        else if(cuppoint<=100&&cuppoint>90) {
            MyCupPoint=1.0f;
            plant();
        }




        //QR을 찍으면 포인트 적립 대신해서 확인하고 있는 test_btn
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyCupPoint+=0.05f;
                cuppoint+=1;//*(나중에설정)파이어베이스에서 값 불러와야함
                tv_cuppoint.setText("cuppoint:"+cuppoint);
                String cuppoint_str=Integer.toString(cuppoint);

                //버튼을 눌렀을 때 파이어베이스에도 값이 저장될 수있도록 설정
                System.out.println("데이터 업데이트");
                databaseReference.child("Users2").child(uid).child("point").setValue(cuppoint_str);
            }
        });

        databaseReference.child("Users2").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo=snapshot.getValue(UserInfo.class);
                changed_point=Integer.parseInt(userInfo.getPoint());
                Log.d(TAG,"point!!!!: "+changed_point);


                currentTime = Calendar.getInstance().getTime();
                dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                monthFormat = new SimpleDateFormat("MM", Locale.getDefault());

                month = monthFormat.format(currentTime);
                day = dayFormat.format(currentTime);
                Log.d(TAG, month + " " + day);

                arrayList.add(new AlarmItem("1point가 적립되었습니다.",month, day));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        if(getArguments()!=null){
            username=getArguments().getString("userEmail");
            System.out.println(username);

        }
        return rootView;
    }

    public void plant(){

        ValueAnimator animator = ValueAnimator.ofFloat(0f, MyCupPoint).setDuration(1000);
        animator.addUpdateListener(animation -> {
            animationView.setProgress((Float) animation.getAnimatedValue());
        });
        animator.start();
    }
}
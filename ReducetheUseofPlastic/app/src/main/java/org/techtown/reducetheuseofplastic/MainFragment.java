package org.techtown.reducetheuseofplastic;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainFragment extends Fragment {

    String username,uid;
    MainActivity mainActivity;
    Context context;
    float MyCupPoint= 0.0f;
    Button btn_test;
    TextView tv_cuppoint,tv_username;
    public int cuppoint=0;
    private ImageButton btn_point_alarm, btn_mypage;
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    LottieAnimationView animationView;
    private GradientDrawable drawable;
    private FirebaseStorage storage=FirebaseStorage.getInstance();
    private StorageReference reference=storage.getReference();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

            //사용자 프로필 사진이 설정되어 있을 경우 설정해주기
            StorageReference path=reference.child(uid);
            if(path!=null){
                StorageReference setreference=path.child("/1.png");
                setreference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onSuccess(Uri uri) {
                        //Glide라이브러리에서 제공해주는 이미지 라운딩 처리(참고사이트 : https://gwi02379.tistory.com/10)
                        Glide.with(MainFragment.this).load(uri).apply(RequestOptions.bitmapTransform(new RoundedCorners(200))).into(btn_mypage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"사용자이미지 업로드를 실패했습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
        else{
            System.out.println("번들값 없다.");
        }

        btn_mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyPageActivity.class);
                startActivity(intent);
            }
        });
/*
        btn_point_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("toast","1");
                LayoutInflater layoutInflater=getLayoutInflater();
                View view_l=inflater.inflate(R.layout.point_save, (ViewGroup)getView().findViewById(R.id.toast_layout));
                Log.d("toast","2");
                TextView tv_toast_point=view_l.findViewById(R.id.tv_toast_point);
                Toast toast=new Toast(context);
                Log.d("toast","3");
                tv_toast_point.setText("??점이 적립되었습니다.");
                tv_toast_point.setTextSize(15);
                tv_toast_point.setTextColor(Color.BLACK);
                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(view_l);
                toast.show();
            }
        });

 */

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
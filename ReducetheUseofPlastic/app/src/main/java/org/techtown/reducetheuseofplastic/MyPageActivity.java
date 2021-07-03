package org.techtown.reducetheuseofplastic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MyPageActivity extends AppCompatActivity {

    private ImageView img_qr;
    private String uid, username , userEmail;
    private String point;
    private TextView tv_id, tv_lv, tv_point;
    private Button btn_setting, btn_back;
    private DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        img_qr=(ImageView)findViewById(R.id.img_qrcode);
        tv_id=(TextView)findViewById(R.id.mypage_id);
        tv_lv=(TextView)findViewById(R.id.tv_mypage_rank);
        tv_point=(TextView)findViewById(R.id.tv_mypage_point);
        btn_setting=(Button)findViewById(R.id.btn_setting);
        btn_back=(Button)findViewById(R.id.btn_back);
        uid=user.getUid();

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyPageBottomSheetDialog myPageBottomSheetDialog=new MyPageBottomSheetDialog();
                myPageBottomSheetDialog.show(getSupportFragmentManager(),"bottomSheet");
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //qr코드
        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        try{
            BitMatrix bitMatrix=multiFormatWriter.encode(userEmail, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
            img_qr.setImageBitmap(bitmap);
        }catch (Exception e){

        }

        rootRef.child("Users2").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInfo userInfo=dataSnapshot.getValue(UserInfo.class);
                username=userInfo.getName();
                point=userInfo.getPoint();

                tv_id.setText(username);
                tv_point.setText(""+point);
                tv_lv.setText("Lv." +check_level(point));
                System.out.println("nickname: "+username);
                System.out.println("point: "+point+"p");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("errer: "+error);
            }
        });


    }

    public String check_level(String ppoint){
        int point=Integer.parseInt(ppoint);
        if(point<=100) return "1";
        else if(point>100 && point<=200) return "2";
        else if(point >200 && point<=300) return "3";
        else return "4";
    }

}
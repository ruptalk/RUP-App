package org.techtown.reducetheuseofplastic;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MyPageActivity extends AppCompatActivity {

    private ImageView img_qr;
    private String userEmail;
    MainActivity mainActivity;
    private TextView tv_id, tv_lv, tv_point;
    private Button btn_setting, btn_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        img_qr=(ImageView)findViewById(R.id.img_qrcode);
        tv_id=(TextView)findViewById(R.id.mypage_id);
        tv_lv=(TextView)findViewById(R.id.mypage_rank);
        tv_point=(TextView)findViewById(R.id.tv_mypage_point);
        btn_setting=(Button)findViewById(R.id.btn_setting);
        btn_back=(Button)findViewById(R.id.btn_back);


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
        
/*
        if(getArguments()!=null){
            userEmail=getArguments().getString("userEmail");
            System.out.println(userEmail);
            tv_id.setText(userEmail);
            //tv_lv.setText("Lv.5");//일단 정적으로 고정

        }

 */


        System.out.println("야야야야야양" + userEmail);

        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        try{
            BitMatrix bitMatrix=multiFormatWriter.encode(userEmail, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
            Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
            img_qr.setImageBitmap(bitmap);
        }catch (Exception e){

        }

    }


}
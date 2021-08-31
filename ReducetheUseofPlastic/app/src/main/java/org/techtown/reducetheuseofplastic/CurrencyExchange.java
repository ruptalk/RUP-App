package org.techtown.reducetheuseofplastic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CurrencyExchange extends AppCompatActivity {

    ImageButton imgbtn_market;
    TextView tv_explain,tv_point;
    int count=-1;
    Dialog exchagedialog;
    DatabaseReference mdatabase= FirebaseDatabase.getInstance().getReference();
    private String uid,useremail,str_point,bank,str_account;
    private int point;
    private String arr[]={"안녕하세요 환경포인트 환전소입니다.",
            "일정 환경 포인트가 모일 수록 환불받을 수 있는 수수료가 낮아 집니다.",
            "환전된 캐쉬를 받는데까지 대략 2~3일 시간이 소요가 됩니다.",
            "자 얼만큼 환전 하실 것인가요?"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_exchange);

        imgbtn_market=(ImageButton)findViewById(R.id.imgbtn_market);
        tv_explain=(TextView)findViewById(R.id.tv_explain);
        tv_point=(TextView)findViewById(R.id.tv_point);
        exchagedialog=new Dialog(CurrencyExchange.this);
        exchagedialog.setContentView(R.layout.currency_exchange_dialog);

        Intent intent_value=getIntent();
        uid=intent_value.getStringExtra("useruid");
        mdatabase.child("Users2").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot=task.getResult();
                UserInfo userInfo=dataSnapshot.getValue(UserInfo.class);
                useremail=userInfo.getEmail();
                str_point=userInfo.getPoint();
                str_account=userInfo.getAccount();
                bank=userInfo.getBank();
                tv_point.setText(str_point);
                point=Integer.parseInt(str_point);
            }
        });

        System.out.println(point);
        System.out.println("결과아아아아ㅏㅇ");




        //상점버튼을 눌렀을 때
        imgbtn_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTextViewClicked();
            }
        });
        tv_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;

                if(count<=3) {
                    tv_explain.setText(arr[count]);
                }
                else if(count==4){
                    showDialog();
                }
            }
        });

    }
    public void onTextViewClicked (){
        //해당 사이트 참고 : https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=0677haha&logNo=60189825261
        count++;

        if(count<=3) {
            tv_explain.setText(arr[count]);
        }
        else if(count==4){
            showDialog();
        }

    }
    protected void showDialog(){
        exchagedialog.show();
        Button btn_cup1=exchagedialog.findViewById(R.id.btn_cup1);
        Button btn_cup2=exchagedialog.findViewById(R.id.btn_cup2);
        Button btn_cup3=exchagedialog.findViewById(R.id.btn_cup3);

        mdatabase.child("Users2").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo=snapshot.getValue(UserInfo.class);
                useremail=userInfo.getEmail();
                str_point=userInfo.getPoint();
                str_account=userInfo.getAccount();
                bank=userInfo.getBank();
                point=Integer.parseInt(str_point);


                btn_cup1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(point>=10) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(CurrencyExchange.this);
                            dialog.setTitle("확실하죠!?");
                            dialog.setMessage("10개 교환을 선택하셨습니다 900원 교환이 가능합니다.맞습니까?");
                            dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    exchagedialog.dismiss();
                                    count = -1;
                                    point=point-10;
                                    str_point=Integer.toString(point);
                                    tv_point.setText(str_point);
                                    mdatabase.child("Users2").child(uid).child("point").setValue(str_point);
                                    //반환신청 데이터 보내기
                                    UserinfoExchage userinfoExchage=new UserinfoExchage(useremail,bank,10,Integer.parseInt(str_account),false);
                                    mdatabase.child("exchange").child(uid).setValue(userinfoExchage);
                                    tv_explain.setText("환전작업은 약 2~3일 소요됩니다. 감사합니다 또뵙겠습니다.");
                                    Toast.makeText(CurrencyExchange.this, "정상적으로 접수 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(CurrencyExchange.this, "다시 선택해주세요", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.show();
                        }
                        else{
                            int temp=10-point;
                            Toast.makeText(getApplicationContext(),"포인트가"+temp+"개 부족합니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //컵 50개
                btn_cup2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(point>=50) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(CurrencyExchange.this);
                            dialog.setTitle("확실하죠!?");
                            dialog.setMessage("50개 교환을 선택하셨습니다 4,250원 교환이 가능합니다.맞습니까?");
                            dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    exchagedialog.dismiss();
                                    count = -1;
                                    point=point-50;
                                    str_point=Integer.toString(point);
                                    tv_point.setText(str_point);
                                    mdatabase.child("Users2").child(uid).child("point").setValue(str_point);
                                    //반환신청 데이터 보내기
                                    UserinfoExchage userinfoExchage=new UserinfoExchage();
                                    userinfoExchage.account=Integer.parseInt(str_account);
                                    userinfoExchage.bank=bank;
                                    userinfoExchage.check=false;
                                    userinfoExchage.point=50;
                                    userinfoExchage.useremail=useremail;
                                    mdatabase.child("exchange").child(uid).setValue(userinfoExchage);
                                    tv_explain.setText("환전작업은 약 2~3일 소요됩니다. 감사합니다 또뵙겠습니다.");
                                    Toast.makeText(CurrencyExchange.this, "정상적으로 접수 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(CurrencyExchange.this, "다시 선택해주세요", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.show();
                        }
                        else{
                            int temp=50-point;
                            Toast.makeText(getApplicationContext(),"포인트가"+temp+"개 부족합니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //컵 100개
                btn_cup3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(point>=100) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(CurrencyExchange.this);
                            dialog.setTitle("확실하죠!?");
                            dialog.setMessage("100개 교환을 선택하셨습니다 10,000원 교환이 가능합니다.맞습니까?");
                            dialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    exchagedialog.dismiss();
                                    count = -1;
                                    point=point-100;
                                    str_point=Integer.toString(point);
                                    tv_point.setText(str_point);
                                    mdatabase.child("Users2").child(uid).child("point").setValue(str_point);
                                    //반환신청 데이터 보내기
                                    UserinfoExchage userinfoExchage=new UserinfoExchage(useremail,bank,100,Integer.parseInt(str_account),false);
                                    mdatabase.child("exchange").child(uid).setValue(userinfoExchage);
                                    tv_explain.setText("환전작업은 약 2~3일 소요됩니다. 감사합니다 또뵙겠습니다.");
                                    Toast.makeText(CurrencyExchange.this, "정상적으로 접수 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(CurrencyExchange.this, "다시 선택해주세요", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialog.show();
                        }
                        else{
                            int temp=100-point;
                            Toast.makeText(getApplicationContext(),"포인트가"+temp+"개 부족합니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }
}
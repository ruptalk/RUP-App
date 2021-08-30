package org.techtown.reducetheuseofplastic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyPageBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener{

    public static MyPageBottomSheetDialog getInstance() { return new MyPageBottomSheetDialog(); }

    private DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

    private Button btn_modify, btn_point_return, btn_notice, btn_service, btn_logout;
    private String uid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mypage_bottomsheet,container, false);

        btn_modify=(Button)view.findViewById(R.id.btn_modify);
        btn_point_return=(Button)view.findViewById(R.id.btn_point_return);
        btn_notice=(Button)view.findViewById(R.id.btn_notice);
        btn_service=(Button)view.findViewById(R.id.btn_service);
        btn_logout=(Button)view.findViewById(R.id.btn_logout);

        btn_modify.setOnClickListener(this);
        btn_point_return.setOnClickListener(this);
        btn_notice.setOnClickListener(this);
        btn_service.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

        uid=user.getUid();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_modify:
                Toast.makeText(getContext(),"개인정보수정", Toast.LENGTH_SHORT).show();
                final EditText editText=new EditText(getActivity());
                editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                dialog.setTitle("비밀번호를 입력하세요.");
                dialog.setView(editText);
                dialog.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password=editText.getText().toString();
                        System.out.println("edit_pw: "+password);

                        rootRef.child("Users2").child(uid).child("pw").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String value=dataSnapshot.getValue(String.class);
                                System.out.println("pw: "+value);

                                if(password.equals(value)) {
                                    Intent intent=new Intent(getActivity(), UserInformationActivity.class);
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(getContext(),"비밀번호가 맞지 않습니다. 다시 입력해주세요.",Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }).setNegativeButton("취소",null).show();
                break;
            case R.id.btn_point_return://포인트 반환하기 페이지
                Intent intent= new Intent(getActivity(),CurrencyExchange.class);
                intent.putExtra("useruid",uid);
                startActivity(intent);
                break;
            case R.id.btn_notice:
                Toast.makeText(getContext(),"공지사항",Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_service:
                Toast.makeText(getContext(),"고객센터",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_logout:
                Toast.makeText(getContext(),"로그아웃",Toast.LENGTH_SHORT).show();
                break;
        }
    }




}




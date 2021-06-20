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

public class MyPageBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener{

    public static MyPageBottomSheetDialog getInstance() { return new MyPageBottomSheetDialog(); }

    private Button btn_modify, btn_point_return, btn_notice, btn_service, btn_logout;
    int res=1;

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

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_modify:
                Toast.makeText(getContext(),"개인정보수정", Toast.LENGTH_SHORT).show();
                final EditText editText=new EditText(getActivity());
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                dialog.setTitle("비밀번호를 입력하세요.");
                dialog.setView(editText);
                dialog.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password=editText.getText().toString();
                        /*
                        여기에 파이어베이스에서 비밀번호 맞는지 확인
                         */
                        //비번 맞으면
                        res=0;
                        //비번 틀리면
                        res=1;
                        if(res==1){
                            Intent intent=new Intent(getActivity(), UserInformationActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getContext(),"비밀번호가 맞지 않습니다. 다시 입력해주세요.",Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }
                }).setNegativeButton("취소",null).show();
                break;
            case R.id.btn_point_return:
                Toast.makeText(getContext(),"포인트 반환하기",Toast.LENGTH_SHORT).show();
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




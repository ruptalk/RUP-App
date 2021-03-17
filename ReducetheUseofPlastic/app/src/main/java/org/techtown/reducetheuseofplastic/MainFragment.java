package org.techtown.reducetheuseofplastic;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    String userEmail;
    MainActivity mainActivity;
    Context context;
    private ImageButton plant;
    private int point;
    Dialog dialog_plant;

    private void point_plant(){

        Log.d("MainFragment","check22");
        if(point<=100){
            plant.setImageResource(R.drawable.plant1);
        }
        else if(point>100&&point<=150){
            plant.setImageResource(R.drawable.plant2);
        }
        else if(point>150&&point<=200){
            plant.setImageResource(R.drawable.plant3);
        }
        else if(point>200&&point<=250){
            plant.setImageResource(R.drawable.plant4);
        }
        else if(point>250&&point<=300){
            plant.setImageResource(R.drawable.plant5);
        }
        else if(point>300&&point<=350){
            plant.setImageResource(R.drawable.plant6);
        }
        else if(point>350&&point<=400){
            plant.setImageResource(R.drawable.plant7);
        }
        else if(point>400&&point<=450){
            plant.setImageResource(R.drawable.plant8);
        }
        else if(point>450&&point<=500){
            plant.setImageResource(R.drawable.plant9);
        }
        else if(point>500&&point<=550){
            plant.setImageResource(R.drawable.plant10);
        }
        else if(point>600&&point<=650){
            plant.setImageResource(R.drawable.plant11);
        }
        else if(point>650&&point<=700){
            plant.setImageResource(R.drawable.plant12);
        }
        else if(point>700&&point<=750){
            plant.setImageResource(R.drawable.plant13);
        }
        else if(point>750&&point<=800){
            plant.setImageResource(R.drawable.plant14);
        }
        else if(point>800&&point<=850){
            plant.setImageResource(R.drawable.plant15);
        }
        else if(point>850&&point<=900){
            plant.setImageResource(R.drawable.plant16);
        }
        else{
            plant.setImageResource(R.drawable.plant17);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity=(MainActivity)getActivity();
        System.out.println("프래그먼트 홈");
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


        plant=(ImageButton)rootView.findViewById(R.id.image_plant);

        //dialog_plant=new Dialog(MainFragment.this);
        //dialog_plant.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog_plant.setContentView(R.layout.dialog_plant);
        point=987;
        point_plant();

        plant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"이미지 클릭됨!!",Toast.LENGTH_SHORT).show();

                Bundle args=new Bundle();
                args.putString("key","value");
                PlantDialogFragment dialog=new PlantDialogFragment();
                dialog.setArguments(args);
                System.out.println("MainFragment 이건 실행되나?");
                dialog.show(getActivity().getSupportFragmentManager(),"tag");
            }
        });



        if(getArguments()!=null){
            userEmail=getArguments().getString("userEmail");
            System.out.println(userEmail);

        }
        return rootView;
    }
/*
    public void showDialogPlant(){
        Button btn_fin=dialog_plant.findViewById(R.id.btn_dialog_plant);
        btn_fin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog_plant.dismiss();
            }
        });
    }

 */
}
package org.techtown.reducetheuseofplastic;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.ListFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RankFragment extends Fragment {
    
    MainActivity mainActivity;
    static final String[] ListMenu={"박소영","park so young","하우"};

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    private ChildEventListener mChildEventListener;


    private ListView listView;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity=(MainActivity)getActivity();
        System.out.println("랭킹시스템 프래그먼트 붙었다");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity=null;
        System.out.println("랭킹시스템 떨어졌다");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.rank, container, false);
        listView=(ListView)rootView.findViewById(R.id.listView_rank);
        ArrayList<UserIdPoint>arrayList=new ArrayList<>();
        RankFragmentAdapter adapter=new RankFragmentAdapter(getContext(),R.layout.rand_item,arrayList);
        listView.setAdapter(adapter);

        mDatabase=FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    System.out.println(task.getException());
                }else{
                    Object item=task.getResult().getValue();
                    System.out.println(item);

                }
            }
        });
        return rootView;
    }

}
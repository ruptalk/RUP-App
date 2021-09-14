package org.techtown.reducetheuseofplastic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmFragment extends Fragment {

    private ListView listview;
    private AlarmAdapter alarmAdapter;

    private String uid, TAG="AlarmFragment";
    public int point, changed_point;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference;

    public AlarmFragment(){ }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.alarm, container,false);

        listview=(ListView)view.findViewById(R.id.listview_alarm);
        alarmAdapter=new AlarmAdapter();

        uid=firebaseUser.getUid();

        //addValueEventListener : 실시간으로 데이터베이스의 값 변화 감지해서
        // 변화가 있다면 onDataChange를 호출해주므로 전역변수 값도 그때그때 바뀜.
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users2").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo=snapshot.getValue(UserInfo.class);
                changed_point=Integer.parseInt(userInfo.getPoint());
                Log.d(TAG,"point!!!!: "+changed_point);

                Date currentTime= Calendar.getInstance().getTime();
                SimpleDateFormat dayFormat=new SimpleDateFormat("dd", Locale.getDefault());
                SimpleDateFormat monthFormat=new SimpleDateFormat("MM",Locale.getDefault());
                SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy",Locale.getDefault());

                String year=yearFormat.format(currentTime);
                String month=monthFormat.format(currentTime);
                String day=monthFormat.format(currentTime);
                Log.d(TAG,year+" "+month+" "+day);

                alarmAdapter.addItem_alarm("10point 적립되었습니다.", year, month, day);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        listview.setAdapter(alarmAdapter);
        return view;
    }
}
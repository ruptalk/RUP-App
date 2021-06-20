package org.techtown.reducetheuseofplastic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AlarmFragment extends Fragment {

    private ListView listview;
    private AlarmAdapter alarmAdapter;

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


        for(int i=1; i<10; i++){
            alarmAdapter.addItem_alarm("10point 적립되었습니다.",""+i+"일");
        }

        listview.setAdapter(alarmAdapter);
        return view;
    }
}
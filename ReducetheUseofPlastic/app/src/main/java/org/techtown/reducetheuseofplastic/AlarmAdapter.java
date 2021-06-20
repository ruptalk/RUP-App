package org.techtown.reducetheuseofplastic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class AlarmAdapter extends BaseAdapter {
    ArrayList<AlarmItem> alarmItems=new ArrayList<AlarmItem>();

    @Override
    public int getCount() {
        return alarmItems.size();
    }

    @Override
    public AlarmItem getItem(int position) {
        return alarmItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv_alarm_content, tv_alarm_date;
        Context context=parent.getContext();

        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.alarm_custom_listview, parent, false);
        }

        tv_alarm_content=convertView.findViewById(R.id.tv_alarm_content);
        tv_alarm_date=convertView.findViewById(R.id.tv_alarm_date);

        //각 리스트에 뿌려줄 아이템 받아오는데 alarmItem 재활용
        AlarmItem alarmItem=getItem(position);

        tv_alarm_content.setText(alarmItem.getContent());
        tv_alarm_date.setText(alarmItem.getDate());

        return convertView;
    }

    public void addItem_alarm(String content, String date){
        AlarmItem alarmItem=new AlarmItem();
        alarmItem.setContent(content);
        alarmItem.setDate(date);
        alarmItems.add(alarmItem);
    }
}

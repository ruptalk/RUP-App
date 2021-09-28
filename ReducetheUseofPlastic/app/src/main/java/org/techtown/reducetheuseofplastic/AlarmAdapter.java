package org.techtown.reducetheuseofplastic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.view.View.inflate;

//public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.Holder>{
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    ArrayList<AlarmItem> arrayList;

    public AlarmAdapter(Context context, ArrayList<AlarmItem> list){
        arrayList=list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView contentView;
        public TextView dateView;

        public ViewHolder(View view){
            super(view);
            contentView=(TextView)view.findViewById(R.id.tv_alarm_content);
            dateView=(TextView)view.findViewById(R.id.tv_alarm_date);
            contentView.setText("");
            dateView.setText("");
        }

        public TextView getContentView(){
            return contentView;
        }

        public TextView getDateView(){
            return dateView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_custom_listview, parent, false);

        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    //실제 각 뷰 홀더(position)에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.contentView.setText("");
        //holder.dateView.setText("");

        holder.contentView.setText(arrayList.get(position).content);
        holder.dateView.setText(arrayList.get(position).month+"월 "+arrayList.get(position).day+"일");

    }

    //리사이클러뷰 안에 들어갈 뷰 홀더의 개수
    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    /*

    @NonNull
    @Override
    public AlarmAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm, parent, false);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.Holder holder, int position) {
        //AlarmItem item=arrayList.get(position);
        int itemPosition=position;

        holder.contentText.setText(arrayList.get(itemPosition).getContent());
        holder.dateText.setText(arrayList.get(itemPosition).getMonth()+"월 "+arrayList.get(itemPosition).getDay()+"일");
        //holder.contentText.setText(item.getContent());
        //holder.dateText.setText(item.getMonth()+"월 "+item.getDay()+"일");

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView contentText, dateText;

        public Holder(View view){
            super(view);
            contentText=(TextView)view.findViewById(R.id.tv_alarm_content);
            dateText=(TextView)view.findViewById(R.id.tv_alarm_date);
        }
    }

     */
}

/*
public class AlarmAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<AlarmItem> arrayList;

    public AlarmAdapter(ArrayList<AlarmItem> list){
        arrayList=list;
    }


    //리사이클러뷰에 들어갈 뷰 홀더를 할당하는 함수
    //뷰 홀더는 실제 레이아웃 파일과 매핑되어야하며, extends의 Adapter<>에서 <>안에 들어가는 타입을 따름.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=inflater.inflate(R.layout.alarm, parent, false);
        ViewHolder viewholer=new ViewHolder(context, view);

        return viewholer;
    }

    //실제 각 뷰 홀더(position)에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlarmItem item=arrayList.get(position);

        holder.content.setText(item.getContent());
        holder.date.setText(item.getMonth()+"월 "+item.getDay()+"일");
    }

    //리사이클러뷰 안에 들어갈 뷰 홀더의 개수
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

 */

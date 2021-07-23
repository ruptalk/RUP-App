package org.techtown.reducetheuseofplastic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class RankFragmentAdapter extends BaseAdapter {
    private ArrayList<RankItem> listViewList=new ArrayList<RankItem>();
    private LayoutInflater layoutInflater;
    private int layout;

    public RankFragmentAdapter(){

    }
    @Override
    public int getCount() {
        return listViewList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context=viewGroup.getContext();
        if(view==null){
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.rank_item,viewGroup,false);
        }
        TextView id=(TextView)view.findViewById(R.id.tv_num);
        TextView user_id=(TextView)view.findViewById(R.id.tv_id);
        TextView user_point=(TextView) view.findViewById(R.id.tv_point);

        id.setText(String.valueOf(listViewList.get(i).getId()));
        user_id.setText(listViewList.get(i).getUser_id());
        user_point.setText(String.valueOf(listViewList.get(i).getUser_point())+"POINT");

        return view;
    }

    public void addItem(RankItem item){
        item.setId(item.getId());
        item.setUser_id(item.getUser_id());
        item.setUser_point(item.getUser_point());
        listViewList.add(item);
    }

}

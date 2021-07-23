package org.techtown.reducetheuseofplastic;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankFragment extends Fragment {
    
    MainActivity mainActivity;
    static final String[] ListMenu={"박소영","park so young","하우"};

    private DatabaseReference mDatabase;
    private ChildEventListener childEventListener;

    private ListView listView;
    private ArrayList<RankItem> itemlist = new ArrayList<RankItem>();;
    private RankFragmentAdapter adapter;


    public int i=0;




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
        adapter=new RankFragmentAdapter();
        itemlist.clear();
        listView.setAdapter(adapter);



        for( i=0;i<10;i++){
            RankItem item=new RankItem();
            item.setId(i);
            item.setUser_id("박소영"+i);
            item.setUser_point(i+1);
            itemlist.add(item);
            //adapter.addItem(item);
        }


        //파이어 베이스에서 사용자들의 정보 모두 들고 와서 추가하기
        mDatabase=FirebaseDatabase.getInstance().getReference("Users2");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren()){
                    UserInfo user=data.getValue(UserInfo.class);
                    //System.out.println(userinfo);
                    RankItem item2=new RankItem();
                    item2.setId(i);
                    item2.setUser_id(user.getName());
                    item2.setUser_point(Integer.parseInt(user.getPoint()));
                    itemlist.add(item2);
                    //adapter.addItem(item2);
                    //adapter.addItem(i,user.getName(),Integer.parseInt(user.getPoint()));
                    //adapter.notifyDataSetChanged();
                    System.out.println(i);
                    i++;
                }

                Comparator<RankItem> comparator=new Comparator<RankItem>() {
                    @Override
                    public int compare(RankItem item1, RankItem item2) {
                        int ret=0;
                        if(item1.getUser_point()<item2.getUser_point()){
                            ret=1;
                        }
                        else if(item1.getUser_point()==item2.getUser_point()){
                            ret=0;
                        }
                        else{
                            ret=-1;
                        }
                        return ret;
                    }
                };
                Collections.sort(itemlist,comparator);
                for(int i=0;i<itemlist.size();i++){
                    adapter.addItem2(itemlist.get(i),i);
                }
                //Collections.reverse(itemlist);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return rootView;
    }

    private void initDatabase(){
        childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.addValueEventListener((ValueEventListener) childEventListener);

    }
    public void sortRank(){
        Comparator<RankItem> comparator=new Comparator<RankItem>() {
            @Override
            public int compare(RankItem item1, RankItem item2) {
                int ret;
                if(item1.getUser_point()<item2.getUser_point()){
                    ret=1;
                }
                else if(item1.getUser_point()==item2.getUser_point()){
                    ret=0;
                }
                else{
                    ret=-1;
                }
                return ret;
            }
        };
        Collections.sort(itemlist,comparator);
        //Collections.reverse(itemlist);
        adapter.notifyDataSetChanged();

    }

}
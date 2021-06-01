package org.techtown.reducetheuseofplastic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class FragmentDialog extends DialogFragment {

    private Fragment fragment;
    public FragmentDialog(){ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.notice1, container, false);

        Bundle args=getArguments();
        String value=args.getString("key");
        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("tag");

        if(fragment!=null){
            DialogFragment dialogFragment=(DialogFragment)fragment;
            dialogFragment.dismiss();
        }

        return view;
    }
}

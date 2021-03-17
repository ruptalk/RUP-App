package org.techtown.reducetheuseofplastic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class PlantDialogFragment extends DialogFragment {

    private Fragment fragment;
    public PlantDialogFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_plant,container,false);

        Bundle args=getArguments();
        String value=args.getString("key");
        System.out.println("plantDialogFragment1");

        fragment=getActivity().getSupportFragmentManager().findFragmentByTag("tag");
        System.out.println("plantDialogFragment2");

        if(fragment!=null){
            PlantDialogFragment plantDialogFragment=(PlantDialogFragment) fragment;
            plantDialogFragment.dismiss();
        }
        System.out.println("plantDialogFragment3");
        return view;
    }
}

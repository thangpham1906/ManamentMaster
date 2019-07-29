package vn.techkids.freemusic11.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import vn.techkids.freemusic11.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NhapFragment extends Fragment {


    public NhapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nhap, container, false);
        Toast.makeText(getContext(), "Nhap ", Toast.LENGTH_SHORT).show();
        return view;
    }

}

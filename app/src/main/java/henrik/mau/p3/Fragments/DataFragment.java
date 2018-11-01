package henrik.mau.p3.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import henrik.mau.p3.Controller.Controller;
import henrik.mau.p3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {
    private Controller controller;
    private String activeFragment;


    public DataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        return view;
    }

    public void setActiveFragment(String tag){
        activeFragment = tag;
    }

    public String getActiveFragment(){
        return  activeFragment;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }



}

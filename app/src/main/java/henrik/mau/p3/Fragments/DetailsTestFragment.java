package henrik.mau.p3.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import henrik.mau.p3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsTestFragment extends Fragment {


    public DetailsTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_test, container, false);
    }

}

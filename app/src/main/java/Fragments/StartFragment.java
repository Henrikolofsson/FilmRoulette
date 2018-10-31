package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import henrik.mau.p3.Controller;
import henrik.mau.p3.MainActivity;
import henrik.mau.p3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    private Controller controller;
    private Spinner spinnerGenre;
    private Spinner spinnerScore;
    private static String[] genres = new String[]{"Action & Adventure", "Animation", "Anime", "Biography", "Children",
                                                    "Comedy", "Crime", "Cult", "Documentary", "Drama", "Family", "Fantasy",
                                                        "Food", "Game Show", "History", "Home & Garden", "Horror", "Independent",
                                                            "LGBTQ", "Musical", "Mystery", "Reality", "Romance", "Science-Fiction",
                                                                "Sport", "Standup & Talk", "Thriller", "Travel"};

    private static String[] scores = new String[]{"> 9", "> 8", "> 7", "> 6", "> 5", "Any Score"};

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        initializeComponents(view);
        ArrayAdapter<String> spinnerGenreAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genres);
        spinnerGenre.setAdapter(spinnerGenreAdapter);
        ArrayAdapter<String> spinnerScoreAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, scores);
        spinnerScore.setAdapter(spinnerScoreAdapter);
        return view;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    private void initializeComponents(View view) {
        spinnerGenre = (Spinner) view.findViewById(R.id.spinnerGenre);
        spinnerScore = (Spinner) view.findViewById(R.id.spinnerScore);
    }

}

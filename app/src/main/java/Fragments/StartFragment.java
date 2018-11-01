package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import henrik.mau.p3.Controller;
import henrik.mau.p3.MainActivity;
import henrik.mau.p3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    private Controller controller;
    private Spinner spinnerGenre;
    private Spinner spinnerRating;
    private Spinner spinnerLength;
    private Switch switch18;
    private Button btnRoulette;
    private static String[] genres = new String[]{"Action & Adventure", "Animation", "Anime", "Biography", "Children",
                                                    "Comedy", "Crime", "Cult", "Documentary", "Drama", "Family", "Fantasy",
                                                        "Food", "Game Show", "History", "Home & Garden", "Horror", "Independent",
                                                            "LGBTQ", "Musical", "Mystery", "Reality", "Romance", "Science-Fiction",
                                                                "Sport", "Standup & Talk", "Thriller", "Travel"};

    private static String[] rating = new String[]{"High", "Medium", "Low", "Any Rating"};
    private static String[] length = new String[]{"Long", "Medium", "Short"};

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
        ArrayAdapter<String> spinnerScoreAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, rating);
        spinnerRating.setAdapter(spinnerScoreAdapter);
        ArrayAdapter<String> spinnerLengthAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, length);
        spinnerLength.setAdapter(spinnerLengthAdapter);
        return view;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    private void initializeComponents(View view) {
        spinnerGenre = (Spinner) view.findViewById(R.id.spinnerGenre);
        spinnerRating = (Spinner) view.findViewById(R.id.spinnerScore);
        spinnerLength = (Spinner) view.findViewById(R.id.spinnerLength);
        switch18 = (Switch) view.findViewById(R.id.switch18);
        btnRoulette = (Button) view.findViewById(R.id.btnRoulette);
    }

    private int getGenreId(){
        String genre = spinnerGenre.getSelectedItem().toString();
        return 0;
    }

    private String getRating(){
        return null;
    }

    private boolean getAdultChoice(){
        return false;
    }

    private String getLength(){
        return null;
    }

    private class ButtonRouletteListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int genreId = getGenreId();
            String rating = getRating();
            boolean adultMovies = getAdultChoice();
            String getLength = getLength();
           // controller.setFilterSettings();
        }
    }

}

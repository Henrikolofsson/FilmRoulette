package henrik.mau.p3.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import henrik.mau.p3.Controller.Controller;
import henrik.mau.p3.Entity.Genres;
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
    private static String[] genres = new String[]{"Action", "Adventure", "Animation", "Comedy", "Crime",
                                                     "Documentary", "Drama", "Family", "Fantasy",
                                                        "History", "Horror", "Music", "Mystery",
                                                            "Romance", "ScienceFiction", "Thriller", "War", "Western"};

    private static String[] rating = new String[]{"High", "Medium", "Low", "Any Rating"};

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        initializeComponents(view);
        registerListeners();
        ArrayAdapter<String> spinnerGenreAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genres);
        spinnerGenre.setAdapter(spinnerGenreAdapter);
        ArrayAdapter<String> spinnerScoreAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, rating);
        spinnerRating.setAdapter(spinnerScoreAdapter);
        return view;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    private void registerListeners(){
        btnRoulette.setOnClickListener(new ButtonRouletteListener());
    }

    private void initializeComponents(View view) {
        spinnerGenre = (Spinner) view.findViewById(R.id.spinnerGenre);
        spinnerRating = (Spinner) view.findViewById(R.id.spinnerScore);
        switch18 = (Switch) view.findViewById(R.id.switch18);
        btnRoulette = (Button) view.findViewById(R.id.btnRoulette);
    }

    private int getGenreId(){
        String genre = spinnerGenre.getSelectedItem().toString();
        int genreId;
        if(genre == "Action"){
            genreId = Genres.action;
        } else if(genre == "Adventure"){
            genreId = Genres.adventure;
        } else if(genre == "Animation"){
            genreId = Genres.animation;
        } else if(genre == "Comedy"){
            genreId = Genres.comedy;
        } else if(genre == "Crime"){
            genreId = Genres.crime;
        } else if(genre == "Documentary"){
            genreId = Genres.documentary;
        } else if(genre == "Drama"){
            genreId = Genres.drama;
        } else if(genre == "Family"){
            genreId = Genres.family;
        } else if(genre == "Fantasy"){
            genreId = Genres.fantasy;
        } else if(genre == "History"){
            genreId = Genres.history;
        } else if(genre == "Horror"){
            genreId = Genres.horror;
        } else if(genre == "Music"){
            genreId = Genres.music;
        } else if(genre == "Mystery"){
            genreId = Genres.mystery;
        } else if(genre == "Romance"){
            genreId = Genres.romance;
        } else if(genre == "ScienceFiction"){
            genreId = Genres.scienceFiction;
        } else if(genre == "Thriller"){
            genreId = Genres.thriller;
        } else if(genre == "War"){
            genreId = Genres.war;
        } else if(genre == "Western"){
            genreId = Genres.western;
        } else {
            genreId = 0;
        }
        return genreId;
    }

    private String getRating(){
        return spinnerRating.getSelectedItem().toString();
    }

    private boolean getAdultMovies(){
        if(switch18.isChecked()){
            return true;
        } else {
            return false;
        }
    }

    private class ButtonRouletteListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("genre", getGenreId());
            bundle.putString("rating", getRating());
            bundle.putBoolean("adult", getAdultMovies());

            int genreId = getGenreId();
            String rating = getRating();
            boolean adultMovies = getAdultMovies();
            Log.d("CHECKVALUES", "GENREID: "+ genreId + " , RATING: "
                    + rating + " , ADULTMOVIES: " + adultMovies);

            controller.onClick(bundle);

        }
    }

}

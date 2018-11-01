package henrik.mau.p3.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;


import com.squareup.picasso.Picasso;

import henrik.mau.p3.Activity.YoutubeActivity;
import henrik.mau.p3.Controller.Controller;
import henrik.mau.p3.Entity.Movie;
import henrik.mau.p3.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    public DetailsFragment() {
        setHasOptionsMenu(true);
        // Required empty public constructor
    }


    private String poster;
    private Movie movie;

    private TextView tvOverview;
    private TextView tvTitle;
    private TextView tvRating;
    private TextView tvDate;
    private ImageView ivPoster;
    private ImageView ivSpeech;
    private Controller controller;
    private View view;
    private String trailer;
    private Button btnYoutube;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);

        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        tvTitle = view.findViewById(R.id.title);
        tvDate = view.findViewById(R.id.date);
        tvRating = view.findViewById(R.id.rating);
        tvOverview = view.findViewById(R.id.overview);
        ivPoster = view.findViewById(R.id.poster);
        ivSpeech = view.findViewById(R.id.ivSpeech);
        ivSpeech.setImageResource(R.drawable.speakericon);
        btnYoutube = view.findViewById(R.id.trailer);

        btnYoutube.setOnClickListener((View v) ->{
            Intent intent = new Intent(getActivity(),YoutubeActivity.class);
            String message = trailer;
            System.out.println(trailer);
            intent.putExtra("video_code", trailer);
            startActivity(intent);
        });

    }

    public void onResume() {
        super.onResume();
        poster = movie.getPoster();
        System.out.println(poster);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + poster).resize(
                MoviesFragment.width, (int) (MoviesFragment.width * 1.5)).into(ivPoster);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvRating.setText(movie.getRating());
        tvDate.setText(movie.getDate());
        trailer=movie.getYoutube();
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }




}

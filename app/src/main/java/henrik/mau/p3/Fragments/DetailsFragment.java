package henrik.mau.p3.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

    private int pWidth;
    private String poster;
    private Movie movie;

    private TextView tvOverview;
    private TextView tvTitle;
    private TextView tvRating;
    private TextView tvDate;
    private ImageView ivPoster;
    private ImageView ivBackdrop;
    private View view;
    private String trailer;
    private Button btnYoutube;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);
        initComponents(view);
        if (savedInstanceState != null) {
            movie = (Movie) savedInstanceState.getSerializable("movie");
            System.out.println(movie.getTitle());
            setValues();
        }
        return view;
    }

    private void initComponents(View view) {
        setWindow();
        tvTitle = view.findViewById(R.id.title);
        tvDate = view.findViewById(R.id.date);
        tvRating = view.findViewById(R.id.rating);
        tvOverview = view.findViewById(R.id.overview);
        ivPoster = view.findViewById(R.id.poster);
        ivBackdrop = view.findViewById(R.id.backdrop);
        btnYoutube = view.findViewById(R.id.trailer);
        ivPoster.setImageResource(R.drawable.black);
    }

    public void onResume() {
        super.onResume();
        Log.d("movie", movie.toString());

        setValues();
    }

    public  void setValues() {
        if (movie != null) {

            poster = movie.getPoster();
           Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + poster).resize(
                    pWidth, (int) (pWidth * 1.5)).into(ivPoster);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + movie.getBackdrop()).into(ivBackdrop);


            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            tvRating.setText(movie.getRating());
            tvDate.setText(movie.getDate());

            btnYoutube.setOnClickListener((View v) -> {
                Intent intent = new Intent(getActivity(), YoutubeActivity.class);
                System.out.println(trailer);
                trailer = movie.getYoutube();
                if (trailer != null) {
                    intent.putExtra("video_code", trailer);
                    startActivity(intent);
                }
            });
        }
    }

    private void setWindow() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        pWidth = size.x / 3;
        float density = getActivity().getResources().getDisplayMetrics().density;
        float dp = pWidth / density;
        float dp2 = (float)(pWidth*1.5)/density;
        Log.d("METRICS", "setWindow: "+dp+", "+dp2);
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("movie", movie);

    }


}

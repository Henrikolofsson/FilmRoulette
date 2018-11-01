package henrik.mau.p3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;

import Fragments.DataFragment;
import Fragments.StartFragment;

public class Controller {
    private MainActivity mainActivity;
    private DataFragment dataFragment;
    private StartFragment startFragment;
    private MoviesFragment moviesFragment;
    private MDB_API movieAPI;
    private DetailsFragment detailsFragment;
    private Movie movie;

    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        MDBController mdb = new MDBController(mainActivity, this);
        //movieAPI = new MDB_API(mainActivity, this);
        initializeDataFragment();
        initializeFragments();
    }

    private void initializeDataFragment() {
        dataFragment = (DataFragment) mainActivity.getFragment("DataFragment");
        if (dataFragment == null) {
            dataFragment = new DataFragment();
            mainActivity.addFragment(dataFragment, "DataFragment");
            dataFragment.setActiveFragment("StartFragment");
        }
        dataFragment.setController(this);
    }

    private void initializeFragments() {
        initializeStartFragment();
        initializeMoviesFragment();
        initializeDetailsFragment();
        setFragment("MoviesFragment");
    }

    private void initializeMoviesFragment() {
        moviesFragment = (MoviesFragment) mainActivity.getFragment("MoviesFragment");
        if (moviesFragment == null) {
            moviesFragment = new MoviesFragment();
        }
        moviesFragment.setController(this);

    }

    private void initializeDetailsFragment() {
        detailsFragment = (DetailsFragment) mainActivity.getFragment("DetailsFragment");
        if (detailsFragment == null) {
            detailsFragment = new DetailsFragment();
        }
        detailsFragment.setController(this);
    }

    private void initializeStartFragment() {
        startFragment = (StartFragment) mainActivity.getFragment("StartFragment");
        if (startFragment == null) {
            startFragment = new StartFragment();
        }
        startFragment.setController(this);
        addStartFragment();
    }

    private void setFragment(String tag) {
        switch (tag) {
            case "StartFragment":
                setFragment(startFragment, "StartFragment");
                break;
            case "MoviesFragment":
                setFragment(moviesFragment, "MoviesFragment");
                break;
            case "DetailsFragment":
                setFragment(detailsFragment, "DetailsFragment");
                break;
        }
    }

    private void setFragment(Fragment fragment, String tag) {
        mainActivity.setFragment(fragment, tag);
        dataFragment.setActiveFragment(tag);
    }

    public void addStartFragment() {
        setFragment("StartFragment");
    }


    public void setContent(ArrayList<String> result) {
        moviesFragment.setContent(result);
    }

    public void startImageLoadTask() {
        movieAPI.startImageLoadTask();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void showMovieDetails(int position) {
        movie = movieAPI.getMovie(position);
        System.out.println("Test");
        System.out.println(movie.getPoster());
        System.out.println(movie.getTitle());
        setFragment("DetailsFragment");
        detailsFragment.setMovie(movie);
    }


    public boolean backPressed() {
        String activeFragment = dataFragment.getActiveFragment();
        Log.d("backPressed", activeFragment);

        if (activeFragment.equals("MoviesFragment")) {
            return true;
        }
        switch (activeFragment) {
            case "DetailsFragment":
                setFragment("MoviesFragment");
                break;
        }
        return false;
    }
}

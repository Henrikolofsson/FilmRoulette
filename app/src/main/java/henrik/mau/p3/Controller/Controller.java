package henrik.mau.p3.Controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;

import henrik.mau.p3.Fragments.DetailsFragment;
import henrik.mau.p3.Fragments.DataFragment;
import henrik.mau.p3.Fragments.StartFragment;
import henrik.mau.p3.Activity.MainActivity;
import henrik.mau.p3.Entity.Movie;
import henrik.mau.p3.MDB.MDBController;

public class Controller {
    private MainActivity mainActivity;
    private DataFragment dataFragment;
    private StartFragment startFragment;
    private DetailsFragment detailsFragment;
    private Movie movie;

    private MDBController mdb;

    public Controller(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mdb = new MDBController(mainActivity, this);
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
        initializeDetailsFragment();

        setFragment(dataFragment.getActiveFragment());
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

    }

    private void setFragment(String tag) {
        switch (tag) {
            case "StartFragment":
                setFragment(startFragment, "StartFragment");
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



    public void onClick(Bundle bundle) {
        mdb.requestRandomMovie(bundle);
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        detailsFragment.setMovie(movie);
        mdb.requestYoutubeVideo(movie.getId());
        setFragment("DetailsFragment");
    }

    public boolean backPressed() {
        String activeFragment = dataFragment.getActiveFragment();
        Log.d("backPressed", activeFragment);

        if (activeFragment.equals("MoviesFragment")) {
            return true;
        }
        switch (activeFragment) {
            case "DetailsFragment":
                setFragment("StartFragment");
                break;
        }
        return false;
    }

    public void setTrailer(String result) {
        movie.setYoutube(result);
    }
}

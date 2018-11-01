package henrik.mau.p3.MDB;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

import henrik.mau.p3.Activity.MainActivity;
import henrik.mau.p3.Controller.Controller;
import henrik.mau.p3.Entity.Movie;

public class MDBController {
    private static String API_KEY = "1382cca4854a01eee1ddc83dc5102dbf";

    private Controller controller;
    private MainActivity activity;
    private MDBRequest request;

    private Random rand = new Random();
    private boolean rolled = false;
    private boolean movieIsSet = false;

    private String baseUrl = "http://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY +
            "&vote_average.gte=0" +
            "&sort_by=vote_count.desc" +
            "&include_video=true";

    public MDBController(MainActivity activity, Controller controller) {
        this.activity = activity;
        this.controller = controller;
        request = new MDBRequest(this);
    }

    public void requestRandomMovie(@Nullable Bundle filterBundle) {
        String urlString = baseUrl;
        int genre = 0;
        String rating = null;
        boolean adult = false;

        if (filterBundle != null) {
            genre = filterBundle.getInt("genre");
            rating = filterBundle.getString("rating");
            adult = filterBundle.getBoolean("adult");
        }

        if (genre != 0) {
            urlString += "&with_genres=" + genre;
        }

        if (rating.equals("High")) {
            urlString += "&vote_average.gte=7.0";
        } else if (rating.equals("Low")) {
            urlString += "&vote_average.lte=4.0";
        } else if (rating.equals("Medium")) {
            urlString += "&vote_average.lte=6.9" +
                    ",vote_average.gte=3.9";
        }

        if (adult) {
            urlString += "&include_adult=true";
        }

        request.requestStringFromURL(urlString);
    }

    public void requestYoutubeVideo(String id) {
        String urlString = "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + API_KEY;
        request.requestStringFromURL(urlString);
    }

    public void onStringReturn(String string) {
        Log.d("mdb", string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            if (!rolled) {
                int totalPages = jsonObject.getInt("total_pages");
                int page;

                if (totalPages >= 1000) {
                    page = rand.nextInt(1000);
                } else {
                    page = rand.nextInt(totalPages);
                }

                String url = baseUrl + "&page=" + page;
                request.requestStringFromURL(url);
                rolled = true;

            } else if (movieIsSet) {
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject youtube;
                if (jsonArray.length() > 0) {
                    youtube = jsonArray.getJSONObject(0);
                    String result = youtube.getString("key");
                    System.out.println(result);
                    controller.setTrailer(result);
                }
                movieIsSet = false;

            } else if (rolled && !movieIsSet) {
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject jsonMovie = jsonArray.getJSONObject(rand.nextInt(jsonArray.length()));
                Movie movie = new Movie(jsonMovie.getString("original_title"),
                        jsonMovie.getString("release_date"),
                        jsonMovie.getString("overview"),
                        jsonMovie.getDouble("vote_average") + "/10",
                        jsonMovie.getInt("id") + "",
                        jsonMovie.getString("poster_path"));
                Log.d("mdb", jsonMovie.toString());
                controller.setMovie(movie);
                movieIsSet = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

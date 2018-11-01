package henrik.mau.p3;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MDB_API {
    private static ArrayList<String> posters;
    private static ArrayList<String> overviews;
    private static ArrayList<String> titles;
    private static ArrayList<String> dates;
    private static ArrayList<String> ratings;
    private static ArrayList<String> youtubes;
    private static ArrayList<String> youtubes2;
    private static ArrayList<String> ids;
    private static ArrayList<Boolean> favorited;
    private static ArrayList<ArrayList<String>> comments;

    private static boolean sortByPop;
    private String URL;

    private static String API_KEY = "1382cca4854a01eee1ddc83dc5102dbf";
    private MainActivity activity;
    private Controller controller;
    private ArrayList<Movie> movies;

    public MDB_API(MainActivity activity, Controller controller) {
        this.activity = activity;
        this.controller = controller;

    }

    public void startImageLoadTask() {
        new ImageLoadTask().execute();
    }


    public class ImageLoadTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            while (true) {
                try {
                    posters = new ArrayList(Arrays.asList(getPathsFromAPI(sortByPop)));
                    return posters;
                } catch (Exception e) {
                    continue;
                }
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            System.out.println("HEJ2");
            if (result != null && activity != null) {
                System.out.println("HEJ");
                System.out.println(result.get(0));
                controller.setContent(result);


            }
        }

        public String[] getPathsFromAPI(boolean sortbypop) {
            while (true) {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String JSONResult;

                try {
                    String urlString = null;
                    if (sortbypop) {
                        urlString = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + API_KEY;
                    } else {
                      //  urlString = "https://api.themoviedb.org/3/genre/movie/list?language=en-US&api_key=1382cca4854a01eee1ddc83dc5102dbf";
                     urlString = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&vote_count.gte=500&api_key=" + API_KEY+"&language=en-US&page=2";
                    }
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    //Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }
                    if (buffer.length() == 0) {
                        return null;
                    }
                    JSONResult = buffer.toString();

                    overviews = new ArrayList<String>(Arrays.asList(getStringsFromJSON(JSONResult, "overview")));
                    titles = new ArrayList<String>(Arrays.asList(getStringsFromJSON(JSONResult, "original_title")));
                    ratings = new ArrayList<String>(Arrays.asList(getStringsFromJSON(JSONResult, "vote_average")));
                    dates = new ArrayList<String>(Arrays.asList(getStringsFromJSON(JSONResult, "release_date")));
                    ids = new ArrayList<String>(Arrays.asList(getStringsFromJSON(JSONResult, "id")));


                    while(true)
                    {
                        youtubes = new ArrayList<String>(Arrays.asList(getYoutubesFromIds(ids,0)));
                        youtubes2 = new ArrayList<String>(Arrays.asList(getYoutubesFromIds(ids,1)));
                        int nullCount = 0;
                        for(int i = 0; i<youtubes.size();i++)
                        {
                            if(youtubes.get(i)==null)
                            {
                                nullCount++;
                                youtubes.set(i,"no video found");
                            }
                        }

                        if(nullCount>1)continue;
                        break;
                    }







                    try {
                        return getPathsFromJSON(JSONResult);
                    } catch (JSONException e) {
                        return null;
                    }
                } catch (Exception e) {
                    continue;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                        }
                    }
                }
            }
        }


        public String[] getPathsFromJSON(String JSONStringParam) throws JSONException {

            JSONObject JSONString = new JSONObject(JSONStringParam);

            JSONArray moviesArray = JSONString.getJSONArray("results");
            String[] result = new String[moviesArray.length()];

            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movie = moviesArray.getJSONObject(i);
                String moviePath = movie.getString("poster_path");
                result[i] = moviePath;
            }
            return result;
        }
    }


    //Make the method more complex when working with inputs from spinners
    public void setURLString(String URL){
        this.URL = URL;
    }


    public String[] getStringsFromJSON(String JSONStringParam, String param) throws JSONException {
        JSONObject JSONString = new JSONObject(JSONStringParam);

        JSONArray moviesArray = JSONString.getJSONArray("results");
        String[] result = new String[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movie = moviesArray.getJSONObject(i);
            if (param.equals("vote_average")) {
                Double number = movie.getDouble("vote_average");
                String rating = Double.toString(number) + "/10";
                result[i] = rating;
            } else {
                String data = movie.getString(param);
                result[i] = data;
            }
        }
        return result;
    }
    public String[] getYoutubesFromIds(ArrayList<String> ids, int position) {
        String[] results = new String[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String JSONResult;

            try {
                String urlString = null;
                urlString = "http://api.themoviedb.org/3/movie/" + ids.get(i) + "/videos?api_key=" + API_KEY;


                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                JSONResult = buffer.toString();
                try {
                    results[i] = getYoutubeFromJSON(JSONResult, position);
                } catch (JSONException E) {
                    results[i] = "no video found";
                }
            } catch (Exception e) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }
        }
        return results;
    }

    public String getYoutubeFromJSON(String JSONStringParam, int position) throws JSONException
    {
        JSONObject JSONString = new JSONObject(JSONStringParam);
        JSONArray youtubesArray = JSONString.getJSONArray("results");
        JSONObject youtube;
        String result = "no videos found";
        if(position ==0)
        {
            youtube = youtubesArray.getJSONObject(0);
            result = youtube.getString("key");
        }
        else if(position==1)
        {
            if(youtubesArray.length()>1)
            {
                youtube = youtubesArray.getJSONObject(1);
            }
            else{
                youtube = youtubesArray.getJSONObject(0);
            }
            result = youtube.getString("key");
        }
        return result;
    }

    public Movie getMovie(int position) {
        Movie movie = new Movie();
        movie.setPoster(posters.get(position));
        movie.setTitle(titles.get(position));
        movie.setDate(dates.get(position));
        movie.setOverview(overviews.get(position));
        movie.setRating(ratings.get(position));
        movie.setYoutube(youtubes.get(position));
        System.out.print(movie.getYoutube());
        return movie;
    }

}

package henrik.mau.p3;

import android.os.AsyncTask;

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
            if (result != null && activity != null) {
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
                        urlString = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&vote_count.gte=500&api_key=" + API_KEY;
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

}

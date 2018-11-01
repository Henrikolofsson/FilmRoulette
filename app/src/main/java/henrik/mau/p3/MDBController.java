package henrik.mau.p3;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MDBController {
    private static String API_KEY = "1382cca4854a01eee1ddc83dc5102dbf";

    private Controller controller;
    private MainActivity activity;
    private MDBRequest request;

    private Random rand = new Random();
    private boolean rolled = false;

    private String baseUrl = "http://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY +
            "&vote_average.gte=0";

    public MDBController(MainActivity activity, Controller controller) {
        this.activity = activity;
        this.controller = controller;
        request = new MDBRequest(activity, this);
        request.requestStringFromURL(baseUrl);
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

            } else if (rolled){
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

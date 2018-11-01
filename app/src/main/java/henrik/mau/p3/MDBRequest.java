package henrik.mau.p3;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MDBRequest {
    private MainActivity activity;
    private MDBController controller;

    public MDBRequest(MainActivity activity, MDBController controller) {
        this.activity = activity;
        this.controller = controller;
    }

    public void requestStringFromURL(final String urlString) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = br.readLine()) != null) {
                        buffer.append(line + "\n");
                    }
                    controller.onStringReturn(buffer.toString());

                    urlConnection.disconnect();
                    br.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

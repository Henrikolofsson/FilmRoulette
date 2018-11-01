package henrik.mau.p3.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import henrik.mau.p3.R;

public class YoutubeActivity extends YouTubeBaseActivity {

    private final String API_KEY = "AIzaSyCgVDJtBN1GfOYbARBO00pRXQ2ZepnqBCU";
    private String VIDEO_CODE = "";
    YouTubePlayerView player;
    private long minutes;
    private long seconds;
    private int millis = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        VIDEO_CODE = intent.getStringExtra("video_code");
        System.out.println(VIDEO_CODE);
        setContentView(R.layout.activity_youtube);
        player = findViewById(R.id.player);
        if (savedInstanceState != null) {
            millis = savedInstanceState.getInt("timestamp", 0);
            System.out.println(millis);
        }

        player.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.setFullscreen(true);
                youTubePlayer.loadVideo(VIDEO_CODE);
                youTubePlayer.seekToMillis(millis);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);




            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("timestamp", millis);
    }

    public void onResume() {
        super.onResume();
    }
}

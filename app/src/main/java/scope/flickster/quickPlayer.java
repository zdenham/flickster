package scope.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class quickPlayer extends YouTubeBaseActivity {
    String key;
    Double popularity;
    Double rating;
    String release_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_player);
        key = getIntent().getStringExtra("key");
        popularity = getIntent().getDoubleExtra("popularity", 0.0);
        rating = getIntent().getDoubleExtra("rating", 0.0);
        release_date = getIntent().getStringExtra("release_date");
        TextView tvPopularity = (TextView)findViewById(R.id.tvPopularity);
        TextView tvRating = (TextView)findViewById(R.id.tvRating);
        TextView tvReleaseDate = (TextView)findViewById(R.id.tvreleaseDate);
        Button btnDone = (Button)findViewById(R.id.btnDone);
        int orientation = getResources().getConfiguration().orientation;

        if(orientation == 1){
            tvPopularity.setText("Popularity: " + Double.toString(popularity));
            tvRating.setText("Ratings: " + Double.toString(rating));
            tvReleaseDate.setText("Release date: " + release_date);
            tvPopularity.setVisibility(View.VISIBLE);
            btnDone.setVisibility(View.VISIBLE);
            tvRating.setVisibility(View.VISIBLE);
            tvReleaseDate.setVisibility(View.VISIBLE);
        } else {
            tvPopularity.setVisibility(View.GONE);
            tvRating.setVisibility(View.GONE);
            tvReleaseDate.setVisibility(View.GONE);
            btnDone.setVisibility(View.GONE);
        }


        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize("AIzaSyC0IO098BgWMuNwAqHcKFmoSqfgoO1yiUU",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(key);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    public void doneClicked(View v){
        Intent done = new Intent();
        setResult(RESULT_OK, done);
        this.finish();
    }
}

package scope.flickster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import scope.flickster.adapters.MovieArrayAdapter;
import scope.flickster.models.singleMovie;

public class Movie extends AppCompatActivity {

    ArrayList<singleMovie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;
    int orientation;
    int REQUEST_CODE = 20;
    int inspectingPos;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orientation = getResources().getConfiguration().orientation;
        movies = new ArrayList<>();
        setContentView(R.layout.activity_movie);
        lvItems = (ListView)findViewById(R.id.lvMovies);
        movieAdapter = new MovieArrayAdapter(this, movies);
        movieAdapter.setOrientation(orientation);
        lvItems.setAdapter(movieAdapter);
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(singleMovie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUGER", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
        setUpViewListener();
    }

    private void setUpViewListener() {
        Log.d("listener", "set up event listener");
        lvItems.setOnItemClickListener(
            new AdapterView.OnItemClickListener () {
                @Override
                public void onItemClick(AdapterView<?>  adapter,
                                        View item, int pos, long id ) {
                    launchYoutubeVideo(pos);
                }
            });
    }

    public void launchYoutubeVideo(int pos){
        JSONObject myObject = movies.get(pos).myObject;
        String url = "";
        inspectingPos = pos;

        try {
            String source = "https://api.themoviedb.org/3/movie/";
            int id = myObject.getInt("id");
            String restOfPath = "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
            url = String.format("%s%d%s", source, id, restOfPath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    startViewing(response.getJSONArray("results").getJSONObject(0).getString("key"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void startViewing(String key) {
        JSONObject myObject = movies.get(inspectingPos).myObject;
        Intent i = new Intent(Movie.this, quickPlayer.class);
        try {
            i.putExtra("popularity", myObject.getDouble("popularity"));
            i.putExtra("rating", myObject.getDouble("vote_average"));
            i.putExtra("release_date", myObject.getString("release_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        i.putExtra("key", key);
        startActivityForResult(i, REQUEST_CODE);
    }

}

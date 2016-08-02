package scope.flickster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zdenham on 7/25/16.
 */
public class singleMovie {
    String posterPath;
    String title;
    String overView;
    int orientation;
    String size;
    public JSONObject myObject;

    public singleMovie(JSONObject myObject) throws JSONException {
        this.myObject = myObject;
    }
    public static ArrayList<singleMovie> fromJSONArray(JSONArray array) {
        ArrayList<singleMovie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new singleMovie(array.getJSONObject(x)));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return results;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        Log.e("la", "orientation is getting set in the first place " + Integer.toString(this.orientation));
        setPics();
    }

    public void setPics() {
        try {
            if (orientation == 1) {
                size = "w342/";
                Log.d("1", Integer.toString(this.orientation));
                this.posterPath = myObject.getString("poster_path");
            } else if (orientation == 2) {
                size = "w650/";
                Log.d("2", Integer.toString(this.orientation));
                this.posterPath = myObject.getString("backdrop_path");
            }
            this.title = myObject.getString("original_title");
            this.overView = myObject.getString("overview");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/%s%s",size, posterPath);
    }

    public String getOverView() {
        return overView;
    }
}

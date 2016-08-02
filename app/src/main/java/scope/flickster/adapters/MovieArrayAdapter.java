package scope.flickster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import scope.flickster.R;
import scope.flickster.models.singleMovie;

/**
 * Created by zdenham on 7/25/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<singleMovie> {
    int orientation;
    Context context;

    public MovieArrayAdapter(Context context, List<singleMovie> movies ){
        super(context, android.R.layout.simple_list_item_1, movies);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        singleMovie movie = getItem(position);
        movie.setOrientation(this.orientation);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivMovieImage);
        ivImage.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvMovieTitle);
        TextView tvOverview = (TextView)convertView.findViewById(R.id.tvOverview);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverView());

        Picasso.with(getContext())
                .load(movie.getPosterPath())
                .placeholder(R.mipmap.scope_logo)
                .transform( new RoundedCornersTransformation(10, 10))
                .into(ivImage);

        return convertView;
    }

    public void setOrientation(int orientation) {
        Toast.makeText(context,"the orientation is " + Integer.toString(orientation), Toast.LENGTH_LONG).show();
        this.orientation = orientation;
    }
}

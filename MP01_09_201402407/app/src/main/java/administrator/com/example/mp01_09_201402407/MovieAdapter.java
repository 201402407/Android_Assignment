package administrator.com.example.mp01_09_201402407;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<MovieItem> movieData; // Item 목록 담을 배열
    private int layout;

    public MovieAdapter(Context context, int layout, ArrayList<MovieItem> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.movieData = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return movieData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        MovieItem movieItem = movieData.get(position);
        position += 1;
        TextView movie = (TextView)convertView.findViewById(R.id.textView);
        movie.setText(position + " " + movieItem.getMovieName());

        return convertView;
    }
}

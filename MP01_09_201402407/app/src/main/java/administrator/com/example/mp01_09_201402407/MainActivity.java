package administrator.com.example.mp01_09_201402407;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    ListView listView;
    Button button;

    ArrayList<MovieItem> data = null; // 영화 DB 데이터
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);

        data = new ArrayList<>();

        // DB 셋팅
        dbHelper = new DBHelper(this);
        try {
            db = dbHelper.getWritableDatabase();
        }
        catch (SQLException ex) {
            db = dbHelper.getReadableDatabase();
        }

        data = getDBItem();
        //List<MovieItem> temp = getDBItem();
        /*
        if(!temp.isEmpty()) {
            int i = 0;
            while (i < temp.size()) {

            }
        }
        */
        // ListView 어댑터 연결
        MovieAdapter movieAdapter = new MovieAdapter(this, R.layout.activity_movie_item, data);
        listView.setAdapter(movieAdapter);

        // ListView Item 클릭 이벤트 함수
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                MovieItem temp = data.get(position);
                intent.putExtra("movieName", temp.getMovieName());
                Log.d("TAGyo", temp.getMovieName());
                intent.putExtra("movieYear", temp.getMovieYear());
                Log.d("TAGyo", temp.getMovieYear() + "");
                intent.putExtra("directorName", temp.getDirectorName());
                Log.d("TAGyo", temp.getDirectorName());
                intent.putExtra("movieScore", temp.getMovieScore());
                Log.d("TAGyo", temp.getMovieScore() + "");
                intent.putExtra("movieCountry", temp.getMovieCountry());
                Log.d("TAGyo", temp.getMovieCountry());
                intent.putExtra("code", "1"); // 존재하는 것 클릭하면 1
                startActivity(intent);
                finish();
            }
        });
        button = (Button) findViewById(R.id.addbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("code", "0"); // 존재하지 않은 것 클릭하면 0
                startActivity(intent);
                finish();
            }
        });
    }

    public ArrayList<MovieItem> getDBItem() {
        ArrayList<MovieItem> movieItemList = new ArrayList<>();
        Cursor cursor;

        cursor = db.rawQuery("SELECT movieName, movieYear, directorName, movieScore, movieCountry FROM myMovie;", null);

        while (cursor.moveToNext()) {
            String movieName = cursor.getString(0);
            Log.d("TAGyo", movieName);
            int movieYear = cursor.getInt(1);
            Log.d("TAGyo", movieYear + "");
            String directorName = cursor.getString(2);
            Log.d("TAGyo", directorName);
            double movieScore = cursor.getDouble(3);
            Log.d("TAGyo", movieScore + "");
            String movieCountry = cursor.getString(4);
            Log.d("TAGyo", movieCountry);

            MovieItem movieItem = new MovieItem(movieName, movieYear, directorName, movieScore, movieCountry);
            movieItemList.add(movieItem);
        }
        return movieItemList;
    }
}

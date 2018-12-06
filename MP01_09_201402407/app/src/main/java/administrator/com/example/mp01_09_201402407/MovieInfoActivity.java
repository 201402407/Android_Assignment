package administrator.com.example.mp01_09_201402407;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MovieInfoActivity extends AppCompatActivity {
    DBHelper dbHelper;
    SQLiteDatabase db;
    EditText mName, mYear, dName, mScore, mCountry;
    Button updatebtn, deletebtn, savebtn;
    boolean isIntent = false;

    String movieName, directorName, movieCountry;
    int movieYear;
    double movieScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        // DB 셋팅
        dbHelper = new DBHelper(this);
        try {
            db = dbHelper.getWritableDatabase();
        }
        catch (SQLException ex) {
            db = dbHelper.getReadableDatabase();
        }

        // EditText 설정
        mName = (EditText) findViewById(R.id.movieName);
        mYear = (EditText) findViewById(R.id.movieYear);
        dName = (EditText) findViewById(R.id.directorName);
        mScore = (EditText) findViewById(R.id.movieScore);
        mCountry = (EditText) findViewById(R.id.movieCountry);

        // Button 설정
        updatebtn = (Button) findViewById(R.id.updatebtn);
        deletebtn = (Button) findViewById(R.id.deletebtn);
        savebtn = (Button) findViewById(R.id.savebtn);

        // 만약 리스트를 클릭해서 이 액티비티에 들어온 것이라면
        Intent intent = getIntent();
        if(intent.getStringExtra("code").equals("1")) {
            isIntent = true;
            movieName = intent.getStringExtra("movieName");
            movieYear = intent.getIntExtra("movieYear", -1);
            directorName = intent.getStringExtra("directorName");
            movieScore = intent.getDoubleExtra("movieScore", -1);
            movieCountry = intent.getStringExtra("movieCountry");
            Log.d("TAGyo", movieName + "");
            Log.d("TAGyo", movieScore + "");
            Log.d("TAGyo", movieYear + "");
            mName.setText(movieName);
            mYear.setText(movieYear + "");
            dName.setText(directorName);
            mScore.setText(movieScore + "");
            mCountry.setText(movieCountry);

            savebtn.setVisibility(View.INVISIBLE);
        }

        // 버튼 클릭할 시 메인 화면으로 되돌아 가기 위한 인텐트 생성
        final Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DB에 존재하지 않던 것을 수정할 때
                if(!isIntent) {
                    Toast.makeText(getApplicationContext(), "수정하려는 값이 존재하지 않았습니다. SAVE를 클릭하세요.",
                            Toast.LENGTH_LONG).show();
                }
                // DB에 존재하는 것을 수정할 때
                else {
                    String mNameResult = mName.getText().toString();
                    int mYearResult = Integer.valueOf(mYear.getText().toString());
                    String dNameResult = dName.getText().toString();
                    double mScoreResult = Double.parseDouble(mScore.getText().toString());
                    String mCountryResult = mCountry.getText().toString();

                    db.execSQL("UPDATE myMovie SET movieName = '" + mNameResult + "', " +
                            "movieYear = '" + mYearResult + "', " +
                            "directorName = '" + dNameResult + "', " +
                            "movieScore = '" + mScoreResult + "', " +
                            "movieCountry = '" + mCountryResult + "'" +
                            " WHERE movieName = '" + movieName + "' AND movieYear = '" + movieYear + "' " +
                            "AND directorName = '" + directorName + "' AND movieScore = '" + movieScore + "' " +
                            "AND movieCountry = '" + movieCountry + "';");
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_LONG).show();
                    startActivity(intent2);
                    finish();
                }
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isIntent) {
                    Toast.makeText(getApplicationContext(), "삭제하려는 값이 존재하지 않았습니다. ",
                            Toast.LENGTH_LONG).show();
                }

                else {
                    db.execSQL("DELETE FROM myMovie WHERE movieName = '" + movieName + "' AND movieYear = '" + movieYear + "' " +
                            "AND directorName = '" + directorName + "' AND movieScore = '" + movieScore + "' " +
                            "AND movieCountry = '" + movieCountry + "';");
                    Toast.makeText(getApplicationContext(), "삭제되었음", Toast.LENGTH_LONG).show();
                    startActivity(intent2);
                    finish();
                }
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mNameResult = mName.getText().toString();
                int mYearResult = Integer.valueOf(mYear.getText().toString());
                String dNameResult = dName.getText().toString();
                double mScoreResult = Double.parseDouble(mScore.getText().toString());
                String mCountryResult = mCountry.getText().toString();
                db.execSQL("INSERT INTO myMovie (movieName, movieYear, directorName, movieScore, movieCountry) " +
                        "VALUES ('" + mNameResult + "', '" + mYearResult + "', '"
                        + dNameResult + "', '" + mScoreResult + "', '" + mCountryResult + "');");
                Toast.makeText(getApplicationContext(), "추가되었음", Toast.LENGTH_LONG).show();
                startActivity(intent2);
                finish();
            }
        });
    }
}

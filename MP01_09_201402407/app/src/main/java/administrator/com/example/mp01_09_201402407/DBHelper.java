package administrator.com.example.mp01_09_201402407;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myMovie.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       // db.execSQL("DROP TABLE myMovie");
        db.execSQL("CREATE TABLE IF NOT EXISTS myMovie ('_id' INTEGER PRIMARY KEY AUTOINCREMENT, movieName TEXT, movieYear INTEGER" +
                ", directorName TEXT, movieScore TEXT, movieCountry TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS myMovie");
        onCreate(db);
    }
}

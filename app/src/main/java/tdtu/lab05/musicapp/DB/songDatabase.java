package tdtu.lab05.musicapp.DB;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class songDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "songs.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_SONGS           = "ListSongs";
    public static final String COLUMN_ID           = "songID";
    public static final String COLUMN_NAME           = "songName";
    public static final String COLUMN_SIZE       = "Size";
    public static final String COLUMN_PATH           = "songpath";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_SONGS + " (" + COLUMN_ID + " INTEGER, " + COLUMN_NAME
            + " TEXT PRIMARY KEY, " + COLUMN_SIZE + " TEXT, " + COLUMN_PATH + " TEXT " + ")";

    public songDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        db.execSQL(TABLE_CREATE);
    }

}


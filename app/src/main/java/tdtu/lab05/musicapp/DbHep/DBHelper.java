package tdtu.lab05.musicapp.DbHep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DataBaseName = "playlist.db";
    public Context context;
    private static final int version = 1;


    public DBHelper(Context context) {
        super(context, DataBaseName, null, version);
    }
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DataBaseName, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableDanhSach = "create table DanhSachPhat("+
                "iddanhsachPhat INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tendanhsachPhat TEXT NOT NULL)";
        db.execSQL(createTableDanhSach);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + "DanhSachPhat" );

        onCreate(db);
    }
}

package tdtu.lab05.musicapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import tdtu.lab05.musicapp.DbHep.DBHelper;
import tdtu.lab05.musicapp.Models.DanhSachPhat;

import java.util.ArrayList;
import java.util.List;

public class DBDanhSachPhat{
    private SQLiteDatabase db;

    public DBDanhSachPhat(Context context) {
        DBHelper dbHelper =new DBHelper(context);
        this.db =dbHelper.getWritableDatabase();
    }
    public long insert(DanhSachPhat obj){
        ContentValues values =new ContentValues();
        values.put(Name.tendanhsachPhat, obj.getTendanhsachPhat());
        return db.insert("DanhSachPhat", null, values);
    }

    public int delete(String id){
        return db.delete("DanhSachPhat","iddanhsachPhat=?", new String[]{id});
    }

    public List<DanhSachPhat> getAll() {
        String sql = "SELECT * FROM DanhSachPhat";
        return getData(sql);
    }

    private List<DanhSachPhat> getData(String sql, String... selectionArgs) {
        List<DanhSachPhat> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);

        while (c.moveToNext()) {
            DanhSachPhat obj = new DanhSachPhat();
            obj.setIddanhsachPhat(c.getInt(c.getColumnIndexOrThrow(Name.iddanhsachPhat)));
            obj.setTendanhsachPhat(c.getString(c.getColumnIndexOrThrow(Name.tendanhsachPhat)));
            list.add(obj);
        }
        return list;
    }

    public static class Name {
        public static String iddanhsachPhat = "iddanhsachPhat";
        public static String tendanhsachPhat = "tendanhsachPhat";

    }


}

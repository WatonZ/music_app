package tdtu.lab05.musicapp.Fragment;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

import tdtu.lab05.musicapp.DB.songDatabase;
import tdtu.lab05.musicapp.Music_player_activity;
import tdtu.lab05.musicapp.R;
import static tdtu.lab05.musicapp.DB.songDatabase.TABLE_SONGS;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    ListView listView;
    String[] songs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.songList);
        runtimePer();
        return view;
    }
    public void runtimePer(){
        Dexter.withContext(getContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displaySong();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    //Tìm bài hát
    public ArrayList<File> findSong(File file) {

        ArrayList arrayList = new ArrayList();
        File [] files = file.listFiles();
        if(files != null) {
            for (File singlefile : files) {
                if (singlefile.isDirectory() && !singlefile.isHidden()) {
                    arrayList.addAll(findSong(singlefile));
                } else {
                    if (singlefile.getName().endsWith(".wav")) {
                        arrayList.add(singlefile);
                    } else if (singlefile.getName().endsWith(".mp3")) {
                        arrayList.add(singlefile);
                    }
                }

            }
        }
        return arrayList;
    }
    //Hiển thị danh sách bài hát có chứa trong bộ nhớ
    void displaySong(){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = new songDatabase(getContext(), null, null, 1).getWritableDatabase();
        final ArrayList<File> mySong = findSong(Environment.getExternalStorageDirectory());
        songs = new String[mySong.size()];
        for (int i = 0; i< mySong.size(); i++){
            //Xóa hiển thị đuôi .mp3 và .wav khi hiển thị tên bài hát
            songs[i] = mySong.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");
            values.put(songDatabase.COLUMN_ID, i+1);
            values.put(songDatabase.COLUMN_NAME, songs[i]);
            values.put(songDatabase.COLUMN_SIZE, mySong.get(i).length()/1024/1000.0 + "MB");
            values.put(songDatabase.COLUMN_PATH, mySong.get(i).getAbsolutePath());
            db.insert(TABLE_SONGS, null, values);
        }

        customAdapter customAdapter = new customAdapter();
        listView.setAdapter(customAdapter);
        //Khi bấm vào bài hát thì sẽ chuyển sang giao diện phát nhạc
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String song_name = (String) listView.getItemAtPosition(i);
                startActivity(new Intent(getContext(), Music_player_activity.class)
                        .putExtra("songs", mySong)
                        .putExtra("song_name", song_name)
                        .putExtra("position", i));
            }
        });
    }

    class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return songs.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = getLayoutInflater().inflate(R.layout.list_song, null);
            TextView name = v.findViewById(R.id.songname);
            name.setSelected(true);
            name.setText(songs[i]);

            return v;
        }
    }
}
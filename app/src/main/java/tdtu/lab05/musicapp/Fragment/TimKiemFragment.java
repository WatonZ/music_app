package tdtu.lab05.musicapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tdtu.lab05.musicapp.Music_player_activity;
import tdtu.lab05.musicapp.R;

public class TimKiemFragment extends Fragment {
    private static final String TABLE_SONGS = "" ;
    SearchView searchView;
    String[] songs;
    ListView listView;
    List<String> items;
    ArrayList<String> search_list;
    CustomAdapter adapter;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        listView = view.findViewById(R.id.listViewSong);
        searchView = view.findViewById(R.id.search_bar);
        search_list = new ArrayList<>();

        displaySong();
        return view;
    }

    public ArrayList<File> findSong(File file) {

        ArrayList arrayList = new ArrayList();
        File[] files = file.listFiles();
        if (files != null) {
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
    void displaySong() {
        final ArrayList<File> mySongs = (ArrayList<File>) findSong(Environment.getExternalStorageDirectory());

        items = new ArrayList<>();
        ArrayList<String> ten_bai_hat = new ArrayList<>();

        for (int i = 0; i < mySongs.size(); i++) {
            items.add(mySongs.get(i).getName().toString().replace(".mp3", "") );
            ten_bai_hat.add( mySongs.get(i).getName().toString().replace(".mp3", "") );
        }
        search_list = ten_bai_hat;

        TimKiemFragment.CustomAdapter customAdapter = new TimKiemFragment.CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String songName = items.get(i);

                startActivity(new Intent(getContext(), Music_player_activity.class)
                        .putExtra("songs", mySongs )
                        .putExtra("song_name" , songName )
                        .putExtra("position" , i) );
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if ( valiDate(query) ){
                    items.clear();
                    items.addAll(search_name(query , search_list) );
                    search_result();
                }else {
                    items.clear();
                    items.addAll(search_list);
                    search_result();
                    Toast.makeText(getContext(), "Please enter song name", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if ( valiDate(newText) ){
                    items.clear();
                    items.addAll(search_name(newText , search_list) );
                    search_result();
                }else {
                    items.clear();
                    items.addAll(search_list);
                    search_result();
                    Toast.makeText(getContext(), "Please enter song name", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void search_result() {
        adapter = new CustomAdapter();
        listView.setAdapter(adapter);
    }

    private List<String> search_name(String bh, List<String> list) {
        List<String> s_list = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if ( list.get(i).toString().toLowerCase().contains( bh.toLowerCase().toString() ) ){
                s_list.add( list.get(i).toString() );
            }
        }

        return s_list;
    }

    private boolean valiDate(String...editTexts) {

        for (int i = 0; i < editTexts.length; i++) {
            if (editTexts[i].toString().isEmpty()) {
                return false;
            }
            ;
        }
        return true;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
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
        public View getView(int i, View convertView, ViewGroup parent) {
            View v = getLayoutInflater().inflate(R.layout.list_song, null);
            TextView name = v.findViewById(R.id.songname);
            name.setSelected(true);
            name.setText(items.get(i));
            return v;
        }
    }
}



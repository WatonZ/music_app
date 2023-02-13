package tdtu.lab05.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import tdtu.lab05.musicapp.Fragment.HomeFragment;
import tdtu.lab05.musicapp.Fragment.PlayListFragment;
import tdtu.lab05.musicapp.Fragment.TimKiemFragment;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView menu_app;
    TimKiemFragment timKiemFragment;
    PlayListFragment playlistFragment;
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        menu_app = findViewById((R.id.menu_of_app));
        timKiemFragment = new TimKiemFragment();
        playlistFragment =new PlayListFragment();
        homeFragment= new HomeFragment();
        tai_fragment(homeFragment);
        setTitle("Home");

        menu_app.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.menu_tim_kiem) {
                    tai_fragment(timKiemFragment);
                    setTitle("Search");
                }else if(id == R.id.menu_danh_sach_phat){
                    tai_fragment(playlistFragment);
                    setTitle("Playlist");
                }else if(id == R.id.menu_home){
                    tai_fragment(homeFragment);
                    setTitle("Home");
                }
                return false;
            }
        });

    }


    public void tai_fragment(Fragment fragment) {
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.HomeLayout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
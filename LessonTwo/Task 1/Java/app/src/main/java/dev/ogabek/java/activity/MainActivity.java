package dev.ogabek.java.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.ogabek.java.R;
import dev.ogabek.java.adapter.ViewPagerAdapter;
import dev.ogabek.java.fragment.FavouriteFragment;
import dev.ogabek.java.fragment.HomeFragment;
import dev.ogabek.java.fragment.ProfileFragment;
import dev.ogabek.java.fragment.SearchFragment;
import dev.ogabek.java.fragment.UploadFragment;

/**
 * It contains viewPager with 5 fragment in MainActivity,
 * and pages can be controlled by BottomNavigationView
 */

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.toString();
    int index = 0;
    HomeFragment homeFragment;
    UploadFragment uploadFragment;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    @SuppressLint("NonConstantResourceId")
    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    viewPager.setCurrentItem(0);
                    break;
                }
                case R.id.navigation_search: {
                    viewPager.setCurrentItem(1);
                    break;
                }
                case R.id.navigation_upload: {
                    viewPager.setCurrentItem(2);
                    break;
                }
                case R.id.navigation_favourite: {
                    viewPager.setCurrentItem(3);
                    break;
                }
                case R.id.navigation_profile: {
                    viewPager.setCurrentItem(4);
                    break;
                }
            }
            return true;
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                bottomNavigationView.getMenu().getItem(index).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Home and Upload Fragments are global for communication purpose
        homeFragment = new HomeFragment();
        uploadFragment = new UploadFragment();
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(homeFragment);
        adapter.addFragment(new SearchFragment());
        adapter.addFragment(uploadFragment);
        adapter.addFragment(new FavouriteFragment());
        adapter.addFragment(new ProfileFragment());
        viewPager.setAdapter(adapter);
    }

}
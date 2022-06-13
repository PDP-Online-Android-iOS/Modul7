package dev.ogabek.java.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dev.ogabek.java.R;

/**
 * It contains viewPager with 5 fragment in MainActivity,
 * and pages can be controlled by BottomNavigationView
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
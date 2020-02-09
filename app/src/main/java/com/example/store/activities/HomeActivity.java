package com.example.store.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.store.R;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

// https://code.tutsplus.com/tutorials/how-to-code-a-navigation-drawer-in-an-android-app--cms-30263

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mDrawer;
    ActionBarDrawerToggle toggle;
    TextView tv_navBarName;
    ImageView iv_navBarImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        mDrawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, mDrawer, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setNavigationHeaderInfo();

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_item_products) {
            startActivity(new Intent(this, ProductsActivity.class));
        } else if (item.getItemId() == R.id.nav_item_orders) {
            startActivity(new Intent(this, null));
        } else if (item.getItemId() == R.id.nav_item_cart) {
            startActivity(new Intent(this, null));
        } else if (item.getItemId() == R.id.nav_item_logout) {
            LoginManager.getInstance().logOut();
            startActivity(new Intent(this, MainActivity.class));
        }

        return true;
    }

    private void setNavigationHeaderInfo() {
        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        tv_navBarName = header.findViewById(R.id.nav_header_textView);
        iv_navBarImg = header.findViewById(R.id.nav_header_imageView);

        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            tv_navBarName.setText(profile.getName());
            Picasso.get().load(profile.getProfilePictureUri(128, 128)).into(iv_navBarImg);
        }
    }
}
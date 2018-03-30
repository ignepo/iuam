package ch.qoa.an.perfapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.design.widget.NavigationView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RecapFragment.OnFragmentInteractionListener,
        AbdoTimeFragment.OnFragmentInteractionListener,
        GlobalFragment.OnFragmentInteractionListener{

    RecapFragment recapFragment;
    AbdoTimeFragment abdotimeFragment;
    GlobalFragment globalFragment;
    public static boolean logged = false;
    String TAG = "Test";
   private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recapFragment = new RecapFragment();
        abdotimeFragment = new AbdoTimeFragment();
        globalFragment = new GlobalFragment();

        /*fm = getSupportFragmentManager(); // or 'getSupportFragmentManager();'
        int count = fm.getBackStackEntryCount();
        for(int i = 0; i < count; ++i) {
            fm.popBackStack();
        }*/

        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, globalFragment);
        fragmentTransaction.commit();

        //-----------------------------------------------------------------------------------
        // Toolbar / drawer
        //-----------------------------------------------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void callFragment(Fragment fragmentToCall, String title) {
        //if(logged) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            setTitle(title);
            transaction.replace(R.id.fragment_container, fragmentToCall);
            transaction.addToBackStack(null);
            transaction.commit();
        /*} else {

            this.finish(); //ESSAI
            startActivity(EmailPasswordActivity.intentLogin);

        }*/
    }

    @Override
    public void onRecapFragmentInteraction(Integer uri) {
        callFragment(globalFragment, "Global");
        //detailFragment.updateElement(itemAtPosition);
        //callFragment(detailFragment, getString(R.string.toolbarTitleDetail));
    }

    @Override
    public void onAbdoTimeFragmentInteraction(Integer uri) {
        //Si on a pressé logout
        if(uri == 999)
        {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            this.finish();
            startActivity(EmailPasswordActivity.intentLogin);
        }
        else {
            callFragment(recapFragment, "Recap");
        }
        //detailFragment.updateElement(itemAtPosition);
        //callFragment(detailFragment, getString(R.string.toolbarTitleDetail));
    }

    @Override
    public void onGlobalFragmentInteraction(Integer uri) {
        abdotimeFragment.setSport(uri);
        callFragment(abdotimeFragment, "AbdoTime");

        /*switch(uri) {
            case 0:
                Log.i(TAG, "onValueSelected : Abdos");
                callFragment(abdotimeFragment, "AbdoTime");
                abdotimeFragment.setSport(uri);
                break;
            case 1:
                Log.i(TAG, "onValueSelected : Dorseaux");
                callFragment(abdotimeFragment, "AbdoTime");
                break;
            case 2:
                Log.i(TAG, "onValueSelected : Corde");
                callFragment(abdotimeFragment, "AbdoTime");
                break;
            case 3:
                Log.i(TAG, "onValueSelected : Squats");
                callFragment(abdotimeFragment, "AbdoTime");
                break;
            default:
                Log.i(TAG, "onValueSelected : Other");
        }*/
        //detailFragment.updateElement(itemAtPosition);
        //callFragment(detailFragment, getString(R.string.toolbarTitleDetail));
        // Test commit
    }

    //----------------------------------------------------------------------
    // Drawer methods
    //----------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //onDrawerFragmentInteraction(mapFragment, getString(R.string.toolbarTitleMap));
        } else if (id == R.id.nav_profile) {
            //onDrawerFragmentInteraction(listFragment, getString(R.string.toolbarTitleList));
        }else if (id == R.id.nav_credit)
        {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}

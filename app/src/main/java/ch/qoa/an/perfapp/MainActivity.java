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

import com.android.volley.Request;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RecapFragment.OnFragmentInteractionListener,
        AbdoTimeFragment.OnFragmentInteractionListener,
        GlobalFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener{

    RecapFragment recapFragment;
    AbdoTimeFragment abdotimeFragment;
    GlobalFragment globalFragment;
    ProfileFragment profileFragment;
    public static boolean logged = false;
    String TAG = "Test";

    public static String AbdosNum;
    public static String DorseauxNum;
    public static String SquatsNum;
    public static String CordesNum;


    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recapFragment = new RecapFragment();
        abdotimeFragment = new AbdoTimeFragment();
        globalFragment = new GlobalFragment();
        profileFragment = new ProfileFragment();

        processGETRequest("/sports/1");

        /*FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, globalFragment);
        fragmentTransaction.commit();*/

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
    public void onProfileFragmentInteraction(Integer uri) {
        callFragment(profileFragment, "Profile");
        //detailFragment.updateElement(itemAtPosition);
        //callFragment(detailFragment, getString(R.string.toolbarTitleDetail));
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
        if(uri == 800)
        {
            callFragment(recapFragment, "Recap");
        }
        else
        {
            abdotimeFragment.setSport(uri);
            callFragment(abdotimeFragment, "AbdoTime");
        }


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
            callFragment(globalFragment, "Global");
            //onDrawerFragmentInteraction(mapFragment, getString(R.string.toolbarTitleMap));
        } else if (id == R.id.nav_profile) {
            onProfileFragmentInteraction(0);
            //onDrawerFragmentInteraction(listFragment, getString(R.string.toolbarTitleList));
        }else if (id == R.id.nav_credit)
        {

        }
        else if (id == R.id.nav_loout)
        {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            this.finish();
            startActivity(EmailPasswordActivity.intentLogin);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    //---------------------------------------------------------------------------------
    // Récupération et parsing du JSON des station preovenant du serveur
    //---------------------------------------------------------------------------------
    private void processGETRequest(String url) {
        final String url_set = url;

        Utils.processRequest(this, Request.Method.GET, url, null,
                new Utils.VolleyCallback() {

                    @Override
                    public void onSuccessResponse(JSONObject result) {
                        try {
                            JSONArray sessions = result.getJSONArray("sessions");
                            for(int k=0; k<sessions.length(); k++)
                            {
                                if(url_set == "/sports/1")
                                {
                                    JSONObject StationK = sessions.getJSONObject(k);

                                    AbdosNum=StationK.getString("Abdos");
                                    DorseauxNum=StationK.getString("Dorsaux");
                                    SquatsNum=StationK.getString("Squats");
                                    CordesNum=StationK.getString("Corde");

                                    Log.i(TAG, "onSuccessResponse:********* Abdos : " + AbdosNum);
                                    Log.i(TAG, "onSuccessResponse:********* Dorseaux : " + DorseauxNum);
                                    Log.i(TAG, "onSuccessResponse:********* Squats : " + SquatsNum);
                                    Log.i(TAG, "onSuccessResponse:********* Cordes : " + CordesNum);

                                    //initList(StationK,k);
                                    FragmentTransaction fragmentTransaction =
                                            getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.fragment_container, globalFragment);
                                    fragmentTransaction.commit();
                                }
                                else
                                {
                                    Log.i(TAG, "onSuccessResponse: GROSSE PROBLEM: ");
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


}

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

import java.util.ArrayList;

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
    String TAG = "TestApp";

    public static ArrayList<SportItem> AbdoSessionList;
    public static ArrayList<SportItem> DorsauxSessionList;
    public static ArrayList<SportItem> CordeSessionList;
    public static ArrayList<SportItem> SquatsSessionList;
    public static ArrayList<SessionItem> AllSessionList;

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

        processGETRequest_TimeHist();

        AbdoSessionList = new ArrayList<>();
        DorsauxSessionList = new ArrayList<>();
        CordeSessionList = new ArrayList<>();
        SquatsSessionList = new ArrayList<>();
        AllSessionList = new ArrayList<>();

                FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, globalFragment);
        /*fragmentTransaction.commit();*/

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
            //setTitle(title);
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
        callFragment(globalFragment, "PerfApp");
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
            //processGETRequest_Hist("/days/1/");
            callFragment(recapFragment, "Recap");
        }
        else if (uri == 100)
        {
            processGETRequest_TimeHist();
        }
        else if(uri == 101)
        {
            processGETRequest_RepHist();
        }
        else
        {
            abdotimeFragment.setSport(uri);

            switch(uri) {
                case 0:
                    abdotimeFragment.setStationList(AbdoSessionList);

                    processGETRequest_SportHist("/sports/1/abdos");
                    break;
                case 1:
                    abdotimeFragment.setStationList(DorsauxSessionList);
                    processGETRequest_SportHist("/sports/1/dorsaux");
                    break;
                case 2:
                    abdotimeFragment.setStationList(CordeSessionList);
                    processGETRequest_SportHist("/sports/1/corde");
                    break;
                case 3:
                    abdotimeFragment.setStationList(SquatsSessionList);
                    processGETRequest_SportHist("/sports/1/squats");
                    break;
                default:
                    //Do Something
            }
        }
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
            callFragment(globalFragment, "PerfApp");
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
    private void processGETRequest_TimeHist() {

        Utils.processRequest(this, Request.Method.GET, "/sports/2", null,
                new Utils.VolleyCallback() {

                    @Override
                    public void onSuccessResponse(JSONObject result) {
                        try {

                            JSONArray sessions = result.getJSONArray("sessions");
                            for(int k=0; k<sessions.length(); k++)
                            {
                                JSONObject StationK = sessions.getJSONObject(k);

                                AbdosNum=StationK.getString("Abdos_time");
                                DorseauxNum=StationK.getString("Dorsaux_time");
                                SquatsNum=StationK.getString("Squats_time");
                                CordesNum=StationK.getString("Corde_time");

                                Log.i(TAG, "onSuccessResponse: Abdos : " + AbdosNum);
                                Log.i(TAG, "onSuccessResponse: Dorseaux : " + DorseauxNum);
                                Log.i(TAG, "onSuccessResponse: Squats : " + SquatsNum);
                                Log.i(TAG, "onSuccessResponse: Cordes : " + CordesNum);

                                FragmentTransaction fragTransaction =  getSupportFragmentManager().beginTransaction();
                                //fragTransaction.add(R.id.fragment_container, globalFragment);
                                fragTransaction.detach(globalFragment);
                                fragTransaction.attach(globalFragment);

                                fragTransaction.commit();
                            }

                    } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //---------------------------------------------------------------------------------
    // Récupération et parsing du JSON des station preovenant du serveur
    //---------------------------------------------------------------------------------
    private void processGETRequest_RepHist() {

        Utils.processRequest(this, Request.Method.GET, "/sports/1", null,
                new Utils.VolleyCallback() {

                    @Override
                    public void onSuccessResponse(JSONObject result) {
                        try {
                            JSONArray sessions = result.getJSONArray("sessions");
                            for(int k=0; k<sessions.length(); k++)
                            {
                                    //Log.i(TAG, "onSuccessResponse: GROSSE PROBLEM: ");

                                    JSONObject StationK = sessions.getJSONObject(k);

                                    AbdosNum=StationK.getString("Abdos");
                                    DorseauxNum=StationK.getString("Dorsaux");
                                    SquatsNum=StationK.getString("Squats");
                                    CordesNum=StationK.getString("Corde");

                                    Log.i(TAG, "onSuccessResponse: Abdos : " + AbdosNum);
                                    Log.i(TAG, "onSuccessResponse: Dorseaux : " + DorseauxNum);
                                    Log.i(TAG, "onSuccessResponse: Squats : " + SquatsNum);
                                    Log.i(TAG, "onSuccessResponse: Cordes : " + CordesNum);

                                    FragmentTransaction fragTransaction =  getSupportFragmentManager().beginTransaction();
                                    fragTransaction.detach(globalFragment);
                                    fragTransaction.attach(globalFragment);
                                    fragTransaction.commit();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //---------------------------------------------------------------------------------
    // Récupération et parsing du JSON des station preovenant du serveur
    //---------------------------------------------------------------------------------
    private void processGETRequest_SportHist(String url) {

        final String Url = url;
        Utils.processRequest(this, Request.Method.GET, Url, null,
                new Utils.VolleyCallback() {

                    @Override
                    public void onSuccessResponse(JSONObject result) {
                        try {
                            if(Url == "/sports/1/abdos")
                            {
                                AbdoSessionList.clear();
                                JSONArray sessions = result.getJSONArray("sessions");

                                for(int k=0; k<sessions.length(); k++)
                                {
                                    JSONObject SessionK = sessions.getJSONObject(k);

                                    initListAbdo(SessionK);
                                }
                            }
                            else if (Url == "/sports/1/dorsaux")
                            {
                                DorsauxSessionList.clear();
                                JSONArray sessions = result.getJSONArray("sessions");

                                for(int k=0; k<sessions.length(); k++)
                                {
                                    JSONObject SessionK = sessions.getJSONObject(k);

                                    initListDorsaux(SessionK);
                                }
                            }
                            else if (Url == "/sports/1/corde")
                            {
                                CordeSessionList.clear();
                                JSONArray sessions = result.getJSONArray("sessions");

                                for(int k=0; k<sessions.length(); k++)
                                {
                                    JSONObject SessionK = sessions.getJSONObject(k);

                                    initListCorde(SessionK);
                                }
                            }
                            else
                            {
                                SquatsSessionList.clear();
                                JSONArray sessions = result.getJSONArray("sessions");

                                for(int k=0; k<sessions.length(); k++)
                                {
                                    JSONObject SessionK = sessions.getJSONObject(k);

                                    initListSquat(SessionK);
                                }
                            }

                            FragmentTransaction fragTransaction =  getSupportFragmentManager().beginTransaction();
                            fragTransaction.detach(abdotimeFragment);
                            fragTransaction.attach(abdotimeFragment);
                            callFragment(abdotimeFragment, "AbdoTime");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //---------------------------------------------------------------------------------
    // Récupération et parsing du JSON des station preovenant du serveur
    //---------------------------------------------------------------------------------
    private void processGETRequest_Hist(String url) {

        final String Url = url;

        Utils.processRequest(this, Request.Method.GET, Url, null,
                new Utils.VolleyCallback() {

                    @Override
                    public void onSuccessResponse(JSONObject result) {
                        try {
                            AllSessionList.clear();
                            JSONArray sessions = result.getJSONArray("sessions");

                            for(int k=0; k<sessions.length(); k++)
                            {
                                JSONObject SessionK = sessions.getJSONObject(k);

                                initListAll(SessionK);
                            }

                            callFragment(recapFragment, "Recap");
                            /*FragmentTransaction fragTransaction =  getSupportFragmentManager().beginTransaction();
                            fragTransaction.detach(abdotimeFragment);
                            fragTransaction.attach(abdotimeFragment);
                            callFragment(abdotimeFragment, "AbdoTime");*/

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //---------------------------------------------------------------------------------
    // Initialisation de la liste
    //---------------------------------------------------------------------------------
    private void initListAbdo(JSONObject sessionK) {

        try {

            Integer  Value = Integer.parseInt(sessionK.getString("value"));
            String  Date = sessionK.getString("date");
            String[] DateS = Date.split("-");
            Integer Year = Integer.parseInt(DateS[0]);
            Integer Month = Integer.parseInt(DateS[1]);
            Integer Day = Integer.parseInt(DateS[2]);

            SportItem abdoSession = new SportItem();

            abdoSession.setRep(Value);
            abdoSession.setYear(Year);
            abdoSession.setMonth(Month);
            abdoSession.setDay(Day);
            AbdoSessionList.add(abdoSession);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //---------------------------------------------------------------------------------
    // Initialisation de la liste
    //---------------------------------------------------------------------------------
    private void initListCorde(JSONObject sessionK) {

        try {

            Integer  Value = Integer.parseInt(sessionK.getString("value"));
            String  Date = sessionK.getString("date");
            String[] DateS = Date.split("-");
            Integer Year = Integer.parseInt(DateS[0]);
            Integer Month = Integer.parseInt(DateS[1]);
            Integer Day = Integer.parseInt(DateS[2]);

            SportItem CordeSession = new SportItem();

            CordeSession.setRep(Value);
            CordeSession.setYear(Year);
            CordeSession.setMonth(Month);
            CordeSession.setDay(Day);
            CordeSessionList.add(CordeSession);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //---------------------------------------------------------------------------------
    // Initialisation de la liste
    //---------------------------------------------------------------------------------
    private void initListSquat(JSONObject sessionK) {

        try {

            Integer  Value = Integer.parseInt(sessionK.getString("value"));
            String  Date = sessionK.getString("date");
            String[] DateS = Date.split("-");
            Integer Year = Integer.parseInt(DateS[0]);
            Integer Month = Integer.parseInt(DateS[1]);
            Integer Day = Integer.parseInt(DateS[2]);

            SportItem SquatSession = new SportItem();

            SquatSession.setRep(Value);
            SquatSession.setYear(Year);
            SquatSession.setMonth(Month);
            SquatSession.setDay(Day);
            SquatsSessionList.add(SquatSession);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //---------------------------------------------------------------------------------
    // Initialisation de la liste
    //---------------------------------------------------------------------------------
    private void initListDorsaux(JSONObject sessionK) {

        try {

            Integer  Value = Integer.parseInt(sessionK.getString("value"));
            String  Date = sessionK.getString("date");
            String[] DateS = Date.split("-");
            Integer Year = Integer.parseInt(DateS[0]);
            Integer Month = Integer.parseInt(DateS[1]);
            Integer Day = Integer.parseInt(DateS[2]);


            Log.i(TAG, "onSuccessResponse: Value : " + Value);
            Log.i(TAG, "onSuccessResponse: Year : " + Year);
            Log.i(TAG, "onSuccessResponse: Month : " + Month);
            Log.i(TAG, "onSuccessResponse: Day : " + Day);

            SportItem DorsauxSession = new SportItem();

            DorsauxSession.setRep(Value);
            DorsauxSession.setYear(Year);
            DorsauxSession.setMonth(Month);
            DorsauxSession.setDay(Day);
            DorsauxSessionList.add(DorsauxSession);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //---------------------------------------------------------------------------------
    // Initialisation de la liste
    //---------------------------------------------------------------------------------
    private void initListAll(JSONObject sessionK) {

        try {

            Integer  AbdosValue = Integer.parseInt(sessionK.getString("Abdo"));
            Integer  DoresauxValue = Integer.parseInt(sessionK.getString("Dorsaux"));
            Integer  CordeValue = Integer.parseInt(sessionK.getString("Corde"));
            Integer  SquatsValue = Integer.parseInt(sessionK.getString("Squats"));

            String  Date = sessionK.getString("date");
            String[] DateS = Date.split("-");
            Integer Year = Integer.parseInt(DateS[0]);
            Integer Month = Integer.parseInt(DateS[1]);
            Integer Day = Integer.parseInt(DateS[2]);

            SessionItem AllSession = new SessionItem();

            AllSession.setRepAbdos(AbdosValue);
            AllSession.setRepDorsaux(DoresauxValue);
            AllSession.setRepCorde(CordeValue);
            AllSession.setRepSquats(SquatsValue);

            AllSession.setYear(Year);
            AllSession.setMonth(Month);
            AllSession.setDay(Day);
            AllSessionList.add(AllSession);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

package ch.qoa.an.perfapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
        SportFragment.OnFragmentInteractionListener,
        GlobalFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        CreditFragment.OnFragmentInteractionListener{

    RecapFragment recapFragment;
    SportFragment abdotimeFragment;
    GlobalFragment globalFragment;
    ProfileFragment profileFragment;
    CreditFragment creditFragment;
    //public static boolean logged = false;
    String TAG = "TestApp1";

    BroadcastReceiver mybroadcast = new InternetConnector_Receiver();
    private static boolean noInternet=false;

    private static Snackbar snackbar;

    public static ArrayList<SportItem> AbdoSessionList;
    public static ArrayList<SportItem> DorsauxSessionList;
    public static ArrayList<SportItem> CordeSessionList;
    public static ArrayList<SportItem> SquatsSessionList;
    public static ArrayList<SessionItem> AllSessionList;

    public static String AbdosNum;
    public static String DorseauxNum;
    public static String SquatsNum;
    public static String CordesNum;

    public static boolean Time_nRep=false;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recapFragment = new RecapFragment();
        abdotimeFragment = new SportFragment();
        globalFragment = new GlobalFragment();
        profileFragment = new ProfileFragment();
        creditFragment = new CreditFragment();

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

        //---------------------------------------------------------------------------------
        // Création de la snackbar qui sera utilisée pour informer l'utilisateur qu'il
        // n'y a pas de connexion internet.
        //---------------------------------------------------------------------------------
        snackbar = Snackbar.make(findViewById(R.id.fragment_container), getString(R.string.NoInternet), Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        //---------------------------------------------------------------------------------
        // Détection de la connexion internet à travers un broadcast
        //---------------------------------------------------------------------------------
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mybroadcast, intentFilter);

        isNetworkAvailable();

    }

    //---------------------------------------------------------------------------------
    // Check de la connection internet
    //---------------------------------------------------------------------------------
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        Log.i(TAG, "isNetworkAvailable#############################: " + activeNetworkInfo);

        return activeNetworkInfo != null;

    }

    private void callFragment(Fragment fragmentToCall, String title) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragmentToCall);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onProfileFragmentInteraction(Integer uri) {
        callFragment(profileFragment, getString(R.string.TitleProfile));
    }

    @Override
    public void onCreditFragmentInteraction(Integer uri) {
        callFragment(creditFragment, getString(R.string.TitleCredit));
    }

    @Override
    public void onRecapFragmentInteraction(Integer uri) {
        callFragment(globalFragment, getString(R.string.PerfApp));
    }

    @Override
    public void onAbdoTimeFragmentInteraction(Integer uri) {
        callFragment(recapFragment, getString(R.string.TitleHist));
    }

    @Override
    public void onGlobalFragmentInteraction(Integer uri) {
        if(uri == 800)
        {
            recapFragment.setSessionHistList(AllSessionList);
            processGETRequest_Hist("/days/1");
        }
        else if (uri == 100)
        {
            if(!noInternet) {
                processGETRequest_TimeHist();
            }
        }
        else if(uri == 101)
        {
            if(!noInternet) {
                processGETRequest_RepHist();
            }
        }
        else
        {
            abdotimeFragment.setSport(uri);

            switch(uri) {
                case 0:
                    abdotimeFragment.setSessionList(AbdoSessionList);

                    processGETRequest_SportHist("/sports/1/abdos");
                    break;
                case 1:
                    abdotimeFragment.setSessionList(DorsauxSessionList);
                    processGETRequest_SportHist("/sports/1/dorsaux");
                    break;
                case 2:
                    abdotimeFragment.setSessionList(CordeSessionList);
                    processGETRequest_SportHist("/sports/1/corde");
                    break;
                case 3:
                    abdotimeFragment.setSessionList(SquatsSessionList);
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
        if (!noInternet)
        {
            if (id == R.id.nav_home) {
                callFragment(globalFragment, getString(R.string.PerfApp));
                //onDrawerFragmentInteraction(mapFragment, getString(R.string.toolbarTitleMap));
            } else if (id == R.id.nav_profile) {
                onProfileFragmentInteraction(0);
                //onDrawerFragmentInteraction(listFragment, getString(R.string.toolbarTitleList));
            }
            else if (id == R.id.nav_intro)
            {
                final Intent i = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(i);
            }
            else if (id == R.id.nav_credit)
            {
                onCreditFragmentInteraction(0);
            }
            else if (id == R.id.nav_loout)
            {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                this.finish();
                startActivity(EmailPasswordActivity.intentLogin);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Activate Internet to enter that menu", Toast.LENGTH_LONG).show();
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
                                Log.i(TAG, "onSuccessResponse: *************ESSAI1");
                                JSONObject SessionK = sessions.getJSONObject(k);

                                initListAll(SessionK);
                            }
                            Log.i(TAG, "onSuccessResponse: *************ESSAI2");
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
            Integer ValueTime = Integer.parseInt(sessionK.getString("value_time"));
            String  Date = sessionK.getString("date");
            String[] DateS = Date.split("-");
            Integer Year = Integer.parseInt(DateS[0]);
            Integer Month = Integer.parseInt(DateS[1]);
            Integer Day = Integer.parseInt(DateS[2]);

            SportItem abdoSession = new SportItem();

            abdoSession.setRep(Value);
            abdoSession.setTime(ValueTime);
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
            Integer ValueTime = Integer.parseInt(sessionK.getString("value_time"));
            String  Date = sessionK.getString("date");
            String[] DateS = Date.split("-");
            Integer Year = Integer.parseInt(DateS[0]);
            Integer Month = Integer.parseInt(DateS[1]);
            Integer Day = Integer.parseInt(DateS[2]);

            SportItem CordeSession = new SportItem();

            CordeSession.setRep(Value);
            CordeSession.setTime(ValueTime);
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
            Integer ValueTime = Integer.parseInt(sessionK.getString("value_time"));
            String  Date = sessionK.getString("date");
            String[] DateS = Date.split("-");
            Integer Year = Integer.parseInt(DateS[0]);
            Integer Month = Integer.parseInt(DateS[1]);
            Integer Day = Integer.parseInt(DateS[2]);

            SportItem SquatSession = new SportItem();

            SquatSession.setRep(Value);
            SquatSession.setTime(ValueTime);
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
            Integer ValueTime = Integer.parseInt(sessionK.getString("value_time"));
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
            DorsauxSession.setTime(ValueTime);
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

            Integer  AbdosValue = Integer.parseInt(sessionK.getString("Abdos"));
            Integer  DoresauxValue = Integer.parseInt(sessionK.getString("Dorsaux"));
            Integer  CordeValue = Integer.parseInt(sessionK.getString("Corde"));
            Integer  SquatsValue = Integer.parseInt(sessionK.getString("Squats"));

            Integer  AbdosTimeValue = Integer.parseInt(sessionK.getString("Abdos_time"));
            Integer  DoresauxTimeValue = Integer.parseInt(sessionK.getString("Dorsaux_time"));
            Integer  CordeTimeValue = Integer.parseInt(sessionK.getString("Corde_time"));
            Integer  SquatsTimeValue = Integer.parseInt(sessionK.getString("Squats_time"));

            String  Date = sessionK.getString("date");

            Log.i(TAG, "initListAll: "+Date);
            //Log.i(TAG, "initListAll abdo: "+AbdosValue);
            Log.i(TAG, "initListAll dorsaux: "+DoresauxValue);
            //Log.i(TAG, "initListAll corde: "+CordeValue);
            //Log.i(TAG, "initListAll squat: "+SquatsValue);

            String[] DateS = Date.split("-");
            Integer Year = Integer.parseInt(DateS[0]);
            Integer Month = Integer.parseInt(DateS[1]);
            Integer Day = Integer.parseInt(DateS[2]);

            SessionItem AllSession = new SessionItem();

            AllSession.setRepAbdos(AbdosValue);
            AllSession.setRepDorsaux(DoresauxValue);
            AllSession.setRepCorde(CordeValue);
            AllSession.setRepSquats(SquatsValue);

            AllSession.setTimeAbdos(AbdosTimeValue);
            AllSession.setTimeDorsaux(DoresauxTimeValue);
            AllSession.setTimeCorde(CordeTimeValue);
            AllSession.setTimeSquats(SquatsTimeValue);

            AllSession.setYear(Year);
            AllSession.setMonth(Month);
            AllSession.setDay(Day);
            AllSessionList.add(AllSession);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //---------------------------------------------------------------------------------
    // Boradcast qui check lorsqu'il y a un changement sur la connecitivé
    //---------------------------------------------------------------------------------
    public class InternetConnector_Receiver extends BroadcastReceiver {

        public InternetConnector_Receiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                // Check internet connection and accrding to state change the
                // text of activity by calling method
                if (isNetworkAvailable()) {
                    if(noInternet)
                    {
                        snackbar.setDuration(0);
                        snackbar.show();
                    }
                    noInternet=false;
                    if(Time_nRep)
                    {
                        processGETRequest_TimeHist(); //ESSAI
                    }
                    else
                    {
                        processGETRequest_RepHist(); //ESSAI
                    }

                    Log.i(TAG, "onReceive: ****************************************internet OK");
                } else {
                    snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
                    snackbar.show();
                    noInternet = true;
                    Log.i(TAG, "onReceive: ****************************************internet KO");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

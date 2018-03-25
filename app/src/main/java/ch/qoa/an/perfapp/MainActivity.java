package ch.qoa.an.perfapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity
        extends AppCompatActivity
        implements RecapFragment.OnFragmentInteractionListener,
        AbdoTimeFragment.OnFragmentInteractionListener,
        GlobalFragment.OnFragmentInteractionListener{

    RecapFragment recapFragment;
    AbdoTimeFragment abdotimeFragment;
    GlobalFragment globalFragment;
    public static Intent intentLogin;
    public static Intent intentAct;
    public static boolean logged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recapFragment = new RecapFragment();
        abdotimeFragment = new AbdoTimeFragment();
        globalFragment = new GlobalFragment();

        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, globalFragment);
        fragmentTransaction.commit();

        // Préparation pour l'activité des utilisateurs connectés
        intentAct = new Intent(this, MainActivity.class);

        // Démarrage de l'activité de login
        intentLogin = new Intent(this, EmailPasswordActivity.class);
        if(!logged)
            startActivity(MainActivity.intentLogin);

        //profileFragment = new ProfileFragment();
        //favoritesFragment = new FavoritesFragment();
        //detailFragment = new DetailFragment();

        //mapFragment.setStationList(stationList);
        //ListFragment.setStationList(stationList);
    }

    private void callFragment(Fragment fragmentToCall, String title) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        setTitle(title);
        transaction.replace(R.id.fragment_container, fragmentToCall);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onRecapFragmentInteraction(Integer uri) {
        callFragment(globalFragment, "Global");
        //detailFragment.updateElement(itemAtPosition);
        //callFragment(detailFragment, getString(R.string.toolbarTitleDetail));
    }

    @Override
    public void onAbdoTimeFragmentInteraction(Integer uri) {
        callFragment(recapFragment, "Recap");
        //detailFragment.updateElement(itemAtPosition);
        //callFragment(detailFragment, getString(R.string.toolbarTitleDetail));
    }

    @Override
    public void onGlobalFragmentInteraction(Integer uri) {
        callFragment(abdotimeFragment, "AbdoTime");
        //detailFragment.updateElement(itemAtPosition);
        //callFragment(detailFragment, getString(R.string.toolbarTitleDetail));
        // Test commit
    }

}

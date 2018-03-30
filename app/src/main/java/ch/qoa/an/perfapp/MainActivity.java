package ch.qoa.an.perfapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity
        extends AppCompatActivity
        implements RecapFragment.OnFragmentInteractionListener,
        AbdoTimeFragment.OnFragmentInteractionListener,
        GlobalFragment.OnFragmentInteractionListener{

    RecapFragment recapFragment;
    AbdoTimeFragment abdotimeFragment;
    GlobalFragment globalFragment;
    public static boolean logged = false;
    String TAG = "Test";
    public static FragmentManager fm;

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

        //profileFragment = new ProfileFragment();
        //favoritesFragment = new FavoritesFragment();
        //detailFragment = new DetailFragment();

        //mapFragment.setStationList(stationList);
        //ListFragment.setStationList(stationList);
    }

    private void callFragment(Fragment fragmentToCall, String title) {
        if(logged) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            setTitle(title);
            transaction.replace(R.id.fragment_container, fragmentToCall);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            /*fm = getSupportFragmentManager(); // or 'getSupportFragmentManager();'
            int count = fm.getBackStackEntryCount();
            for(int i = 0; i < count; ++i) {
                fm.popBackStack();
            }*/
            this.finish(); //ESSAI
            startActivity(EmailPasswordActivity.intentLogin);

        }
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

}

package ch.qoa.an.perfapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance(getString(R.string.Title1),
                getString(R.string.Body1),
                R.drawable.intro, Color.parseColor("#212121")));

        addSlide(AppIntroFragment.newInstance(getString(R.string.Title2),
                getString(R.string.Body2),
                R.drawable.choice, Color.parseColor("#212121")));

        addSlide(AppIntroFragment.newInstance(getString(R.string.Title3),
                getString(R.string.Body3),
                R.drawable.all, Color.parseColor("#212121")));


        addSlide(AppIntroFragment.newInstance(getString(R.string.Title4),
                getString(R.string.Body4),
                R.drawable.one, Color.parseColor("#212121")));

        addSlide(AppIntroFragment.newInstance(getString(R.string.Title5),
                getString(R.string.Body5),
                R.drawable.force, Color.parseColor("#212121")));


        /*addSlide(AppIntroFragment.newInstance("Visualization choice",
                "You can choice if you want to visualize your data in duration or repetition",
                R.drawable.choice, getResources().getColor(R.color.colorAccent1)));*/

        // OPTIONAL METHODS
        // Override bar/separator color.
        //setBarColor(Color.parseColor("#3F51B5"));
        setBarColor(Color.parseColor("#212121"));
        //setSeparatorColor(Color.parseColor("#2196F3"));
        setSeparatorColor(getResources().getColor(R.color.colorAccent1));

        // Hide Skip/Done button.
        showSkipButton(true);
        setColorSkipButton(getResources().getColor(R.color.colorAccent1));
        setColorDoneText(getResources().getColor(R.color.colorAccent1));
        //setColorTransitionsEnabled(true);
        setNextArrowColor(getResources().getColor(R.color.colorAccent1));
        setIndicatorColor(getResources().getColor(R.color.colorAccent1),Color.parseColor("#FFFFFF"));
        setProgressButtonEnabled(true);

        // Animations -- use only one of the below. Using both could cause errors.
        //setFadeAnimation(); // OR
        setZoomAnimation(); // OR
        //setFlowAnimation(); // OR
        //setSlideOverAnimation(); // OR
        //setDepthAnimation(); // OR
        //setCustomTransformer(yourCustomTransformer);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        //setVibrate(true);
        //setVibrateIntensity(30);


        // Permissions -- takes a permission and slide number
        //askForPermissions(new String[]{Manifest.permission.CAMERA}, 3);
        /*if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED);
        askForPermissions(new String[]{android.Manifest.permission.CAMERA}, 3);*/
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        finish();
        Toast.makeText(getApplicationContext(), "SKIP INTRO", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
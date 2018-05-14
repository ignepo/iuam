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
        // Use the default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        //1st slide
        addSlide(AppIntroFragment.newInstance(getString(R.string.Title1),
                getString(R.string.Body1),
                R.drawable.intro, Color.parseColor("#212121")));

        //2nd slide
        addSlide(AppIntroFragment.newInstance(getString(R.string.Title2),
                getString(R.string.Body2),
                R.drawable.choice, Color.parseColor("#212121")));

        //3rd slide
        addSlide(AppIntroFragment.newInstance(getString(R.string.Title3),
                getString(R.string.Body3),
                R.drawable.all, Color.parseColor("#212121")));

        //4th slide
        addSlide(AppIntroFragment.newInstance(getString(R.string.Title4),
                getString(R.string.Body4),
                R.drawable.one, Color.parseColor("#212121")));

        //5th slide
        addSlide(AppIntroFragment.newInstance(getString(R.string.Title5),
                getString(R.string.Body5),
                R.drawable.force, Color.parseColor("#212121")));

        //Set the color
        setBarColor(Color.parseColor("#212121"));
        setSeparatorColor(getResources().getColor(R.color.colorAccent1));

        // Show Skip/Done button.
        showSkipButton(true);

        //Set color of the action buttons
        setColorSkipButton(getResources().getColor(R.color.colorAccent1));
        setColorDoneText(getResources().getColor(R.color.colorAccent1));
        setNextArrowColor(getResources().getColor(R.color.colorAccent1));
        setIndicatorColor(getResources().getColor(R.color.colorAccent1),Color.parseColor("#FFFFFF"));
        setProgressButtonEnabled(true);

        //Chose one of the animation changing the slide
        // Animations -- use only one of the below. Using both could cause errors.
        //setFadeAnimation(); // OR
        setZoomAnimation(); // OR
        //setFlowAnimation(); // OR
        //setSlideOverAnimation(); // OR
        //setDepthAnimation(); // OR
        //setCustomTransformer(yourCustomTransformer);
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
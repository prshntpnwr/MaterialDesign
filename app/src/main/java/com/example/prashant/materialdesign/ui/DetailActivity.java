package com.example.prashant.materialdesign.ui;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.prashant.materialdesign.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TransitionSet transitionSet = new TransitionSet();

        // slide views in activity layout
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setInterpolator(
                AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        slide.excludeTarget(findViewById(R.id.checkbox), true);
        slide.excludeTarget(findViewById(R.id.imageView), true);
        slide.setDuration(400);
        transitionSet.addTransition(slide);

        //
        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.setDuration(100);
        fade.setStartDelay(300);
        fade.addTarget(findViewById(R.id.checkbox));
        transitionSet.addTransition(fade);

        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setEnterTransition(transitionSet);

        final ImageView imageView = findViewById(R.id.action_image);
        final AnimatedVectorDrawable crossToTick = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_cross_to_tick);
        final AnimatedVectorDrawable tickToCross = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_tick_to_cross);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageDrawable(imageView.getDrawable() == crossToTick ? tickToCross : crossToTick);
//                imageView.setImageDrawable(crossToTick);
                crossToTick.start();
            }
        });
    }

    public void dismiss(View view) {
//        finishAfterTransition();
    }
}

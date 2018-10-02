package com.example.prashant.materialdesign.ui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.prashant.materialdesign.R;
import com.example.prashant.materialdesign.ui.transitions.GravityArcMotion;
import com.example.prashant.materialdesign.ui.widget.ElasticDragDismissFrameLayout;

public class DetailActivity extends AppCompatActivity {

    private ElasticDragDismissFrameLayout.ElasticDragDismissCallback dragDismissCallback;
    private ElasticDragDismissFrameLayout draggableFrame;

    private FloatingActionButton fab;

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
        final Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        fade.setDuration(100);
        fade.setStartDelay(300);
        fade.addTarget(findViewById(R.id.checkbox));
        transitionSet.addTransition(fade);

        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setEnterTransition(transitionSet);

        fab = findViewById(R.id.fab);

        transitionSet.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                Animation scaleAnimation = AnimationUtils.loadAnimation(DetailActivity.this, R.anim.view_scale_anim);
                scaleAnimation.setDuration(DetailActivity.this.getResources().getInteger(android.R.integer.config_longAnimTime));
                fab.setAnimation(scaleAnimation);
                scaleAnimation.start();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

//        final ImageView imageView = findViewById(R.id.action_image);
//        final AnimatedVectorDrawable crossToTick = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_cross_to_tick);
//        final AnimatedVectorDrawable tickToCross = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_tick_to_cross);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageView.setImageDrawable(imageView.getDrawable() == crossToTick ? tickToCross : crossToTick);
////                imageView.setImageDrawable(crossToTick);
//                crossToTick.start();
//            }
//        });

        draggableFrame = findViewById(R.id.draggable_frame);
        dragDismissCallback = new ElasticDragDismissFrameLayout.ElasticCallListener(this) {
            @Override
            public void onDragDismissed() {
                super.onDragDismissed();
            }

            @Override
            public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {
                super.onDrag(elasticOffset, elasticOffsetPixels, rawOffset, rawOffsetPixels);
            }
        };
    }

    public void dismiss(View view) {
//        finishAfterTransition();
    }

    public void timeline(View view) {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        draggableFrame.addListener(dragDismissCallback);
    }

    @Override
    protected void onPause() {
        draggableFrame.removeListener(dragDismissCallback);
        super.onPause();
    }


    boolean isReturnAnimation = false;

    public void fabClick(View view) {
//        TransitionManager.beginDelayedTransition((ViewGroup) view,
//                new ChangeBounds().setPathMotion(new ArcMotion()));

        Path path = new Path();
        path.arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
        animator.setDuration(2000);
        animator.start();

//        ChangeBounds bounds = new ChangeBounds();
//        bounds.setPathMotion(new GravityArcMotion());
//        bounds.setInterpolator(new FastOutSlowInInterpolator());
//        TransitionManager.beginDelayedTransition(draggableFrame, bounds);
//
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
//        params.gravity = isReturnAnimation ? (Gravity.START | Gravity.TOP) :
//                (Gravity.BOTTOM | Gravity.END);
//        view.setLayoutParams(params);
    }
}

package com.example.prashant.materialdesign.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Transition;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.prashant.materialdesign.R;
import com.example.prashant.materialdesign.ui.transitions.GravityArcMotion;

public class TranslationActivity extends AppCompatActivity implements View.OnClickListener {

    Animation alphaAppear, alphaDisappear;
    int x, y, width, height, hypotenuse;
    float pixelDensity;

    ImageView imageView;
    ImageButton closeButton;
    FloatingActionButton fab;
    RelativeLayout revealView;
    LinearLayout layoutButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageView);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(this);
        revealView = findViewById(R.id.linearView);
        layoutButtons = findViewById(R.id.layoutButtons);

        pixelDensity = getResources().getDisplayMetrics().density;

        alphaAppear = AnimationUtils.loadAnimation(this, R.anim.alpha_show);
        alphaDisappear = AnimationUtils.loadAnimation(this, R.anim.alpha_hide);
    }

    void launchFab() {
        width = imageView.getWidth();
        height = imageView.getHeight();

        x = width / 2;
        y = height / 2;
        hypotenuse = (int) Math.hypot(x, y);

        x = (int) (x - ((16 * pixelDensity) + (28 * pixelDensity)));

        FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)
                revealView.getLayoutParams();
        parameters.height = imageView.getHeight();
        revealView.setLayoutParams(parameters);

        ObjectAnimator objectAnimator =
                ObjectAnimator.ofFloat(fab, View.X, View.Y, new GravityArcMotion().getPath(0, 0, -x, -y));
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Animator anim = ViewAnimationUtils.createCircularReveal(revealView, width / 2, height / 2, 28 * pixelDensity, hypotenuse);
                anim.setDuration(350);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        layoutButtons.setVisibility(View.VISIBLE);
                        closeButton.setVisibility(View.VISIBLE);
                        layoutButtons.startAnimation(alphaAppear);
                        closeButton.startAnimation(alphaAppear);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
                fab.setVisibility(View.GONE);
                revealView.setVisibility(View.VISIBLE);
                anim.start();
            }
        });
        objectAnimator.start();
    }

    void startFab() {
        width = imageView.getWidth();
        height = imageView.getHeight();

        x = width / 2;
        y = height / 2;
        hypotenuse = (int) Math.hypot(x, y);

        x = (int) (x - ((16 * pixelDensity) + (28 * pixelDensity)));

        FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)
                revealView.getLayoutParams();
        parameters.height = imageView.getHeight();
        revealView.setLayoutParams(parameters);

            fab.animate()
                    .translationX(-x)
                    .translationY(-y)
                    .setDuration(200)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, width / 2, height / 2, 28 * pixelDensity, hypotenuse);
                            anim.setDuration(350);
                            anim.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    layoutButtons.setVisibility(View.VISIBLE);
                                    closeButton.setVisibility(View.VISIBLE);
                                    layoutButtons.startAnimation(alphaAppear);
                                    closeButton.startAnimation(alphaAppear);
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {
                                }
                            });
                            fab.setVisibility(View.GONE);
                            revealView.setVisibility(View.VISIBLE);
                            anim.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {
                        }
                    });
    }

    void closeFab() {
        alphaDisappear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animator anim = ViewAnimationUtils.createCircularReveal(revealView, width / 2, height / 2, hypotenuse, 28 * pixelDensity);
                anim.setDuration(350);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        revealView.setVisibility(View.GONE);
                        fab.setVisibility(View.VISIBLE);
                        fab.animate()
                                .translationX(0f)
                                .translationY(0f)
                                .setDuration(200).setListener(null);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
                anim.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        layoutButtons.setVisibility(View.GONE);
        closeButton.setVisibility(View.GONE);
        layoutButtons.startAnimation(alphaDisappear);
        closeButton.startAnimation(alphaDisappear);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                startFab();
                break;

            case R.id.closeButton:
                closeFab();
                break;
        }
    }
}


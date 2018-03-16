package com.example.prashant.materialdesign.ui;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.prashant.materialdesign.R;
import com.example.prashant.materialdesign.ui.transitions.FabTransform;
import com.example.prashant.materialdesign.ui.transitions.MorphTransform;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        FrameLayout container = findViewById(R.id.container);

        if (!FabTransform.setup(this, container)) {
            MorphTransform.setup(this, container,
                    ContextCompat.getColor(this, R.color.background_light),
                    getResources().getDimensionPixelSize(R.dimen.dialog_corners));
        }
    }

    public void dismiss(View view) {
        finishAfterTransition();
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }
}

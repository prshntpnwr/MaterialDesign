package com.example.prashant.materialdesign.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.prashant.materialdesign.R;
import com.example.prashant.materialdesign.ui.transitions.FabTransform;
import com.example.prashant.materialdesign.ui.transitions.MorphTransform;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        LinearLayout container = findViewById(R.id.container);

        if (!FabTransform.setup(this, container)) {
            MorphTransform.setup(this, container,
                    ContextCompat.getColor(this, R.color.background_light),
                    getResources().getDimensionPixelSize(R.dimen.dialog_corners));
        }
    }

    public void dismiss(View view) {
        finishAfterTransition();
    }

    public void translation(View view) {
        Intent intent = new Intent(this, TranslationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }
}

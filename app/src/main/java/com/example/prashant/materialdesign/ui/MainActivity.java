package com.example.prashant.materialdesign.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.prashant.materialdesign.R;
import com.example.prashant.materialdesign.ui.adapter.MainAdapter;
import com.example.prashant.materialdesign.ui.transitions.FabTransform;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.view_scale_anim);
        fab.startAnimation(scaleUp);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter adapter = new MainAdapter(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Intent intent = new Intent(MainActivity.this, DialogActivity.class);
                FabTransform.addExtras(intent,
                        ContextCompat.getColor(MainActivity.this, R.color.colorAccent),
                        android.R.drawable.ic_input_add);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, fab,
                        getString(R.string.transition_dialog));
                startActivity(intent, options.toBundle());
                break;
        }
    }
}

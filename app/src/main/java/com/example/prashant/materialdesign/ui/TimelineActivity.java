package com.example.prashant.materialdesign.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.prashant.materialdesign.R;
import com.example.prashant.materialdesign.model.Timeline;
import com.example.prashant.materialdesign.ui.adapter.TimelineAdapter;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity implements TimelineAdapter.EventCallbackListener {

    private ArrayList<Timeline> timelineList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Timeline");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        setUpRecyclerView();
    }

    private void populateTimelineList() {
        for (int i = 0; i < 8; i++) {
            Timeline timeline = new Timeline();
            if (i == 0) {
                timeline.setStatus(1);
                timeline.setTime("Jan 20, 9:58 AM");
                timeline.setTitle("MockUp Design");
                timeline.setSubTitle("MockUp are ready");
            } else if (i == 1) {
                timeline.setStatus(1);
                timeline.setTime("Jan 20, 03:50 PM");
                timeline.setTitle("Layouts");
                timeline.setSubTitle("Layout with Dimensions and Colors");
            } else if (i == 2) {
                timeline.setStatus(1);
                timeline.setTime("Jan 21, 10:02 AM");
                timeline.setTitle("Dummy Test");
                timeline.setSubTitle("Layout Test With Dummy Data");
            }  else if (i == 3) {
                timeline.setStatus(1);
                timeline.setTime("Jan 21, 04:40 PM");
                timeline.setTitle("Drawables");
                timeline.setSubTitle("Roster and Vector Drawable");
            } else if (i == 4) {
                timeline.setStatus(0);
                timeline.setTime("Jan 22, 09:55 PM");
                timeline.setTitle("Animations");
                timeline.setSubTitle("Working On Transition and Vector Animations");
            } else if (i == 5) {
                timeline.setStatus(-1);
                timeline.setTime("null");
                timeline.setTitle("Database");
                timeline.setSubTitle("No Info");
            } else if (i == 6) {
                timeline.setStatus(-1);
                timeline.setTime("null");
                timeline.setTitle("Remote EndPoint Task");
                timeline.setSubTitle("No Info");
            } else if (i == 7) {
                timeline.setStatus(-1);
                timeline.setTime("null");
                timeline.setTitle("Final Testing");
                timeline.setSubTitle("No Info");
            }

            timelineList.add(timeline);
        }
    }

    private void setUpRecyclerView() {
        populateTimelineList();

        final RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        TimelineAdapter detailAdapter = new TimelineAdapter(this, this, timelineList);
        mRecyclerView.setAdapter(detailAdapter);
    }

    @Override
    public void onEventClick(String EventType) {

    }
}

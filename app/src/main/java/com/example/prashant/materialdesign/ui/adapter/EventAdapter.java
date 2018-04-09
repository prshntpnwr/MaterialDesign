package com.example.prashant.materialdesign.ui.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.prashant.materialdesign.R;
import com.example.prashant.materialdesign.model.Timeline;
import com.example.prashant.materialdesign.ui.EventActivity;

import java.util.ArrayList;

/**
 * Created by prashantpanwar on 09/03/18.
 */

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Timeline> mTimelineList;
    private int mPosition = -1;

    private EventCallbackListener mCallbackListener;

    public interface EventCallbackListener {
        void onEventClick(String EventType);
    }

    public EventAdapter(Context mContext, EventCallbackListener callbackListener,
                        ArrayList<Timeline> mTimelineList) {
        this.mContext = mContext;
        this.mCallbackListener = callbackListener;
        this.mTimelineList = mTimelineList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_header, parent, false);
                return new ViewHolder(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
                return new ViewHolderEvent(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                ((ViewHolder) holder).bindView();
                break;

            case 1:
                ((ViewHolderEvent) holder).bindView(position, mTimelineList.get(position));
                break;
        }
    }

    private int getEventColor(int position) {
        int color;
        if (position % 5 == 0)
            color = ContextCompat.getColor(mContext, R.color.orange_200);
        else if (position % 5 == 1)
            color = ContextCompat.getColor(mContext, R.color.indigo_200);
        else if (position % 5 == 2)
            color = ContextCompat.getColor(mContext, R.color.light_blue_200);
        else if (position % 5 == 3)
            color = ContextCompat.getColor(mContext, R.color.red_200);
        else if (position % 5 == 4)
            color = ContextCompat.getColor(mContext, R.color.light_green_200);
        else
            color = ContextCompat.getColor(mContext, R.color.pink_200);

        return color;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return mTimelineList.size();
    }

    public class ViewHolderEvent extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView statusImageView;
        ProgressBar lineView;
        LinearLayout statusLayout;
        TextView dateTime, eventTitle, displayText;
        Timeline timeline;

        ViewHolderEvent(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            statusLayout = itemView.findViewById(R.id.status_indicator_layout);
            statusImageView = itemView.findViewById(R.id.status);
            lineView = itemView.findViewById(R.id.view_line);
            dateTime = itemView.findViewById(R.id.event_date_time);
            eventTitle = itemView.findViewById(R.id.event_title);
            displayText = itemView.findViewById(R.id.event_display_text);
        }

        private void bindView(int pos, Timeline timeline) {
            int color = timeline.getStatus() == 1 ? ContextCompat.getColor(mContext, R.color.green_success) : getEventColor(pos);
            this.timeline = timeline;

            int progress = timeline.getStatus() == 1 ? 100 : 0;
            int drawable = timeline.getStatus() == 1 ? R.drawable.ic_success : (timeline.getStatus() == 0 ? R.drawable.ic_sync : R.drawable.ic_circle);

            statusImageView.setBackgroundResource(drawable);
            statusImageView.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
            statusImageView.setBackgroundTintList(ColorStateList.valueOf(color));

            if (pos > mPosition) {
                Animation scaleAnimation = AnimationUtils.loadAnimation(mContext, R.anim.view_scale_anim);
                scaleAnimation.setDuration(mContext.getResources().getInteger(android.R.integer.config_longAnimTime));
                statusLayout.setAnimation(scaleAnimation);
                scaleAnimation.start();

                ObjectAnimator animation = ObjectAnimator.ofInt(lineView, "progress", 0, progress);
                animation.setDuration(700);
                animation.setStartDelay(100);
                animation.setInterpolator(new LinearInterpolator());
                animation.start();

                mPosition = pos;
            } else {
                lineView.setProgress(progress);
            }

            lineView.setVisibility(pos == mTimelineList.size() - 1 ? View.GONE : View.VISIBLE);
            dateTime.setText(timeline.getTime().equals("null") ? "--:--" : timeline.getTime());

            eventTitle.setText(timeline.getTitle());
            displayText.setText(timeline.getSubTitle());
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == itemView.getId())
                mCallbackListener.onEventClick(timeline.getTime());
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }

        void bindView() {
            RecyclerView.LayoutParams cardParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            cardParams.setMargins(0, 0, 0, EventActivity.dipToPixels(16, mContext));
            itemView.setLayoutParams(cardParams);
        }
    }
}

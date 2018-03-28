package com.example.prashant.materialdesign.ui.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.prashant.materialdesign.R;
import com.example.prashant.materialdesign.ui.DetailActivity;

/**
 * Created by prashant on 17/3/18.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Context mContext;

    public MainAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        holder.bindView();
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;

        ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.player_avatar);
        }

        private void bindView() {
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailActivity.class);

                    Pair[] transitionPairs = new Pair[2];
                    transitionPairs[0] = Pair.create((View)avatar, avatar.getTransitionName()); // Transition the Toolbar
                    transitionPairs[1] = Pair.create(itemView, mContext.getString(R.string.transition_background)); // Transition the content_area (This will be the content area on the detail screen)

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                            (Activity) mContext, transitionPairs);

                    mContext.startActivity(intent, options.toBundle());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemView.setActivated(!itemView.isActivated());
                }
            });
        }
    }
}

package com.example.prashant.materialdesign.ui.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        boolean isActive = false;

        ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.player_avatar);
        }

        private void bindView(int position) {
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                            (Activity) mContext, avatar, mContext.getString(R.string.transition_avatar)
                    );

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

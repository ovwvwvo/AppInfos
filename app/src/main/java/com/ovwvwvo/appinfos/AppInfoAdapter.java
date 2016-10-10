package com.ovwvwvo.appinfos;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guang on 16/9/1.
 */
public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.AppInfoViewHolder> {

    private LayoutInflater inflater;
    private List<AppInfoModel> models = new ArrayList<>();

    public AppInfoAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AppInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppInfoViewHolder(inflater.inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(AppInfoViewHolder holder, int position) {
        holder.cardView.setBackgroundColor(holder.getColor(position));
        holder.icon.setImageDrawable(models.get(position).getIcon());
        holder.appName.setText(models.get(position).getAppName());
        holder.packageName.setText(models.get(position).getPackageName());
    }

    public void setModels(List<AppInfoModel> models) {
        this.models = models;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class AppInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.appName)
        TextView appName;
        @BindView(R.id.packageName)
        TextView packageName;
        @BindView(R.id.icon)
        ImageView icon;
        @BindArray(R.array.colors)
        int[] colors;

        AppInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.copy)
        void copy() {

        }

        int getColor(int position) {
            return colors[position % colors.length];
        }
    }
}

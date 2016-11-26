package com.ovwvwvo.appinfos.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.model.AppInfoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ovwvwvo on 16/9/1.
 */
public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.AppInfoViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<AppInfoModel> models = new ArrayList<>();

    public AppInfoAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public AppInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppInfoViewHolder(inflater.inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(AppInfoViewHolder holder, int position) {
        holder.icon.setImageDrawable(models.get(position).getIcon());
        holder.appName.setText(models.get(position).getAppName());
        holder.packageName.setText(models.get(position).getPackageName());
    }

    public void setModels(List<AppInfoModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    public void clearModel() {
        this.models.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class AppInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.appName)
        TextView appName;
        @BindView(R.id.packageName)
        TextView packageName;

        AppInfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.copy)
        void copy() {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            final String content = models.get(getAdapterPosition()).toString();
            ClipData clip = ClipData.newPlainText("appInfo", content);
            clipboard.setPrimaryClip(clip);
            Snackbar.make(icon, R.string.copy, Snackbar.LENGTH_LONG)
                .setAction(R.string.share, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String msg = content;
                        intent.putExtra(Intent.EXTRA_TEXT, msg);
                        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_title)));
                    }
                })
                .show();
        }
    }
}

package com.example.conquer_app;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class InfluencerAdapter extends RecyclerView.Adapter<InfluencerAdapter.ViewHolder> {

    private List<Influencer> originalList;
    private List<Influencer> filteredList;

    public InfluencerAdapter(List<Influencer> list) {
        this.originalList = list;
        this.filteredList = new ArrayList<>(list);
    }

    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            String lower = query.toLowerCase();
            for (Influencer i : originalList) {
                if (i.getName().toLowerCase().contains(lower)
                        || i.getHandle().toLowerCase().contains(lower)
                        || i.getCategory().toLowerCase().contains(lower)) {
                    filteredList.add(i);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_influencer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Influencer inf = filteredList.get(position);
        holder.name.setText(inf.getName());
        holder.handle.setText(inf.getHandle());
        holder.location.setText("📍 " + inf.getLocation());
        holder.category.setText(inf.getCategory());
        holder.followers.setText(inf.getFollowers());

        // Click → open detail screen
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), InfluencerDetailActivity.class);
            intent.putExtra("name",      inf.getName());
            intent.putExtra("handle",    inf.getHandle());
            intent.putExtra("location",  inf.getLocation());
            intent.putExtra("category",  inf.getCategory());
            intent.putExtra("followers", inf.getFollowers());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return filteredList.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, handle, location, category, followers;
        ViewHolder(View itemView) {
            super(itemView);
            name      = itemView.findViewById(R.id.tv_influencer_name);
            handle    = itemView.findViewById(R.id.tv_influencer_handle);
            location  = itemView.findViewById(R.id.tv_influencer_location);
            category  = itemView.findViewById(R.id.tv_category);
            followers = itemView.findViewById(R.id.tv_followers);
        }
    }
}
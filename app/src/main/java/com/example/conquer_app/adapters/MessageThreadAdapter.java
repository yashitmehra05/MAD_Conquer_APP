package com.example.conquer_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conquer_app.R;
import com.example.conquer_app.models.MessageThread;

import java.util.List;

public class MessageThreadAdapter extends RecyclerView.Adapter<MessageThreadAdapter.ViewHolder> {

    private List<MessageThread> threads;

    public MessageThreadAdapter(List<MessageThread> threads) {
        this.threads = threads;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_thread, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageThread thread = threads.get(position);
        holder.tvName.setText(thread.getName());
        holder.tvHandle.setText(thread.getHandle());
        holder.tvLastMessage.setText(thread.getLastMessage());
        holder.ivAvatar.setImageResource(thread.getAvatarResId());

        holder.itemView.setOnClickListener(v ->
                Toast.makeText(v.getContext(),
                        "Opening chat with " + thread.getName(),
                        Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return threads.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvHandle;
        TextView tvLastMessage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
        }
    }
}

package com.example.conquer_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.conquer_app.R;
import com.example.conquer_app.models.MessageThread;
import java.util.List;

public class MessageThreadAdapter extends RecyclerView.Adapter<MessageThreadAdapter.ViewHolder> {

    public interface OnThreadClickListener {
        void onClick(MessageThread thread);
    }

    private List<MessageThread> threads;
    private OnThreadClickListener listener;

    public MessageThreadAdapter(List<MessageThread> threads, OnThreadClickListener listener) {
        this.threads  = threads;
        this.listener = listener;
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
        holder.itemView.setOnClickListener(v -> listener.onClick(thread));
    }

    @Override
    public int getItemCount() { return threads.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvName, tvHandle, tvLastMessage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar      = itemView.findViewById(R.id.ivAvatar);
            tvName        = itemView.findViewById(R.id.tvName);
            tvHandle      = itemView.findViewById(R.id.tvHandle);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
        }
    }
}
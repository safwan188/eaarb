package com.example.eaarb.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eaarb.Message;
import com.example.eaarb.R;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messages;
    private LayoutInflater mInflater;

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
        private TextView content;
        private RelativeLayout press;


        private MessageViewHolder(View itemView) {
            super(itemView);
            this.content = itemView.findViewById(R.id.theMessage);
            this.time = itemView.findViewById(R.id.theDate);
            this.press = itemView.findViewById(R.id.messageLayout);
        }

    }

    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mInflater.inflate(R.layout.senttocontact, parent, false);
        return new MessageViewHolder(itemView);
    }
    public MessageAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            holder.content.setText(current.content);
            holder.time.setText(current.created);
            if (current.sent) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.press.getLayoutParams();
                params.setMargins(0,0,10,0);
                params.addRule(RelativeLayout.ALIGN_PARENT_START);
                holder.press.setLayoutParams(params);
                holder.press.setBackgroundResource(R.drawable.mysend);
            } else {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.press.getLayoutParams();
                params.setMargins(10,0,0,0);
                params.addRule(RelativeLayout.ALIGN_PARENT_END);
                holder.press.setLayoutParams(params);
                holder.press.setBackgroundResource(R.drawable.mysend);
            }
        }

    }


    public void setMessages(List<Message> m) {
        messages = m;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        } else return 0;
    }



}
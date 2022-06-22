package com.example.eaarb.adapters;

import com.example.eaarb.ContactConnected;



import com.example.eaarb.Message;
import com.example.eaarb.MyContacts;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eaarb.Contact;

import com.example.eaarb.R;

import java.util.List;
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contacts;
    private List<Message> messages;
    private LayoutInflater mInflater;
    private Context context;


    class ContactViewHolder extends RecyclerView.ViewHolder {


        private TextView contactName;
        private TextView lastMessage;
        private TextView time;
        private ConstraintLayout press;
        //private ImageView image;


        private ContactViewHolder(View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.nickName);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            press = itemView.findViewById(R.id.contactview);
            time = itemView.findViewById(R.id.date);
        }

    }

    public ContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;


        {
        }
    }

    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_contact_view, parent, false);
        return new ContactViewHolder(itemView);
    }


    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if (contacts != null) {
            final Contact current = contacts.get(position);

            holder.contactName.setText(current.contName);

            if((messages.get(position) != null)) {
                final Message message = messages.get(position);
            holder.lastMessage.setText(message.content);
           holder.time.setText(message.created);
            } else {
               holder.lastMessage.setText("");
            }
            holder.press.setOnClickListener(v -> {
                goToContact(current.contName);
            });
            //holder.image.setImageDrawable(R.drawable.ic_account_circle_);
            ContactConnected contactConnected = new ContactConnected();


        }
    }

    public void setContacts(List<Contact> c) {
        contacts = c;
        notifyDataSetChanged();
    }
    public void setMessages(List<Message> c) {
        this.messages = c;
        notifyDataSetChanged();
    }


    public int getItemCount() {
        if (contacts != null) {
            return contacts.size();
        } else return 0;
    }


    public List<Contact> getContacts() {
        return contacts;
    }


    public void goToContact(String c) {
        ContactConnected contactConnected = new ContactConnected();
        contactConnected.setConected(c);
        Intent i = new Intent(context, MyContacts.class);
        context.startActivity(i);
    }

}
package com.example.pc.simplechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    public MessageAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Message> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message msg = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.msg_item, parent, false);
        }
        // Lookup view for data population
        TextView msgView = (TextView) convertView.findViewById(R.id.textViewMsg);
        // Populate the data into the template view using the data object
        msgView.setText(msg.getContent());
/*        TextView msgViewEmail = (TextView) convertView.findViewById(R.id.textViewMsgEmail);
        // Populate the data into the template view using the data object
        msgViewEmail.setText(msg.getEmail());*/
        // Return the completed view to render on screen
        return convertView;


    }
}

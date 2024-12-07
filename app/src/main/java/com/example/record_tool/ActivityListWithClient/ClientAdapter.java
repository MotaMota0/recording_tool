package com.example.record_tool.ActivityListWithClient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.record_tool.R;

import java.util.ConcurrentModificationException;
import java.util.List;

public class ClientAdapter extends ArrayAdapter<Client> {

    private Context context;
    private int resource;
    private List<Client> clientList;

    public ClientAdapter(Context context, int resource, List<Client> clientList) {
        super(context, resource, clientList);
        this.context = context;
        this.resource = resource;
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, parent, false);
        }

        Client client = clientList.get(position);

        TextView clientName = view.findViewById(R.id.clientName);
        TextView clientTime = view.findViewById(R.id.clientTime);

        clientName.setText(client.getName());
        clientTime.setText(client.getTime());

        return view;
    }
}


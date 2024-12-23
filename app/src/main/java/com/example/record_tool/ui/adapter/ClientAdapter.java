package com.example.record_tool.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.record_tool.R;
import com.example.record_tool.model.Client;

import java.util.List;

public class ClientAdapter extends ArrayAdapter<Client> {

    private Context context;
    private int resource;

    public ClientAdapter(Context context, int resource, List<Client> clientList) {
        super(context, resource, clientList);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        //View view = super.getView(position,convertView,parent);



        if (convertView == null) {
            // Если convertView равен null, создаем новый элемент
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            // Создаем ViewHolder для хранения ссылок на UI элементы
            viewHolder = new ViewHolder();

            viewHolder.clientName = convertView.findViewById(R.id.user_name);
            viewHolder.clientTime = convertView.findViewById(R.id.user_time);
            viewHolder.checkBoxApply = convertView.findViewById(R.id.checkBox);

            // Сохраняем ViewHolder в convertView для последующего использования
            convertView.setTag(viewHolder);
        } else {
            // Если convertView не равен null, то извлекаем ViewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Получаем текущего клиента из списка
        Client client = getItem(position);

        if (client != null) {
            // Заполняем данные в элементы UI
            viewHolder.clientName.setText(client.getName());
            viewHolder.clientTime.setText(client.getTime());

            viewHolder.checkBoxApply.setChecked(client.isSelected());

            viewHolder.checkBoxApply.setOnCheckedChangeListener((buttonView, isChecked) -> {
                client.setSelected(isChecked);
            });

        }

        return convertView;
    }

    // Вспомогательный класс для хранения UI элементов
    private static class ViewHolder {
        TextView clientName;
        TextView clientTime;
        CheckBox checkBoxApply;

    }
    private boolean getCheckBox(){
        ViewHolder viewHolder  = new ViewHolder();
        return viewHolder.checkBoxApply.isChecked();
    }
}

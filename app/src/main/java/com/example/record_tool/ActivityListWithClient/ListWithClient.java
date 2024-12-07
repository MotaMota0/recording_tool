package com.example.record_tool.ActivityListWithClient;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.MasterReg.MasterAuthActivity;
import com.example.record_tool.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListWithClient extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String MASTER_NAME_KEY = "name";

    private List<Client> clientList;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_lists_of_client);

        clientList = new ArrayList<>();
        listView = findViewById(R.id.listViewClient);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Проверяем, есть ли сохранённое имя мастера
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String masterName = preferences.getString(MASTER_NAME_KEY, null);

        if (masterName == null) {
            // Если имя мастера не найдено, перенаправляем на экран авторизации
            Toast.makeText(this,"Master поля не найдено", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(this, MasterAuthActivity.class);
            //startActivity(intent);
            //finish(); // Завершаем текущую активность, чтобы пользователь не вернулся назад
        } else {
            // Загружаем список клиентов для мастера
            loadClientList(firestore, masterName);
        }
    }

    private void loadClientList(FirebaseFirestore firestore, String masterName) {
        firestore.collection("booking")
                .whereEqualTo("masterName", masterName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("Name");
                            String time = document.getString("Time");
                            clientList.add(new Client(name, time));
                        }
                        ClientAdapter adapter = new ClientAdapter(ListWithClient.this, R.layout.list_item, clientList);
                        listView.setAdapter(adapter);
                        Toast.makeText(ListWithClient.this, "Data Loaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ListWithClient.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}







































/*
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.MainScreenForMaster;
import com.example.record_tool.MasterReg.Master;
import com.example.record_tool.MasterReg.MasterActivity;
import com.example.record_tool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListWithClient extends AppCompatActivity {
    private List <Client> clientList;
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_lists_of_client);

        clientList = new ArrayList<>();

        listView = findViewById(R.id.listViewClient);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        String masterName = getIntent().getStringExtra("masterName");

        if (masterName == null || masterName.isEmpty()) {
            Toast.makeText(this, "Master name is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка существования аккаунта мастера
        checkIfMasterExists(firestore, masterName, new MasterCheckCallback() {
            @Override
            public void onResult(boolean exists) {
                if (exists) {
                    loadClientList(firestore, masterName);
                } else {
                    Toast.makeText(ListWithClient.this, "Account not found. Please register first.", Toast.LENGTH_SHORT).show();
                    // Открыть экран регистрации мастера
                    Intent intent = new Intent(ListWithClient.this, MasterActivity.class);
                    startActivity(intent);
                    finish(); // Завершаем текущую активность, если мастер не зарегистрирован
                }
            }
        });
    }

    // Функция для проверки существования мастера в базе данных
    private void checkIfMasterExists(FirebaseFirestore firestore, String masterName, MasterCheckCallback callback) {
        firestore.collection("Master") // Коллекция, где хранятся мастера
                .whereEqualTo("Name", masterName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        callback.onResult(true); // Мастер найден
                    } else {
                        callback.onResult(false); // Мастер не найден
                    }
                });
    }

    // Функция для загрузки списка клиентов
    private void loadClientList(FirebaseFirestore firestore, String masterName) {
        firestore.collection("booking")
                .whereEqualTo("masterName", masterName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("Name");
                            String time = document.getString("Time");
                            clientList.add(new Client(name, time));
                        }
                        ClientAdapter adapter = new ClientAdapter(ListWithClient.this, R.layout.name_item, clientList);
                        listView.setAdapter(adapter);
                        Toast.makeText(ListWithClient.this, "Data Loaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ListWithClient.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Интерфейс обратного вызова для проверки мастера
    interface MasterCheckCallback {
        void onResult(boolean exists);
    }
*/































        /*String masterName = getIntent().getStringExtra("masterName");
        if (masterName == null || masterName.isEmpty()) {
            Toast.makeText(this, "Master name is missing", Toast.LENGTH_SHORT).show();

        }else {

            firestore.collection("booking")
                    .whereEqualTo("masterName", masterName)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String name = document.getString("Name");
                                    String time = document.getString("Time");
                                    clientList.add(new Client(name, time));
                                }
                                ClientAdapter adapter = new ClientAdapter(ListWithClient.this, R.layout.name_item, clientList);
                                listView.setAdapter(adapter);
                                Toast.makeText(ListWithClient.this, "Data Loaded Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ListWithClient.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }*/








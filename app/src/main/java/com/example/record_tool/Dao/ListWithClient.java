/*
package com.example.record_tool.ActivityListWithClientDao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.RegisterDao.StartActivity;
import com.example.record_tool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListWithClient extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String MASTER_NAME_KEY = "master_name"; // Для сохранения имени мастера

    private List<Client> clientList;
    private ListView listView;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_lists_of_client);

        listView = findViewById(R.id.listViewClient);
        clientList = new ArrayList<>();

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Проверяем, авторизован ли пользователь
        if (auth.getCurrentUser() == null) {
            // Если пользователь не авторизован, перенаправляем на экран входа
            redirectToLogin();
            return;
        }

        // Получаем UID текущего пользователя
        String uid = auth.getCurrentUser().getUid();

        // Загружаем данные мастера из Firestore
        loadMasterData(uid);
    }

    private void loadMasterData(String uid) {
        firestore.collection("Master")
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot document = task.getResult();
                        String masterName = document.getString("Name");

                        if (masterName != null) {
                            // Сохраняем имя мастера в SharedPreferences
                            saveMasterNameToPreferences(masterName);

                            // Загружаем список клиентов
                            loadClientList(masterName);
                        } else {
                            Toast.makeText(this, "Мастер не найден", Toast.LENGTH_SHORT).show();
                            redirectToLogin();
                        }
                    } else {
                        Toast.makeText(this, "Ошибка загрузки данных мастера", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Ошибка загрузки мастера: " + e.getMessage());
                    Toast.makeText(this, "Ошибка подключения к серверу", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadClientList(String masterName) {
        firestore.collection("booking")
                .whereEqualTo("masterName", masterName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        Log.d("Firestore", "Найдено " + task.getResult().size() + " записей");

                        // Очищаем список перед добавлением новых данных
                        clientList.clear();

                        // Обрабатываем документы из Firestore
                        for (DocumentSnapshot document : task.getResult()) {
                            String clientName = document.getString("Name");
                            String bookingTime = document.getString("Time");

                            // Добавляем клиента в список, если данные корректны
                            if (clientName != null && bookingTime != null) {
                                clientList.add(new Client(clientName, bookingTime));
                            } else {
                                Toast.makeText(this, "Некорректные данные клиента", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // Привязываем адаптер, если список не пуст
                        if (!clientList.isEmpty()) {
                            ClientAdapter adapter = new ClientAdapter(ListWithClient.this, R.layout.list_item, clientList);
                            listView.setAdapter(adapter);
                        } else {
                            Toast.makeText(this, "Нет клиентов для отображения", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Список клиентов пуст", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Ошибка загрузки списка клиентов: " + e.getMessage());
                    Toast.makeText(this, "Ошибка подключения к серверу", Toast.LENGTH_SHORT).show();
                });
    }

    private void saveMasterNameToPreferences(String masterName) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MASTER_NAME_KEY, masterName);
        editor.apply();
    }

    private void redirectToLogin() {
        Toast.makeText(this, "Пожалуйста, войдите в систему", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
*/



package com.example.record_tool.Dao;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.ui.adapter.ClientAdapter;
import com.example.record_tool.R;
import com.example.record_tool.model.Client;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListWithClient extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String MASTER_NAME_KEY = "name";
    private ClientAdapter adapter;
    private ProgressBar progressBar;
    private List<Client> clientList;
    private ListView listView;
    private ImageButton imgBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_lists_of_client);

        clientList = new ArrayList<>();
        listView = findViewById(R.id.listViewClient);

        imgBtn = findViewById(R.id.btnDelete);



        progressBar = findViewById(R.id.progressBar);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Проверяем, есть ли сохранённое имя мастера
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String masterName = preferences.getString(MASTER_NAME_KEY, "Default name");

        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        if (masterName == null) {

            Toast.makeText(this, "Мастер табылмады, қайта көріңіз", Toast.LENGTH_SHORT).show();

            // Intent intent = new Intent(this, MasterAuthActivity.class);
            // startActivity(intent);
            finish();
        } else {
            loadClientList(firestore, masterName);

        }

        imgBtn.setOnClickListener(view -> {
            deleteSelectedItems();
        });


    }


    public void loadClientList(FirebaseFirestore firestore, String masterName) {
        firestore.collection("bookings")
                .whereEqualTo("masterName", masterName)
                .get()
                .addOnCompleteListener(task -> {

                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        Log.d("Firestore", "Found " + task.getResult().size() + " documents");


                        clientList.clear();


                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            String time = document.getString("time");


                            if (name != null && time != null) {
                                clientList.add(new Client(name, time));
                            } else {
                                Toast.makeText(this, "дерек дұрыс енгізілмеді", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // Если список клиентов не пуст, создаём адаптер и привязываем его к ListView
                        if (!clientList.isEmpty()) {
                            adapter = new ClientAdapter(ListWithClient.this, R.layout.name_item_of_client_for_master, clientList);

                            listView.setAdapter(adapter);

                            Toast.makeText(ListWithClient.this, "Дерек жазылды", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ListWithClient.this, "Деректер жоқ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("Firestore", "No documents found");
                        Toast.makeText(ListWithClient.this, "Деректер бос", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error loading documents", e);
                    Toast.makeText(ListWithClient.this, "Ошибка загрузки данных: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }



    public void deleteSelectedItems() {
        List<Client> selectedClients = new ArrayList<>();
        for (Client client : clientList) {
            if (client.isSelected()) {
                selectedClients.add(client);
            }
        }

        if (!selectedClients.isEmpty()) {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            for (Client client : selectedClients) {
                firestore.collection("bookings")
                        .whereEqualTo("name", client.getName())
                        .whereEqualTo("time", client.getTime())
                        .get()
                        .addOnSuccessListener(querySnapshot -> {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                document.getReference().delete();
                            }
                            clientList.remove(client);
                            adapter.notifyDataSetChanged();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Өшіруде қате шықты: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }

            Toast.makeText(this, "Таңдалған жазбалар өшірілді", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Жазбаларды таңдаңыз", Toast.LENGTH_SHORT).show();
        }
    }


}


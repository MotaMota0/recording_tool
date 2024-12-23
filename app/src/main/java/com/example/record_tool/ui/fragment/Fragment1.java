package com.example.record_tool.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.record_tool.R;
import com.example.record_tool.model.Master;
import com.example.record_tool.ui.adapter.MasterAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    private RecyclerView recyclerView;
    private MasterAdapter adapter;
    private List<Master> masterList;
    private FirebaseFirestore firestoreBD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Инициализация Firestore
        firestoreBD = FirebaseFirestore.getInstance();
        masterList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        // Инициализация RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Инициализация адаптера и установка его в RecyclerView
        adapter = new MasterAdapter(masterList);
        recyclerView.setAdapter(adapter);

        // Загрузка данных мастеров из Firestore
        loadMastersFromFirestore();

        return view;
    }

    /**
     * Загружает данные мастеров из коллекции Firestore.
     */
    private void loadMastersFromFirestore() {
        firestoreBD.collection("Master")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Очистка списка перед добавлением новых данных
                            masterList.clear();

                            boolean isListEmpty = true; // Флаг для проверки наличия мастеров

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Проверка услуги мастера
                                String service = document.getString("Service");
                                if ("Техника".equals(service)) {
                                    isListEmpty = false;

                                    // Получение данных мастера
                                    String name = document.getString("Name");
                                    String address = document.getString("Adress");

                                    // Добавление мастера в список
                                    masterList.add(new Master(name, address));
                                }
                            }

                            // Обновление адаптера
                            adapter.notifyDataSetChanged();

                            if (isListEmpty) {
                                Toast.makeText(getContext(), "Тізім бос", Toast.LENGTH_SHORT).show();
                                Log.d("Fragment1", "No masters found for service: Техника");
                            }
                        } else {
                            // Обработка ошибок при получении данных
                            Log.e("Fragment1", "Error getting documents: ", task.getException());
                            Toast.makeText(getContext(), "Қате пайда болды", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

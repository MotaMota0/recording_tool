package com.example.record_tool.MasterReg;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.record_tool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MasterListForClient extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MasterAdapter adapter;
    private List<Master> masterList;
    private FirebaseFirestore firestoreBD;

    /* private FirebaseFirestore firestoreBD;
     private ListView listMaster;
     private List<String> masterData;*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        masterList = new ArrayList<>();
        adapter = new MasterAdapter(masterList);
        recyclerView.setAdapter(adapter);

        firestoreBD = FirebaseFirestore.getInstance();
        firestoreBD.collection("Master")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot dc : task.getResult()) {
                                // Выводим данные из документа
                                String name = dc.getString("Name");
                                String address = dc.getString("Adress");
                                masterList.add(new Master(name, address));

                            }

                            adapter.notifyDataSetChanged();

                            /*ArrayAdapter<String> arrayAdaper = new ArrayAdapter<>(
                                    MasterListForClient.this,
                                    android.R.layout.simple_list_item_1);
                            masterList.setAdapter(adaper);*/
                        } else {
                            // Обработка ошибок
                            Toast.makeText(MasterListForClient.this,
                                    "Get failed: " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

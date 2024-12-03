package com.example.record_tool;



import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.record_tool.MasterReg.Master;
import com.example.record_tool.MasterReg.MasterAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainScreenForMaster extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MasterAdapter adapter;
    private List<Master> masterList;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_activity_list);

        // Initialize the list and adapter
        masterList = new ArrayList<>();
        adapter = new MasterAdapter(masterList);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView); // Make sure your RecyclerView is set in the XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();

        // Fetch data from Firestore
        fetchMasterData();
    }

    private void fetchMasterData() {
        String masterName = ""; // You can set a specific name or get it from somewhere else
        firestore.collection("booking")
                .whereEqualTo("masterName", masterName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("Name");
                                String address = document.getString("Adress");
                                masterList.add(new Master(name, address));
                            }
                            adapter.notifyDataSetChanged(); // Notify the adapter that data has changed
                            Toast.makeText(MainScreenForMaster.this, "Data Loaded Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainScreenForMaster.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

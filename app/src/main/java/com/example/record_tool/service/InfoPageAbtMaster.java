package com.example.record_tool.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.Dao.ClientActivity;
import com.example.record_tool.R;
import com.example.record_tool.ui.adapter.MasterAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class InfoPageAbtMaster extends AppCompatActivity {

     public FirebaseFirestore firestore;
    public TextView masterName;
    public TextView infoMaster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inf_about_master);


        firestore = FirebaseFirestore.getInstance();
        String masterNm = MasterAdapter.MasterViewHolder.getMasterName();
        masterName = findViewById(R.id.tVNameMaster);
        infoMaster = findViewById(R.id.tVAboutInfo);


        loadMAsterData(masterNm);

    }
    public void loadMAsterData(String masterNm){

        firestore.collection("Master")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {

                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            if (masterNm.equals(doc.getString("Name"))) {
                                masterName.setText(doc.getString("Name"));
                                infoMaster.setText(doc.getString("Info"));
                                break;
                            } else {
                                Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                })
                .addOnFailureListener(task -> {
                    Toast.makeText(this, "Табылмады", Toast.LENGTH_SHORT).show();
                });
        ImageButton backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MasterListForClient.class);
            startActivity(intent);
        });
        ImageButton accept = findViewById(R.id.recordBtn);
        accept.setOnClickListener(view -> {
            Toast.makeText(this,"Сәтті жазылды",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ClientActivity.class);
            startActivity(intent);
        });
    }
}

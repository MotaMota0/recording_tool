package com.example.record_tool.MasterReg;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.record_tool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MasterActivity extends AppCompatActivity {

    private FirebaseFirestore firestore ;
    private Map<String , Object> listMaster;
    private EditText masterName ;
    private EditText masterAdress ;
    private EditText aboutMaster ;
    private String listBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_master);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        firestore = FirebaseFirestore.getInstance();


        masterName = findViewById(R.id.idNameMaster);
        masterAdress = findViewById(R.id.idAdressMaster);
        aboutMaster = findViewById(R.id.idInfoMaster);
        ListView listView = findViewById(R.id.listView);
        String[] options = {"Техника", "Шаштараз", "Сұлулық салоны", "Клининг"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                options
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            listBooking = options[position];
            Toast.makeText(this, "Selected: " + listBooking, Toast.LENGTH_SHORT).show();
        });
        listBooking =options[0];

    }
    public void onClickEngizu(View view){



        String infoMaster = aboutMaster.getText().toString();

        listMaster = new HashMap<>();
        listMaster.put("Name", masterName.getText().toString());
        listMaster.put("Adress", masterAdress.getText().toString());
        listMaster.put("Info", infoMaster);
        listMaster.put("Service", listBooking);



        firestore.collection("Master")

                .add(listMaster)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(MasterActivity.this,"Satty jazyldy",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MasterActivity.this , "Kate",Toast.LENGTH_SHORT).show();
                    }
                });


    }

}
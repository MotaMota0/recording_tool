package com.example.record_tool.service;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.R;
import com.google.android.gms.common.moduleinstall.internal.ApiFeatureRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.v1.WriteRequest;
import com.google.firestore.v1.WriteResult;

import java.util.Date;
import java.util.Map;

public class EditActivityForClient extends AppCompatActivity {
    private EditText editTextData;
    private EditText editTextTime;
    private TextView textViewOldData;
    private TextView textViewOldTime;
    private Button btnSave;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String userId;
    private String uid = "";
    private String newDate = "";
    private String newTime = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_room);

        editTextData = findViewById(R.id.editTextData);
        editTextTime = findViewById(R.id.editTextTime);
        textViewOldData = findViewById(R.id.tVData);
        textViewOldTime = findViewById(R.id.tVTime);
        btnSave = findViewById(R.id.saveBtn);


        SharedPreferences sharedPreferences = getSharedPreferences("EditPrefs", MODE_PRIVATE);
        String nameCl = sharedPreferences.getString("name", "Default name");
        String dateCl = sharedPreferences.getString("date", "Default date");
        String timeCl = sharedPreferences.getString("time", "Default time");

        textViewOldData.setText(dateCl);
        textViewOldTime.setText(timeCl);


        firestore = FirebaseFirestore.getInstance();
        mAuth  =FirebaseAuth.getInstance();

        firestore.collection("bookings")
                .whereEqualTo("name",nameCl)
                .get()
                .addOnSuccessListener(querySnapshots ->{
                    if(!querySnapshots.isEmpty()){

                        uid = querySnapshots.getDocuments().get(0).getId();
                        Toast.makeText(this,"Күтіңіз",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->{
                    Toast.makeText(this,"Err" + e.getMessage(),Toast.LENGTH_SHORT).show();
                });

        //ID user
        //userId = mAuth.getCurrentUser().getUid();


        btnSave.setOnClickListener(v ->{

            newDate = editTextData.getText().toString().trim();
            newTime = editTextTime.getText().toString().trim();

            if (newDate.isEmpty() || newTime.isEmpty()) {
                Toast.makeText(this, "Орын бос қалдырмаңыз", Toast.LENGTH_SHORT).show();
                return;
            }
            String[] dateParts = newDate.split("-");
            int year = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1; // Месяцы начинаются с 0
            int day = Integer.parseInt(dateParts[2]);

            String[] timeParts = newTime.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            // Собираем новую дату-время в формате Date
            Date selectedDateTime = new Date(year - 1900, month, day, hour, minute);
            Date currentDateTime = new Date();

            if (selectedDateTime.before(currentDateTime)) {
                Toast.makeText(this, "Өткен уақытты таңдай алмайсыз", Toast.LENGTH_SHORT).show();
                return;
            }


            DocumentReference docRef = firestore.collection("bookings").document(uid);

            docRef.set(Map.of("date",newDate,"time",newTime), SetOptions.merge());
            /*docRef.update("date",newDate,"time",newTime)

                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Данные успешно обновлены", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Ошибка обновления данных: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show());
*/
            // Сохранение новых данных в SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("date", newDate);
            editor.putString("time", newTime);
            editor.apply();

            // Сообщение об успешном сохранении
            Toast.makeText(this, "Деректер сәтті жазылды", Toast.LENGTH_SHORT).show();

            // Завершение активности
            finish();
        });




    }
}

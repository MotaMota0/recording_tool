package com.example.record_tool.MasterReg;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.ActivityListWithClient.ListWithClient;
import com.example.record_tool.R;

public class MasterAuthActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "MasterPrefs";
    private static final String MASTER_NAME_KEY = "masterName";

    private EditText masterNameInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity_master);

        masterNameInput = findViewById(R.id.masterNameInput);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String masterName = masterNameInput.getText().toString().trim();
                if (masterName.isEmpty()) {
                    Toast.makeText(MasterAuthActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    // Сохраняем имя мастера в SharedPreferences
                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(MASTER_NAME_KEY, masterName);
                    editor.apply();

                    // Переход на экран списка клиентов
                    Intent intent = new Intent(MasterAuthActivity.this, ListWithClient.class);
                    startActivity(intent);
                    finish(); // Завершаем текущую активность
                }
            }
        });
    }
}


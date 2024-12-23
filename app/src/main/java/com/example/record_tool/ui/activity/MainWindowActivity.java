package com.example.record_tool.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.Dao.StartActivity;
import com.example.record_tool.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainWindowActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ImageView image ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window_activity);

        mAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        String namee = sharedPreferences.getString("name","Default name");
        boolean isTrue = sharedPreferences.getBoolean("isMaster",false);

        /*Intent intent = new Intent(this, ListWithClient.class);
        Intent intent1 = new Intent(this, MasterListForClient.class);
        if ((!namee.equals("Default name"))) {
            if (isTrue) {
                startActivity(intent1);  // Запускаем MasterListForClient
            } else {
                startActivity(intent);   // Запускаем ListWithClient
            }
        }else {
            Toast.makeText(this,"Деректерді енгізіңіз",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,StartActivity.class));
        };*/


        image = findViewById(R.id.imageView2);
        image.setImageResource(R.drawable.booking_ic);


        findViewById(R.id.logoutButton).setOnClickListener(v -> {
            startActivity(new Intent(this, StartActivity.class));
        });
        /*findViewById(R.id.logoutButton).setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(this, StartActivity.class));
            finish();
        });*/
    }
}

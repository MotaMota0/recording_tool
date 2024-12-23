package com.example.record_tool.ui.activity;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.Dao.MasterActivity;
import com.example.record_tool.R;
import com.example.record_tool.service.MasterListForClient;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_choosen);


        Button btClient = findViewById(R.id.idBtnClient);
        btClient.setOnClickListener(view ->{
                Intent   intent = new Intent(this, MasterListForClient.class);
                startActivity(intent);
        });
        Button btMaster = findViewById(R.id.idBtnMaster);
        btMaster.setOnClickListener(view ->{
            Intent   intent = new Intent(this, MasterActivity.class);
            startActivity(intent);
        });




    }

}

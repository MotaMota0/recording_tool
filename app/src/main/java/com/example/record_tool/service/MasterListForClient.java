package com.example.record_tool.service;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;


import com.example.record_tool.Dao.ClientActivity;
import com.example.record_tool.R;
import com.example.record_tool.model.Client;
import com.example.record_tool.ui.fragment.*;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class MasterListForClient extends AppCompatActivity {
   /* private RecyclerView recyclerView;
    private MasterAdapter adapter;
    private List<Master> masterList;
    private FirebaseFirestore firestoreBD;
    private FirebaseFirestore firestoreBD;
     private ListView listMaster;
     private List<String> masterData;*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        /*WorkRequest checkData = new PeriodicWorkRequest.Builder(CheckDataWorker.class,
                12, TimeUnit.HOURS)
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueue(checkData);*/
        /*recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
*/

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        // Обрабатываем выбор пунктов меню
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem menuItem) {

                Fragment selectedFragment = null;




                if (savedInstanceState == null) {
                    selectedFragment = new Fragment1();
                }
                int itemId = menuItem.getItemId();

                if (itemId == R.id.menu_shashtaraz) {

                    selectedFragment = new Fragment2();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, selectedFragment);
                    ft.commit();

                } else if (itemId == R.id.menu_salon) {
                    selectedFragment = new Fragment3();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, selectedFragment);
                    ft.commit();

                } else if (itemId == R.id.menu_technica) {
                    selectedFragment = new Fragment1();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, selectedFragment);
                    ft.commit();

                } else if (itemId == R.id.menu_clining) {
                    selectedFragment = new Fragment4();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container, selectedFragment);
                    ft.commit();
                }

                return true;
            }
        });
        /*masterList = new ArrayList<>();
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

                            *//*ArrayAdapter<String> arrayAdaper = new ArrayAdapter<>(
                                    MasterListForClient.this,
                                    android.R.layout.simple_list_item_1);
                            masterList.setAdapter(adaper);*//*
                        } else {
                            // Обработка ошибок
                            Toast.makeText(MasterListForClient.this,
                                    "Get failed: " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
*/
    }
}

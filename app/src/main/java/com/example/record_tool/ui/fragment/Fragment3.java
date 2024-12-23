package com.example.record_tool.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


public class Fragment3 extends Fragment {

    private MasterAdapter adapter;
    private List<Master> masterList;
    private FirebaseFirestore firestoreBD;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestoreBD = FirebaseFirestore.getInstance();
        masterList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        // Найдите RecyclerView внутри этой View
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Установите LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        /*SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ServicePref", Context.MODE_PRIVATE);
        String services = sharedPreferences.getString("service","Default service");
        */
        adapter = new MasterAdapter(masterList);
        recyclerView.setAdapter(adapter);
        loadMastersFromFirestore();

        return view;
    }
    private void loadMastersFromFirestore() {

        firestoreBD = FirebaseFirestore.getInstance();
        firestoreBD.collection("Master")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() ) {
                            masterList.clear();

                            boolean isListEmpty = true;
                            for (QueryDocumentSnapshot dc : task.getResult()) {
                                String service = dc.getString("Service");
                                if (service.equals("Сұлулық салоны")){
                                    isListEmpty = false;
                                    // Выводим данные из документа
                                    String name = dc.getString("Name");
                                    String address = dc.getString("Adress");
                                    masterList.add(new Master(name, address));
                                }
                            }

                            adapter.notifyDataSetChanged();

                            if(isListEmpty){
                                Toast.makeText(getContext(),"Тізім бос",Toast.LENGTH_SHORT).show();
                            }
                            /*ArrayAdapter<String> arrayAdaper = new ArrayAdapter<>(
                                    MasterListForClient.this,
                                    android.R.layout.simple_list_item_1);
                            masterList.setAdapter(adaper);*/
                        } else {
                            // Обработка ошибок
                            Log.e("this","Get failed");
                        }
                    }
                });
    }
}
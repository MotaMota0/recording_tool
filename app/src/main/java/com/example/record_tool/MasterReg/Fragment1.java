package com.example.record_tool.MasterReg;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.record_tool.R;


public class Fragment1 extends Fragment {

    private RecyclerView recyclerView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        // Найдите RecyclerView внутри этой View
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Установите LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;

    }
}
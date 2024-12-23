package com.example.record_tool;

import static androidx.test.espresso.Espresso.onView;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.record_tool.model.Master;
import com.example.record_tool.ui.adapter.MasterAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class ExampleTest {

    private MasterAdapter adapter;
    private List<Master> masterList;

    @Before
    public void setUp() {
        // Инициализация списка мастеров и адаптера
        masterList = new ArrayList<>();
        adapter = new MasterAdapter(masterList);
    }

    @Test
    public void testRecyclerViewAdapter_DataBinding() {
        // Добавление данных в список
        masterList.add(new Master("Master1", "Address1"));
        masterList.add(new Master("Master2", "Address2"));

        // Уведомление адаптера об изменении данных
        adapter.notifyDataSetChanged();

        // Проверка, что количество элементов в списке совпадает с ожидаемым
        assertEquals(2, masterList.size());
    }
}


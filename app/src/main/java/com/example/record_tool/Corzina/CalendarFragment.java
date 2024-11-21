package com.example.record_tool.Corzina;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.record_tool.R;

import java.util.HashMap;
import java.util.Map;

public class CalendarFragment extends Fragment {

    private Map<String, String> notes = new HashMap<>();
    private EditText records;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Раздуваем разметку для календарного фрагмента
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Инициализация элементов
        records = view.findViewById(R.id.idRecords); // Правильная инициализация
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        EditText notesEditText = view.findViewById(R.id.notesEditText);
        Button saveNoteButton = view.findViewById(R.id.saveNoteButton);

        // Обработка выбора даты
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;

            // Отображаем заметку, если она существует
            if (notes.containsKey(selectedDate)) {
                notesEditText.setText(notes.get(selectedDate));
                records.setText(notes.get(selectedDate)); // Заполняем поле records
            } else {
                notesEditText.setText("");
                records.setText(""); // Очищаем поле records
            }
        });

        // Обработка сохранения заметки
        saveNoteButton.setOnClickListener(v -> {
            // Получаем текущую дату в формате day/month/year
            String selectedDate = calendarView.getDate() + "";
            String note = notesEditText.getText().toString();

            // Сохраняем заметку
            notes.put(selectedDate, note);
            Toast.makeText(getContext(), "Note saved for " + selectedDate, Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}

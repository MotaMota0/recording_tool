package com.example.record_tool.Corzina;



import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.R;

public class ClientActivityyy extends AppCompatActivity {

    private Spinner spinnerServices;
    private CalendarView calendarView;
    private TimePicker timePicker;
    private Button btnConfirmBooking;
    private String selectedService;
    private String selectedDate;
    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_booking); //

        // Инициализация элементов интерфейса
        spinnerServices = findViewById(R.id.spinnerServices);
        calendarView = findViewById(R.id.calendarView);
        timePicker = findViewById(R.id.timePicker);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);

        // Обработка выбора даты
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
        });

        // Установка времени в формате HH:mm
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            selectedTime = String.format("%02d:%02d", hourOfDay, minute);
        });

        // Кнопка подтверждения бронирования
        btnConfirmBooking.setOnClickListener(v -> {
            // Получение выбранной услуги
            selectedService = spinnerServices.getSelectedItem() != null ? spinnerServices.getSelectedItem().toString() : "No Service Selected";

            if (selectedService == null || selectedDate == null || selectedTime == null) {
                Toast.makeText(ClientActivityyy.this, "Please select all booking details.", Toast.LENGTH_SHORT).show();
            } else {
                String confirmationMessage = "Service: " + selectedService + "\nDate: " + selectedDate + "\nTime: " + selectedTime;
                Toast.makeText(ClientActivityyy.this, "Booking Confirmed!\n" + confirmationMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}

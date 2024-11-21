package com.example.record_tool;



import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.record_tool.Booking.Booking;
import com.example.record_tool.Corzina.FragmentClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ClientActivity extends AppCompatActivity {

    private Button buttonConfirmBooking;
    private TimePicker timePicker;
    private Spinner spinnerServices;
    private CalendarView calendarView;
    private TextView tvBookingTitle;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        // Инициализация UI элементов
        buttonConfirmBooking = findViewById(R.id.btnConfirmBooking);
        timePicker = findViewById(R.id.timePicker);
        spinnerServices = findViewById(R.id.spinnerServices);
        calendarView = findViewById(R.id.calendarView);
        tvBookingTitle = findViewById(R.id.idSelectTime);
        editText = findViewById(R.id.editTextText);


        String[] services = {"Haircut", "Nail Service", "Massage", "Facial"};


        // Создаем адаптер и устанавливаем его для Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                services
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(adapter);

        // Устанавливаем обработчик для BottomNavigationView
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Устанавливаем начальный фрагмент при запуске
        /*if (savedInstanceState == null) {
            replaceFragment(new FragmentClient());
        }*/

        // Обрабатываем выбор пунктов меню
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                int itemId = menuItem.getItemId();
                if (itemId == R.id.menu_shashtaraz) {
                    selectedFragment = new FragmentClient();
                } else if (itemId == R.id.menu_salon) {
                    selectedFragment = new FragmentClient(); // Замените на другой фрагмент при необходимости
                } else if (itemId == R.id.menu_technica) {
                    selectedFragment = new FragmentClient(); // Замените на другой фрагмент при необходимости
                } else if (itemId == R.id.menu_clining) {
                    selectedFragment = new FragmentClient(); // Замените на другой фрагмент при необходимости
                }

                /*if (selectedFragment != null) {
                    replaceFragment(selectedFragment);
                }*/
                return true;
            }
        });

        // Обработчик нажатия на кнопку подтверждения
        buttonConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDataToFirebase();
            }
        });
    }

    // Метод для добавления данных в Firebase
    public void SetDataToFirebase() {
        // Получаем выбранную услугу из спиннера
        String selectedService = spinnerServices.getSelectedItem().toString();

        // Получаем отформатированную дату с календаря
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = sdf.format(calendarView.getDate());

        // Получаем время из timePicker
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String time = String.format(Locale.getDefault(), "%02d:%02d:00", hour, minute);

        // Получаем заголовок из TextView (если нужно)
        String title = tvBookingTitle.getText().toString();
        String name = editText.getText().toString();
        // Создаем объект бронирования
        Booking booking = new Booking(selectedService, date, time, title,name);

        // Получаем экземпляр Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Добавляем данные в Firestore
        db.collection("bookings")
                .add(booking)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "Booking saved! Document ID: " + documentReference.getId());
                        Toast.makeText(ClientActivity.this, "Booking saved!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding booking", e);
                        Toast.makeText(ClientActivity.this, "Error saving booking", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Метод для замены фрагмента
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentLayout, fragment);
        transaction.commit();
    }
}




































    /*private void openBookingFragment() {
        Fragment bookingFragment = new FragmentBooking(); // Ваш фрагмент с формой бронирования

        // Используем FragmentTransaction для замены фрагмента
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameConteiner, bookingFragment);
        transaction.addToBackStack(null); // Добавляем в стек для возврата
        transaction.commit();
    }*/

   /* public void saveDataTime(){
        String timeWasSet = textView.getText().toString();
        map = new HashMap<>();
        map.put("Time", timeWasSet);
        firestore.collection("TimeWasSet")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ClientActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ClientActivity.this, "Data dont save", Toast.LENGTH_SHORT).show();
                    }
                });
*/


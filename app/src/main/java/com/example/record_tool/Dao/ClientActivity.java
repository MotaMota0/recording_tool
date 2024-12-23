package com.example.record_tool.Dao;



import static androidx.core.app.NotificationCompat.PRIORITY_LOW;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.record_tool.service.EditActivityForClient;
import com.example.record_tool.R;

import com.example.record_tool.ui.adapter.*;
import com.example.record_tool.service.MasterListForClient;
import com.google.firebase.Timestamp;

import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ClientActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID= 1;
    private static final String CHANEL_ID="CHANEL_ID";
    private Button buttonConfirmBooking;
    private TimePicker timePicker;

    private CalendarView calendarView;
    private TextView tvBookingTitle;
    private TextView textView6;
    private EditText editText;
    private String selectedTitle;
    private Timestamp timestampUser;
    private String dataTime = "" ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        // Инициализация UI элементов
        buttonConfirmBooking = findViewById(R.id.btnConfirmBooking);
        timePicker = findViewById(R.id.timePicker);

        calendarView = findViewById(R.id.calendarView);
        tvBookingTitle = findViewById(R.id.idSelectTime);
        editText = findViewById(R.id.editTextText);
        textView6 = findViewById(R.id.textView6);

        /*Intent intent = new Intent(this,EstimatorClientActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0 , intent,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationChannel notificationChannel = new NotificationChannel(
                "TEST_CHANNEL","TEST_DESCRIPTION",
                NotificationManager.IMPORTANCE_HIGH);


        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);

        Notification notification = new NotificationCompat.Builder(this,"TEST_CHANNEL")
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Мастерді бағалау")
                .setContentText("1 - 5 Аралығында Баға беріңіз")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(45,notification);*/


        selectedTitle = "";

        //самообработка кнопки при вызове увидомление

        // Обработчик нажатия на кнопку подтверждения
        buttonConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDate = textView6.getText().toString();
                int selectedHour = timePicker.getHour();
                int selectedMinute = timePicker.getMinute();

                long currentTimeMillis = System.currentTimeMillis();
                Date currentDate = new Date(currentTimeMillis);


                String[] parts = selectedDate.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]) - 1; // Месяцы начинаются с 0
                int day = Integer.parseInt(parts[2]);

                Date selectedDateTime = new Date(year - 1900, month, day, selectedHour, selectedMinute);
                if (selectedDateTime.before(currentDate)) {
                    Toast.makeText(ClientActivity.this, "Басқа уақыт таңдаңыз", Toast.LENGTH_SHORT).show();
                    return;
                }
            //это отдельный метод для сохранения даннных
                SetDataToFirebase();

                SharedPreferences sharedPreferences = getSharedPreferences("EditPrefs",MODE_PRIVATE);
                dataTime = sharedPreferences.getString("time","Default time");

            }
        });



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Делаем форматирование даты в нужном формате
                String selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                // Обновляем дату в TextView (или можете записать в переменную)
                textView6.setText(selectedDate);
            }
        });
    }


    // Метод для добавления данных в Firebase
    public void SetDataToFirebase() {
            // Получаем дату и время бронирования
            String date = textView6.getText().toString();
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String time = String.format(Locale.getDefault(), "%02d:%02d:00", hour, minute);

            // Получаем имя клиента и выбранный пункт меню
            String name = editText.getText().toString();
            String slctTitle = selectedTitle;

            // Получение имени мастера из адаптера
            String masterName = MasterAdapter.MasterViewHolder.getMasterName().toString();
            timestampUser = new Timestamp(new Date());

            // Текущее серверное время (timestamp) для хранения времени создания записи
            Map<String, Object> bookingData = new HashMap<>();
            bookingData.put("title", slctTitle);
            bookingData.put("date", date);
            bookingData.put("time", time);
            bookingData.put("name", name);
            bookingData.put("masterName", masterName);
            bookingData.put("timestamp",timestampUser); // Серверное время

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Проверка на занятое время
            db.collection("bookings")
                    .whereEqualTo("date", date)
                    .whereEqualTo("time", time)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Если запись с таким временем уже существует
                            Toast.makeText(ClientActivity.this, "Уақыт таңдалған, басқа уақытты таңдаңыз", Toast.LENGTH_SHORT).show();
                        } else {
                            // Добавляем данные в коллекцию
                            db.collection("bookings")
                                    .add(bookingData)
                                    .addOnSuccessListener(documentReference -> {
                                        SharedPreferences sharedPreferences = getSharedPreferences("EditPrefs",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("name",name);
                                        editor.putString("date", date);
                                        editor.putString("time",time);

                                        editor.putString("timestamp", String.valueOf(timestampUser.toDate().getTime()));
                                        editor.apply();
                                        Log.d("TAG", "Booking saved! Document ID: " + documentReference.getId());
                                        Toast.makeText(ClientActivity.this, "Запись успешно сохранена!", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.w("TAG", "Error adding booking", e);
                                        Toast.makeText(ClientActivity.this, "Ошибка при сохранении записи", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.w("TAG", "Error checking booking time", e);
                        Toast.makeText(ClientActivity.this, "Ошибка при проверке времени", Toast.LENGTH_SHORT).show();
                    });
        }


    public void exitONBack(View view ){
        Intent intent = new Intent(this, MasterListForClient.class);
        startActivity(intent);
    }

    public void settingONBack(View view ){
        Intent intent = new Intent(this, EditActivityForClient.class);
        startActivity(intent);
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


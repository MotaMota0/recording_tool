package com.example.record_tool.Booking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class RecordData extends AppCompatActivity {

    Button btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
    TimePicker timePicker = findViewById(R.id.timePicker);
    Spinner spinnerServices = findViewById(R.id.spinnerServices);
    CalendarView calendarView = findViewById(R.id.calendarView);
    TextView tvBookingTitle = findViewById(R.id.idSelectTime);
    EditText editText  = findViewById(R.id.editTextText);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected service from the spinner
                String selectedService = spinnerServices.getSelectedItem().toString();

                // Get formatted date from calendar
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String date = sdf.format(calendarView.getDate());

                // Get formatted time from time picker
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

                String time = String.format(Locale.getDefault(), "%02d:%02d:00", hour, minute);
                String name = editText.getText().toString();
                // Optional: Get title from TextView (if needed)
                String title = tvBookingTitle.getText().toString();

                // Create a Booking object
                Booking booking = new Booking(selectedService, date, time, title,name);

                // Get Firebase Firestore instance
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Add data to Firestore
                db.collection("bookings")
                        .add(booking)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "Booking saved! Document ID: " + documentReference.getId());
                                // Handle success (e.g., show a confirmation message)
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error adding booking", e);
                                // Handle error (e.g., show an error message)
                            }
                        });
            }
        });
    }
}

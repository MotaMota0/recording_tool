package com.example.record_tool.Booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.record_tool.R;
import com.example.record_tool.SharedViewModel;

public class FragmentBooking extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Инфлейт макет
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        // Получение данных из аргументов
        String serviceType = "Unknown Service";  // Значение по умолчанию
        if (getArguments() != null) {
            serviceType = getArguments().getString("serviceType", "Unknown Service");
        }
        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.setSelectedId(R.id.btnConfirmBooking);


        // Устанавливаем заголовок
        TextView titleView = view.findViewById(R.id.tvBookingTitle);
        titleView.setText(serviceType);

        return view;
    }
}


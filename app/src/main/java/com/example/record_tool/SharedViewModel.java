package com.example.record_tool;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> selectedId = new MutableLiveData<>();

    public void setSelectedId(Object o) {
        selectedId.setValue(o.toString());
    }

    public LiveData<String> getSelectedId() {
        return selectedId;
    }
}


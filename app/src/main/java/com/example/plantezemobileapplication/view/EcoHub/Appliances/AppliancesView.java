package com.example.plantezemobileapplication.view;

import com.example.plantezemobileapplication.model.Appliance;

import java.util.List;

public interface AppliancesView {
    void showAppliances(List<Appliance> appliances);
    void showError(String message);
}


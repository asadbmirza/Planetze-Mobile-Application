package com.example.plantezemobileapplication.view.EcoHub.Appliances;

import com.example.plantezemobileapplication.model.ApplianceModel;

import java.util.List;

public interface AppliancesView {
    void showAppliances(List<ApplianceModel> appliances);
    void showError(String message);
}


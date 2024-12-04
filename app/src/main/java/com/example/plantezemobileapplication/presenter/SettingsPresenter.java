package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.SettingsModel;
import com.example.plantezemobileapplication.view.settings.SettingsView;

import java.util.Objects;

public class SettingsPresenter {
    SettingsView view;
    SettingsModel model;

    public SettingsPresenter(SettingsView view, SettingsModel model) {
        this.view = view;
        this.model = model;
    }

    public void updateUserData(String name, String country) {
        if (Objects.equals(name, "") || Objects.equals(country, "")) {
            view.showProcessFailure("Cannot leave fields empty");
            return;
        }

        model.saveUserFullName(name);
        model.saveUserCountry(country);
    }
}

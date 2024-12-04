package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.model.SettingsModel;
import com.example.plantezemobileapplication.view.settings.SettingsView;

public class SettingsPresenter {
    SettingsView view;
    SettingsModel model;

    public SettingsPresenter(SettingsView view, SettingsModel model) {
        this.view = view;
        this.model = model;
    }
}

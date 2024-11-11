package com.example.plantezemobileapplication.view;

import android.widget.ProgressBar;

public interface ProcessView {
    void showProcessSuccess();
    void showProcessFailure();
    void showLoading();
    void hideLoading();
}

package com.example.plantezemobileapplication.presenter;

import com.example.plantezemobileapplication.utils.ActivityLog;

import java.util.List;

public interface EcoMonitorCallback {
    void onDefaultVehicleFetched(int defaultVehicleIndex);
    void onUserEnergyFetched(int energyTypeIndex);
    void onEmissionFetched(String category, double emission);
    void onActivitiesFetched(List<ActivityLog> activityLogList);
    void onFetchError(String errorMessage);
}

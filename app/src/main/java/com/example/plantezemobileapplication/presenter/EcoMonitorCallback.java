package com.example.plantezemobileapplication.presenter;

public interface EcoMonitorCallback {
    void onDefaultVehicleFetched(int defaultVehicleIndex);
    void onUserEnergyFetched(int energyTypeIndex);
    void onEmissionFetched(String category, double emission);
    void onFetchError(String errorMessage);
}

package com.example.plantezemobileapplication.model;

import java.util.List;

public class ApplianceResponseModel {
    private Data data;
    public Data getData() {
        return data;
    }
    public static class Data {
        private List<ApplianceModel> products;

        public List<ApplianceModel> getAppliances() {
            return products;
        }

    }
}

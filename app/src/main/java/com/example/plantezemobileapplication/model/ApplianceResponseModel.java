package com.example.plantezemobileapplication.model;

import java.util.List;

public class ApplianceResponse {
    private Data data;
    public Data getData() {
        return data;
    }
    public static class Data {
        private List<Appliance> products;

        public List<Appliance> getAppliances() {
            return products;
        }

    }
}

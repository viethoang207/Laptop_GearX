package com.example.laptop_gearx;

public class APIService {
    private static String base_url="https://gearx.conveyor.cloud/";
    public static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
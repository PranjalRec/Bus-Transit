package com.pranjal.bustransit;

public class CitiesModel {
    int image;
    String cityName;

    public CitiesModel(int image,String cityName) {
        this.image = image;
        this.cityName = cityName;
    }

    public int getImage() {
        return image;
    }

    public String getCityName() {
        return cityName;
    }
}

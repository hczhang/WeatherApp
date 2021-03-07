package com.example.weatherapp.api.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/** The Location object inside Weather */
public class Location {

    @SerializedName("name")
    private String name;

    @SerializedName("region")
    private String region;

    @SerializedName("country")
    private String country;

    @SerializedName("localtime")
    private String localTime;

    public String getName() {
        return name;
    }

    //TODO This is for mock data manipulation only.
    // Not necessary for network data.
    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    /**
     * Original format: 2021-01-23 23:59
     * Output format: 23:59
     */
    public String getLocalTime() {
        return localTime == null || localTime.length() < 11 ? "" : localTime.substring(11);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location location = (Location) obj;
        return Objects.equals(name, location.name) && Objects.equals(region, location.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, region);
    }
}

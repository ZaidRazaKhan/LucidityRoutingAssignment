package com.lucidity.route.plan.v1;

import com.lucidity.route.plan.Location;

public class GeoLocation implements Location {
    private final Double latitude;
    private final Double longitude;

    @Override
    public String toString() {
        return "GeoLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public GeoLocation(final Double latitude, final Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}

package com.lucidity.route.plan.v1;

import com.lucidity.route.plan.DistanceCalculator;

public class HarvesineDistanceCalculator implements DistanceCalculator<GeoLocation> {
    private static final int R = 6371;

    @Override
    public Double calculate(GeoLocation from, GeoLocation to) {
        return getHarvesianDistance(from, to);
    }

    private Double getHarvesianDistance(final GeoLocation from, final GeoLocation to) {
        Double lat2 = to.getLatitude();
        Double lat1 = from.getLatitude();
        Double lon1 = from.getLongitude();
        Double lon2 = to.getLongitude();
        double latDistance = toRad(lat2-lat1);
        double lonDistance = toRad(lon2-lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    private Double toRad(final Double value) {
        return value * Math.PI / 180;
    }
}

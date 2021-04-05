package com.lucidity.route.plan.v1;

import com.lucidity.route.plan.DistanceCalculator;
import com.lucidity.route.plan.TimeCalculator;

public class TimeCalculatorImpl implements TimeCalculator<GeoLocation> {

    private final double averageSpeed;
    private final DistanceCalculator<GeoLocation> harvesineDistanceCalculator;

    public TimeCalculatorImpl(final double averageSpeed,
                              final DistanceCalculator<GeoLocation> harvesineDistanceCalculator) {
        this.averageSpeed = averageSpeed;
        this.harvesineDistanceCalculator = harvesineDistanceCalculator;
    }


    @Override
    public Double estimateTime(GeoLocation from, GeoLocation to) {
        return harvesineDistanceCalculator.calculate(from, to)/averageSpeed;
    }


}

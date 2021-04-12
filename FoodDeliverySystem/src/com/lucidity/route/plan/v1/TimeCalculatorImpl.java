package com.lucidity.route.plan.v1;

import com.lucidity.route.plan.DistanceCalculator;
import com.lucidity.route.plan.TimeCalculator;
import org.jetbrains.annotations.NotNull;

public class TimeCalculatorImpl implements TimeCalculator<GeoLocation> {

    private final double averageSpeed;
    private final DistanceCalculator<GeoLocation> harvesineDistanceCalculator;

    public TimeCalculatorImpl(final double averageSpeed,
                              final DistanceCalculator<GeoLocation> harvesineDistanceCalculator) {
        this.averageSpeed = averageSpeed;
        this.harvesineDistanceCalculator = harvesineDistanceCalculator;
    }


    @Override
    @NotNull
    public Double estimateTime(final GeoLocation from, final GeoLocation to) {
        return harvesineDistanceCalculator.calculate(from, to)/averageSpeed;
    }


}

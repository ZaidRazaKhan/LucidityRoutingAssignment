package com.lucidity.route.plan;

public interface DistanceCalculator<T extends Location>{

    Double calculate(T from, T to);
}

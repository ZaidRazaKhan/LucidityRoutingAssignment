package com.lucidity.route.plan;

public interface TimeCalculator<T extends Location> {
    Double estimateTime(T from, T to);
}

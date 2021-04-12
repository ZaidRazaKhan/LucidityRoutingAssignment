package com.lucidity.route.plan;

import org.jetbrains.annotations.NotNull;

public interface DistanceCalculator<T extends Location>{
    @NotNull
    Double calculate(final T from, final T to);
}

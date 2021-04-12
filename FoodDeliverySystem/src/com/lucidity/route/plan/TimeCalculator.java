package com.lucidity.route.plan;

import org.jetbrains.annotations.NotNull;

public interface TimeCalculator<T extends Location> {
    @NotNull
    Double estimateTime(final T from, final T to);
}

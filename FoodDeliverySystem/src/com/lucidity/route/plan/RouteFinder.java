package com.lucidity.route.plan;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface RouteFinder<T extends Location, U extends Order, V extends RouteNode> {
    @NotNull
    List<V> find(final List<T> startLocation, final List<U> orders);
}

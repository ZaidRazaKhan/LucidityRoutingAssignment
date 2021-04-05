package com.lucidity.route.plan.v1;

import com.lucidity.route.plan.RouteNode;

import java.util.Set;

public class RouteNodeImpl implements RouteNode {
    private final GeoLocation location;
    private final Set<String> idsOfOrdersPicked;
    private final Set<String> idsOfOrdersDropped;

    @Override
    public String toString() {
        return "RouteNodeImpl{" +
                "location=" + location +
                ", idsOfOrdersPicked=" + idsOfOrdersPicked +
                ", idsOfOrdersDropped=" + idsOfOrdersDropped +
                "} \n";
    }

    public RouteNodeImpl(final GeoLocation location, final Set<String> idsOfOrdersPicked,
                         final Set<String> idsOfOrdersDropped) {

        this.location = location;
        this.idsOfOrdersPicked = idsOfOrdersPicked;
        this.idsOfOrdersDropped = idsOfOrdersDropped;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public Set<String> getIdsOfOrdersPicked() {
        return idsOfOrdersPicked;
    }

    public Set<String> getIdsOfOrdersDropped() {
        return idsOfOrdersDropped;
    }
}

package com.lucidity.route.plan.v1;

import com.lucidity.route.plan.Location;
import com.lucidity.route.plan.Order;

public class OrderImpl<T extends Location>  extends Order {

    private final T pickupLocation;
    private final T dropLocation;

    public OrderImpl(final T pickupLocation, final T dropLocation, final String id) {
        super(id);
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
    }

    public T getDropLocation() {
        return dropLocation;
    }

    public T getPickupLocation() {
        return pickupLocation;
    }
}

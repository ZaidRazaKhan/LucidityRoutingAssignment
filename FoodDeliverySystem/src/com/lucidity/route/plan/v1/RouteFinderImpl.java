package com.lucidity.route.plan.v1;

import com.lucidity.route.plan.RouteFinder;
import com.lucidity.route.plan.TimeCalculator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;


 public class RouteFinderImpl implements RouteFinder<GeoLocation, OrderImpl<GeoLocation>, RouteNodeImpl> {

    private final TimeCalculator<GeoLocation> timeCalculator;

    public RouteFinderImpl(final TimeCalculator<GeoLocation> timeCalculator) {
        this.timeCalculator = timeCalculator;
    }

    @NotNull
    private List<RouteNodeImpl> getOptimalRoute(final OrderImpl<GeoLocation> order1,
                                                final OrderImpl<GeoLocation> order2,
                                                final GeoLocation deliveryBoyLocation) {

        final List<List<RouteNodeImpl>> routes = getValidRoutes(order1, order2, deliveryBoyLocation);
        List<RouteNodeImpl> optimalRoute = new ArrayList<>();

        double minTime = Double.MAX_VALUE;
        for(List<RouteNodeImpl> route: routes){
            double timeElapsed = routeTime(route);
            if(timeElapsed < minTime){
                minTime = timeElapsed;
                optimalRoute = route;
            }
        }

        return optimalRoute;
    }

    @NotNull
     private List<List<RouteNodeImpl>> getValidRoutes(final OrderImpl<GeoLocation> order1,
                                                      final OrderImpl<GeoLocation> order2,
                                                      final GeoLocation deliveryBoyLocation) {
         final List<List<RouteNodeImpl>> routes = new ArrayList<>();

         //Case 0: R1, R2, C1, C2
         routeHavingRestaurantFirstCustomerLaterInOrder(order2, order1, deliveryBoyLocation, routes);

         //Case 1: R1, R2, C2, C1
         routeHavingRestaurantFirstCustomerLaterOppositeOrder(order2, order1, deliveryBoyLocation, routes);

         //Case 2: R2, R1, C1, C2
         routeHavingRestaurantFirstCustomerLaterOppositeOrder(order1, order2, deliveryBoyLocation, routes);

         //Case 3: R2, R1, C2, C1
         routeHavingRestaurantFirstCustomerLaterInOrder(order1, order2, deliveryBoyLocation, routes);

         //Case 4: R1, C1, R2, C2
         routeHavingCustomerRightAfterCorrespondingRestaurant(order2, order1, deliveryBoyLocation, routes);

         //Case 5: R2, C2, R1, C1
         routeHavingCustomerRightAfterCorrespondingRestaurant(order1, order2, deliveryBoyLocation, routes);

         return routes;
     }

     private void routeHavingRestaurantFirstCustomerLaterInOrder(final OrderImpl<GeoLocation> order2,
                                                                 final OrderImpl<GeoLocation> order1,
                                                                 final GeoLocation deliveryBoyLocation,
                                                                 final List<List<RouteNodeImpl>> routes) {
         restaurantFirstCustomerLast(order2, order1, deliveryBoyLocation, routes, order1.getDropLocation(),
                 order1.getId(), order2.getDropLocation(), order2.getId());
     }
    private void restaurantFirstCustomerLast(final OrderImpl<GeoLocation> order2, final OrderImpl<GeoLocation> order1,
                                              final GeoLocation deliveryBoyLocation, final List<List<RouteNodeImpl>> routes,
                                              final GeoLocation dropLocation, final String id,
                                              final GeoLocation dropLocation2, final String id2) {
         final List<RouteNodeImpl> route = new ArrayList<>();
         route.add(new RouteNodeImpl(deliveryBoyLocation, new HashSet<>(), new HashSet<>()));
         route.add(new RouteNodeImpl(order1.getPickupLocation(),
                 new HashSet<>(Collections.singletonList(order1.getId())), new HashSet<>()));
         route.add(new RouteNodeImpl(order2.getPickupLocation(),
                 new HashSet<>(Collections.singletonList(order2.getId())), new HashSet<>()));
         route.add(new RouteNodeImpl(dropLocation,
                 new HashSet<>(), new HashSet<>(Collections.singletonList(id))));
         route.add(new RouteNodeImpl(dropLocation2,
                 new HashSet<>(), new HashSet<>(Collections.singletonList(id2))));
         routes.add(route);
     }
    private void routeHavingRestaurantFirstCustomerLaterOppositeOrder(final OrderImpl<GeoLocation> order1,
                                                                       final OrderImpl<GeoLocation> order2,
                                                                       final GeoLocation deliveryBoyLocation,
                                                                       final List<List<RouteNodeImpl>> routes) {
         restaurantFirstCustomerLast(order1, order2, deliveryBoyLocation, routes, order1.getDropLocation(),
                 order1.getId(), order2.getDropLocation(), order2.getId());
     }

     private void routeHavingCustomerRightAfterCorrespondingRestaurant(final OrderImpl<GeoLocation> order1,
                                                                       final OrderImpl<GeoLocation> order2,
                                                                       final GeoLocation deliveryBoyLocation,
                                                                       final List<List<RouteNodeImpl>> routes) {
         final List<RouteNodeImpl> route;
         route = new ArrayList<>();
         route.add(new RouteNodeImpl(deliveryBoyLocation, new HashSet<>(), new HashSet<>()));
         route.add(new RouteNodeImpl(order2.getPickupLocation(),
                 new HashSet<>(Collections.singletonList(order2.getId())), new HashSet<>()));
         route.add(new RouteNodeImpl(order2.getDropLocation(),
                 new HashSet<>(), new HashSet<>(Collections.singletonList(order2.getId()))));
         route.add(new RouteNodeImpl(order1.getPickupLocation(),
                 new HashSet<>(Collections.singletonList(order1.getId())), new HashSet<>()));
         route.add(new RouteNodeImpl(order1.getDropLocation(),
                 new HashSet<>(), new HashSet<>(Collections.singletonList(order1.getId()))));
         routes.add(route);
     }

    private Double routeTime(final List<RouteNodeImpl> routeNodeImpls){
        Double time = 0.0;
        for(int i = 0; i < routeNodeImpls.size()-1; i++){
            time += timeCalculator.estimateTime(routeNodeImpls.get(i).getLocation(),
                    routeNodeImpls.get(i+1).getLocation());
        }
        return time;

    }
    @Override
    @NotNull
    public List<RouteNodeImpl> find(final List<GeoLocation> startLocation, final List<OrderImpl<GeoLocation>> orders) {
        if(Objects.isNull(startLocation) || startLocation.size() != 1)
            throw new IllegalArgumentException("This implementation requires only one starting location");
        if(Objects.isNull(orders) || orders.size() != 2)
            throw new IllegalArgumentException("This implementation requires only 2 orders");
        OrderImpl<GeoLocation> order1 = orders.get(0);
        OrderImpl<GeoLocation> order2 = orders.get(1);
        GeoLocation deliveryBoyLocation = startLocation.get(0);
        return getOptimalRoute(order1, order2, deliveryBoyLocation);
    }
}

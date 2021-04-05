import com.lucidity.route.plan.DistanceCalculator;
import com.lucidity.route.plan.RouteFinder;
import com.lucidity.route.plan.TimeCalculator;
import com.lucidity.route.plan.v1.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        List<OrderImpl<GeoLocation>> orders = fetchAllOrders();

        List<GeoLocation> startLocations = new ArrayList<>();
        startLocations.add(new GeoLocation(0.0,0.0));

        DistanceCalculator<GeoLocation> harvesineDistanceCalculator = new HarvesineDistanceCalculator();

        TimeCalculator<GeoLocation> timeCalculator = new TimeCalculatorImpl(10.0, harvesineDistanceCalculator);

        RouteFinder<GeoLocation, OrderImpl<GeoLocation>, RouteNodeImpl> routeFinder = new RouteFinderImpl(timeCalculator);

        List<RouteNodeImpl> route = routeFinder.find(startLocations, orders);
        System.out.println(route);
    }

    private static GeoLocation getRandomLocation(){
        return new GeoLocation(Math.random()*10, Math.random()*10);
    }
    private static List<OrderImpl<GeoLocation>> fetchAllOrders() {
        List<OrderImpl<GeoLocation>> orders = new ArrayList<>();
        for(int i = 0 ; i < 2; i++){
            GeoLocation pickupLocation = getRandomLocation();
            GeoLocation dropLocation = getRandomLocation();
            OrderImpl<GeoLocation> order = new OrderImpl<>(pickupLocation, dropLocation, UUID.randomUUID().toString());
            orders.add(order);
        }
        return orders;
    }
}

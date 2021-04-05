package com.lucidity.route.plan;

public abstract class Order {
    private final String id;

    protected Order(final String id) {
        this.id = id;
    }

    public String getId(){
        return id;
    }
}

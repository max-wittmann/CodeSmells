package com.cleancode.main.indentationlevels;

/**
 * Created by mwittman on 25/03/2014.
 */
public class ShippingDetails {
    public final Area area;
    public final Type type;

    public ShippingDetails(Area area, Type type) {
        this.area = area;
        this.type = type;
    }

    public static enum Area {
        METRO, URBAN, RURAL
    }

    public static enum Type {
        NORMAL, EXPRESS
    }
}

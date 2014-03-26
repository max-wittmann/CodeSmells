package com.cleancode.main.indentationlevels;

/**
 * Created by mwittman on 25/03/2014.
 */
public class ShippingDetails {
    public final Area area;
    public final Type type;
    public final Per per;

    private ShippingDetails(Per per, Area area, Type type) {
        this.area = area;
        this.type = type;
        this.per = per;
    }

    public static ShippingDetails createForTotalShippingDetails(Area area, Type type) {
        return new ShippingDetails(Per.TOTAL, area, type);
    }

    public static ShippingDetails createPerItemShippingDetails(Area area, Type type) {
        return new ShippingDetails(Per.ITEM, area, type);
    }

    public static enum Area {
        METRO, URBAN, RURAL
    }

    public static enum Type {
        NORMAL, EXPRESS
    }

    public static enum Per {
        TOTAL, ITEM
    }
}

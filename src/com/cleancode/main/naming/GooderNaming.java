package com.cleancode.main.naming;

import java.util.*;

/**
 * Created by mwittman on 25/03/2014.
 */
public class GooderNaming {

    public static void main (String [] args) {
        Products products = new Products();
        products.addProduct("ProductA", 5);
        products.addProduct("ProductB", 10);

        Discounts discounts = new Discounts();
        discounts.addDiscount("ProductA", 6, 0.5f);
        discounts.addDiscount("ProductB", 12, 0.8f);

        List<OrderItem> cart = new ArrayList<>();
        cart.add(new OrderItem("ProductA", 6));
        cart.add(new OrderItem("ProductB", 11));

        int total = GooderNaming.calculatePrice(products, discounts, cart);
        System.out.println("Total is " + total);
    }

    public static class NoSuchProductException extends RuntimeException {
        public NoSuchProductException(String productName) {
            super("No such product: " + productName);
        }
    }

    public static class Products {
        Map<String, Integer> productPrices = new HashMap<>();

        public void addProduct(String productName, int productPrice) {
            productPrices.put(productName, productPrice);
        }

        public int getUnitPrice(String productName) {
            if(!productPrices.containsKey(productName))
            {
                throw new NoSuchProductException(productName);
            }
            return productPrices.get(productName);
        }
    }

    public static class Discounts {
        Map <String, Float> percentageDiscountForProduct = new HashMap <>();
        Map <String, Integer> minAmountOfProductForDiscount = new HashMap <>();

        public void addDiscount(String productName, int minQuantity, float discountAmount) {
            percentageDiscountForProduct.put(productName, discountAmount);
            minAmountOfProductForDiscount.put(productName, minQuantity);
        }

        public float getDiscountPercentage(OrderItem orderItem) {
            if(!percentageDiscountForProduct.containsKey(orderItem.productName) ||
                    orderItem.quantity < minAmountOfProductForDiscount.get(orderItem.productName)) {
                return 0.0f;
            }
            return percentageDiscountForProduct.get(orderItem.productName);
        }
    }

    public static class OrderItem {
        public OrderItem(String productName, int quantity) {
            this.productName = productName;
            this.quantity = quantity;
        }

        public int quantity;
        public String productName;
    }

    public static int calculatePrice(
        Products products,
        Discounts discounts,
        Collection<OrderItem> shoppingCart) {
        int total = 0;
        for(OrderItem orderItem : shoppingCart) {
            int orderItemCost = orderItem.quantity *  products.getUnitPrice(orderItem.productName);
            float discountPercentage = discounts.getDiscountPercentage(orderItem);
            if(discountPercentage > 0) {
                orderItemCost *= 1.0 - discountPercentage;
            }
            total += orderItemCost;
        }
        return total;
    }



    }

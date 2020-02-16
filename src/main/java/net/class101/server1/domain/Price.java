package net.class101.server1.domain;

public class Price {
    private static final int DELIVERY_PRICE = 5000;

    private final int purchasePrice;
    private final int totalPrice;

    public Price(int purchasePrice) {
        this.purchasePrice = purchasePrice;
        this.totalPrice = comparePrice(purchasePrice);
    }

    private int comparePrice(int purchasePrice) {
        return purchasePrice < 50000 ? purchasePrice + DELIVERY_PRICE : purchasePrice;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}

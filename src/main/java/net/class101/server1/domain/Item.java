package net.class101.server1.domain;

import net.class101.server1.domain.exception.CannotBuyDuplicateClassException;
import net.class101.server1.domain.exception.CannotBuyExcessAmountException;
import net.class101.server1.domain.exception.SoldOutException;

import java.util.Map;
import java.util.Objects;

public abstract class Item {
    private static final String CLASS = "클래스";

    private final int id;
    private final String kind;
    private final String name;
    private final int price;
    private final int stockNumber;

    Item(int id, String kind, String name, int price, int stockNumber) {
        this.id = id;
        this.kind = kind;
        this.name = name;
        this.price = price;
        this.stockNumber = stockNumber;
    }

    Item(int id, String name, int price, int stockNumber) {
        this.id = id;
        this.kind = "";
        this.name = name;
        this.price = price;
        this.stockNumber = stockNumber;
    }

    public Item checkAvailableForPurchase(int itemAmount) {
        if (this.stockNumber <= 0) {
            throw new SoldOutException();
        }
        if (this.stockNumber < itemAmount) {
            throw new CannotBuyExcessAmountException();
        }
        return this;
    }

    public Map<Item, Integer> getItemAmount(Map<Item, Integer> orderList, int itemAmount) {
        if (this.kind.equals(CLASS)) {
            throw new CannotBuyDuplicateClassException();
        }
        int beforeItemAmount = orderList.get(this);
        orderList.put(this, beforeItemAmount + itemAmount);
        return orderList;
    }

    public Item subtractStockNumber(int itemAmount) {
        if (this.kind.equals(CLASS)) {
            return this;
        }
        int afterItemStockNumber = this.stockNumber - itemAmount;
        return new OrderItem(this.id, this.kind, this.name, this.price, afterItemStockNumber);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public String getKind() {
        return kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                stockNumber == item.stockNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stockNumber);
    }
}

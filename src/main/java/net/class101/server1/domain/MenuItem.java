package net.class101.server1.domain;

public class MenuItem extends Item {

    public MenuItem(int id, String name, int price, int stockNumber) {
        super(id, name, price, stockNumber);
    }

    @Override
    public String toString() {
        return String.format("%-6d %-40s %-6d %-5d", getId(), getName(), getPrice(), getStockNumber());
    }
}

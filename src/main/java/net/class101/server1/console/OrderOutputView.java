package net.class101.server1.console;

import net.class101.server1.domain.Item;
import net.class101.server1.domain.Price;

import java.util.List;
import java.util.Map;

public class OrderOutputView {
    private static final int LIMIT_PRICE = 50000;

    public void showTopMenu() {
        System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
    }

    public void showOrderMenu(List<Item> menus) {
        System.out.println(String.format("%-6s %-45s %-6s %-5s", "상품번호", "상품명", "판매가격", "재고수"));
        menus.forEach(menu -> System.out.println(menu.toString()));
        System.out.println();
    }

    public void showOrderList(Map<Item, Integer> orders) {
        System.out.println("-----------------------------------------------------------------------------");
        for (Item item : orders.keySet()) {
            System.out.println(item.getName() + " - " + orders.get(item) + "개");
        }
    }

    public void showItemId() {
        System.out.print("상품 번호 : ");
    }

    public void showItemAmount() {
        System.out.print("수량 : ");
    }

    public void showQuitMessage() {
        System.out.println("고객님의 주문 감사합니다.");
    }

    public void showTotalPrice(Price price) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("주문 금액 : " + price.getPurchasePrice() + "원");

        if (price.getPurchasePrice() < LIMIT_PRICE) {
            System.out.println("배송비 : 5000");
        }

        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("지불금액 : " + price.getTotalPrice() + "원");
    }

    public void showError(String message) {
        System.out.println(message);
    }
}

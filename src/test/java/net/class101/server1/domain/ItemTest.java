package net.class101.server1.domain;

import net.class101.server1.domain.exception.CannotBuyDuplicateClassException;
import net.class101.server1.domain.exception.CannotBuyExcessAmountException;
import net.class101.server1.domain.exception.SoldOutException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemTest {

    private static final Map<Item, Integer> ORDER_LIST = new HashMap<>();
    private static final int ITEM_AMOUNT = 1;

    private Item kitItem;
    private Item classItem;
    private Item soldOutItem;

    @BeforeEach
    void setUp() {
        ORDER_LIST.put(new OrderItem(1, "클래스", "test1", 50000, 99999), 1);
        ORDER_LIST.put(new OrderItem(2, "키트", "test2", 10000, 1), 2);
        ORDER_LIST.put(new OrderItem(3, "클래스", "test3", 60000, 99999), 1);
        ORDER_LIST.put(new OrderItem(4, "키트", "test4", 200000, 2), 4);
        ORDER_LIST.put(new OrderItem(5, "클래스", "test5", 300000, 99999), 1);

        kitItem = new OrderItem(4, "키트", "test4", 200000, 1);
        classItem = new OrderItem(5, "클래스", "test5", 300000, 99999);
        soldOutItem = new OrderItem(7, "키트", "soldOutTest", 10000, 0);
    }

    @Test
    @DisplayName("재고로 인한 구매가 가능한지 테스트")
    void checkAvailableForPurchaseTest() {
        assertThat(kitItem.checkAvailableForPurchase(1)).isEqualTo(kitItem);
    }

    @Test
    @DisplayName("재고가 없을경우 예외처리 테스트")
    void checkSoldOutStockNumberTest() {
        assertThrows(SoldOutException.class, () -> soldOutItem.checkAvailableForPurchase(1));
    }

    @Test
    @DisplayName("남은 재고 이상으로 구매할 경우 예외처리 되는지 테스트")
    void checkExcessAmountStockNumberTest() {
        assertThrows(CannotBuyExcessAmountException.class, () -> kitItem.checkAvailableForPurchase(2));
    }

    @Test
    @DisplayName("기존에 주문한 물건이 맵에 잘 들어가는 테스트")
    void getExistItemAmountTest() {
        Map<Item, Integer> expectItems = compareOrderList();
        expectItems.put(kitItem, 5);

        assertThat(kitItem.getItemAmount(ORDER_LIST, ITEM_AMOUNT)).isEqualTo(expectItems);
    }

    private Map<Item, Integer> compareOrderList() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(new OrderItem(1, "클래스", "test1", 50000, 99999), 1);
        items.put(new OrderItem(2, "키트", "test2", 10000, 1), 2);
        items.put(new OrderItem(3, "클래스", "test3", 60000, 99999), 1);
        items.put(new OrderItem(4, "키트", "test4", 200000, 2), 4);
        items.put(new OrderItem(5, "클래스", "test5", 300000, 99999), 1);

        return  items;
    }

    @Test
    @DisplayName("종목이 클래스인 경우 구매 못하게 예외처리 테스트")
    void CannotBuyDuplicateClassExceptionTest() {
        assertThrows(CannotBuyDuplicateClassException.class, () -> classItem.getItemAmount(ORDER_LIST, ITEM_AMOUNT));
    }

    @Test
    @DisplayName("item이 클래스인 경우 재고수 테스트")
    void subtractClassStockNumberTest() {
        Item expectClassItem = new OrderItem(5, "클래스", "test5", 300000, 99999);
        assertThat(classItem.subtractStockNumber(ITEM_AMOUNT)).isEqualTo(expectClassItem);
    }
    @Test
    @DisplayName("item이 키트인 경우 재고수 테스트")
    void subtractKitStockNumberTest() {
        Item expectKitItem =  new OrderItem(4, "키트", "test4", 200000, 0);
        assertThat(kitItem.subtractStockNumber(ITEM_AMOUNT)).isEqualTo(expectKitItem);
    }

    @AfterEach
    void tearDown() {
        ORDER_LIST.clear();
    }
}
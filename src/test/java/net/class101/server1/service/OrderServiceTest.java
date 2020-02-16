package net.class101.server1.service;

import net.class101.server1.dao.OrderDao;
import net.class101.server1.domain.Item;
import net.class101.server1.domain.MenuItem;
import net.class101.server1.domain.OrderItem;
import net.class101.server1.service.exception.OrderServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderDao orderDao;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderDao = mock(OrderDao.class);
        orderService = new OrderService(orderDao);
    }

    @Test
    @DisplayName("상품 목록 테스트")
    void getMenusTest() {
        when(orderDao.findAll()).thenReturn(getMenuItem());

        List<Item> items = orderService.getMenus();

        verify(orderDao, atLeastOnce()).findAll();
        assertThat(items).isEqualTo(getMenuItem());
    }

    @Test
    @DisplayName("주문 리스트 갱신 테스트")
    void getOrderListTest() {
        when(orderDao.findById(2)).thenReturn(new OrderItem(2, "키트", "testName2", 10000, 2));

        Map<Item, Integer> expectItemOrderList = getOrderListItems();
        expectItemOrderList.put(new OrderItem(2, "키트", "testName2", 10000, 2), 2);

        assertThat(orderService.getOrderList(2, 1, getOrderListItems())).isEqualTo(expectItemOrderList);

        verify(orderDao, atLeastOnce()).findById(2);
        verify(orderDao, atLeastOnce()).update(new OrderItem(2, "키트", "testName2", 10000, 1));
    }

    @Test
    @DisplayName("클래스로 인한 주문 리스트 갱신 예외 테스트")
    void CannotBuyDuplicateClassExceptionTest() {
        when(orderDao.findById(1)).thenReturn(new OrderItem(1, "클래스", "testName1", 50000, 99999));

        assertThrows(OrderServiceException.class, () -> orderService.getOrderList(1, 1, getOrderListItems()));

        verify(orderDao, atLeastOnce()).findById(1);
        verify(orderDao, never()).update(new OrderItem(1, "클래스", "testName1", 50000, 99999));
    }

    @Test
    @DisplayName("Sold Out 으로 인한 주문 리스트 갱신 예외 테스트")
    void SoldOutExceptionTest() {
        when(orderDao.findById(4)).thenReturn(new OrderItem(4, "키트", "testName4", 200000, 0));

        assertThrows(OrderServiceException.class, () -> orderService.getOrderList(4, 1, getOrderListItems()));
        verify(orderDao, atLeastOnce()).findById(4);
        verify(orderDao, never()).update(new OrderItem(4, "키트", "testName4", 200000, 0));
    }

    @Test
    @DisplayName("초과 구매로 인한 주문 리스트 갱신 예외 테스트")
    void CannotBuyExcessAmountExceptionTest() {
        when(orderDao.findById(6)).thenReturn(new OrderItem(6, "키트", "testName6", 15000, 1));

        assertThrows(OrderServiceException.class, () -> orderService.getOrderList(6, 2, getOrderListItems()));
        verify(orderDao, atLeastOnce()).findById(6);
        verify(orderDao, never()).update(new OrderItem(6, "키트", "testName6", 15000, 1));
    }

    @Test
    @DisplayName("디비에 없는 값을 찾을 경우 예외처리 테스트")
    void name() {
        when(orderDao.findById(7)).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(OrderServiceException.class, () -> orderService.getOrderList(7, 1, getOrderListItems()));
        verify(orderDao, atLeastOnce()).findById(7);
    }

    @Test
    @DisplayName("가격 총합 구하기 테스트")
    void getTotalPriceTest() {
        assertThat(orderService.getTotalPrice(getOrderListItems()).getPurchasePrice()).isEqualTo(620000);
        assertThat(orderService.getTotalPrice(getOrderListItems()).getTotalPrice()).isEqualTo(620000);

        Map<Item, Integer> includeDeliveryFeeItems = new HashMap<>();
        includeDeliveryFeeItems.put(new OrderItem(1, "클래스", "testName1", 49999, 9999), 1);
        assertThat(orderService.getTotalPrice(includeDeliveryFeeItems).getPurchasePrice()).isEqualTo(49999);
        assertThat(orderService.getTotalPrice(includeDeliveryFeeItems).getTotalPrice()).isEqualTo(54999);
    }

    @Test
    @DisplayName("새로 주문한 물건이 맵에 잘 들어가는 테스트")
    void getNewItemAmount() {
        when(orderDao.findById(6)).thenReturn(new OrderItem(6, "키트", "testName6", 15000, 3));

        assertThat(orderService.getOrderList(6, 2, getOrderListItems())
                .get(new OrderItem(6, "키트", "testName6", 15000, 3))).isEqualTo(2);

        verify(orderDao, atLeastOnce()).findById(6);
        verify(orderDao, atLeastOnce()).update(new OrderItem(6, "키트", "testName6", 15000, 1));

    }

    private List<Item> getMenuItem() {
        return Arrays.asList(
                new MenuItem(1, "testName1", 50000, 99999),
                new MenuItem(2, "testName2", 10000, 2),
                new MenuItem(3, "testName3", 60000, 99999),
                new MenuItem(4, "testName4", 200000, 0),
                new MenuItem(5, "testName5", 300000, 99999),
                new MenuItem(6, "testName6", 15000, 1)
        );
    }

    private Map<Item, Integer> getOrderListItems() {
        Map<Item, Integer> items = new HashMap<>();
        items.put(new OrderItem(1, "클래스", "testName1", 50000, 99999), 1);
        items.put(new OrderItem(2, "키트", "testName2", 10000, 2), 1);
        items.put(new OrderItem(3, "클래스", "testName3", 60000, 99999), 1);
        items.put(new OrderItem(4, "키트", "testName4", 200000, 4), 1);
        items.put(new OrderItem(5, "클래스", "testName5", 300000, 99999), 1);

        return items;
    }

}
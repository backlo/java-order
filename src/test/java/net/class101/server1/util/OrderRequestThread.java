package net.class101.server1.util;

import net.class101.server1.domain.Item;
import net.class101.server1.service.OrderService;
import net.class101.server1.service.exception.OrderServiceException;

import java.util.Map;
import java.util.concurrent.ExecutorService;

public class OrderRequestThread implements Runnable {
    private final Map<Item, Integer> orderList;
    private final OrderService orderService;
    private final int itemId;
    private final int itemAmount;
    private final ExecutorService executorService;

    public OrderRequestThread(Map<Item, Integer> orderList, OrderService orderService, int itemId, int itemAmount, ExecutorService executorService) {
        this.orderList = orderList;
        this.orderService = orderService;
        this.itemId = itemId;
        this.itemAmount = itemAmount;
        this.executorService = executorService;
    }

    @Override
    public void run() {
        try {
            orderService.getOrderList(itemId, itemAmount, orderList);
            executorService.shutdown();
        } catch (OrderServiceException e) {
            throw new OrderServiceException(e);
        }
    }
}

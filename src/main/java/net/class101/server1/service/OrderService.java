package net.class101.server1.service;

import net.class101.server1.dao.OrderDao;
import net.class101.server1.domain.Item;
import net.class101.server1.domain.Price;
import net.class101.server1.domain.exception.CannotBuyDuplicateClassException;
import net.class101.server1.domain.exception.CannotBuyExcessAmountException;
import net.class101.server1.domain.exception.SoldOutException;
import net.class101.server1.service.exception.OrderServiceException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Map;

public class OrderService {
    private final OrderDao orderDao;

    public OrderService() {
        orderDao = new OrderDao();
    }

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Item> getMenus() {
        return orderDao.findAll();
    }

    public Map<Item, Integer> getOrderList(int itemId, int itemAmount, Map<Item, Integer> orderList) {
        try {
            Item findItem = orderDao.findById(itemId).checkAvailableForPurchase(itemAmount);

            orderList = addOrderList(findItem, itemAmount, orderList);
            orderDao.update(findItem.subtractStockNumber(itemAmount));

            return orderList;
        } catch (EmptyResultDataAccessException e) {
            throw new OrderServiceException();
        } catch (SoldOutException | CannotBuyDuplicateClassException | CannotBuyExcessAmountException e) {
            throw new OrderServiceException(e);
        }
    }

    private Map<Item, Integer> addOrderList(Item findItem, int itemAmount, Map<Item, Integer> orderList) {
        if (orderList.containsKey(findItem)) {
            return findItem.getItemAmount(orderList, itemAmount);
        }
        orderList.put(findItem, itemAmount);
        return orderList;
    }

    public Price getTotalPrice(Map<Item, Integer> orderList) {
        int totalPrice = orderList.keySet().stream()
                .mapToInt(item -> item.getPrice() * orderList.get(item))
                .sum();

        return new Price(totalPrice);
    }

}

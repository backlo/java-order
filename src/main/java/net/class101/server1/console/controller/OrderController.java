package net.class101.server1.console.controller;

import net.class101.server1.console.dto.InputOrderItemAmountDto;
import net.class101.server1.console.dto.InputOrderItemIdDto;
import net.class101.server1.console.dto.InputTopMenuDto;
import net.class101.server1.console.OrderInputView;
import net.class101.server1.console.OrderOutputView;
import net.class101.server1.domain.Item;
import net.class101.server1.domain.Price;
import net.class101.server1.console.dto.exception.NoMatchTopMenuInitialsException;
import net.class101.server1.service.OrderService;
import net.class101.server1.support.exception.CanNotReadFileException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderController {
    private final OrderOutputView outputView;
    private final OrderInputView inputView;
    private final OrderService orderService;

    public OrderController() throws CanNotReadFileException {
        outputView = new OrderOutputView();
        inputView = new OrderInputView();
        orderService = new OrderService();
    }

    public void run() {
        while (true) {
            try {
                String topMenu = chooseTopMenu().getTopMenu();

                if (topMenu.equals("q")) {
                    outputView.showQuitMessage();
                    break;
                }

                outputView.showOrderMenu(getItemMenus());
                doPayment(purchaseItem());
            } catch (NoMatchTopMenuInitialsException e) {
                outputView.showError(e.getMessage());
            }
        }
    }

    private InputTopMenuDto chooseTopMenu() {
        outputView.showTopMenu();
        return new InputTopMenuDto(inputView.inputTopMenu());
    }

    private List<Item> getItemMenus() {
        return orderService.getMenus();
    }

    private Map<Item, Integer> purchaseItem() {
        Map<Item, Integer> orderList = new HashMap<>();

        while (true) {
            try {
                int itemId = orderItemId().getItemId();
                if (itemId == -1) {
                    break;
                }
                int itemAmount = orderItemAmount().getItemAmount();
                orderList = orderService.getOrderList(itemId, itemAmount, orderList);
            } catch (Exception e) {
                outputView.showError(e.getMessage());
            }
        }

        return orderList;
    }

    private InputOrderItemIdDto orderItemId() {
        outputView.showItemId();
        return new InputOrderItemIdDto(inputView.inputItemId());
    }

    private InputOrderItemAmountDto orderItemAmount() {
        outputView.showItemAmount();
        return new InputOrderItemAmountDto(inputView.inputItemAmount());
    }

    private void doPayment(Map<Item, Integer> orderList) {
        if(orderList.isEmpty()) {
            return;
        }
        outputView.showOrderList(orderList);

        Price purchasePrice = orderService.getTotalPrice(orderList);
        outputView.showTotalPrice(purchasePrice);
    }
}

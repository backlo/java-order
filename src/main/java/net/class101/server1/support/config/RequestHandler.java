package net.class101.server1.support.config;

import net.class101.server1.console.controller.OrderController;

import java.util.concurrent.ExecutorService;

public class RequestHandler implements Runnable {

    private final ExecutorService executorService;

    public RequestHandler(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void run() {
        OrderController orderController = new OrderController();
        orderController.run();
        executorService.shutdown();
    }
}

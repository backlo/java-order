package net.class101.server1;

import net.class101.server1.console.controller.OrderController;
import net.class101.server1.support.context.ContextLoaderListener;
import net.class101.server1.support.exception.CanNotReadFileException;

public class SeverContainerInitializer {

    private final ContextLoaderListener contextLoaderListener;

    public SeverContainerInitializer() {
        this.contextLoaderListener = new ContextLoaderListener();
    }

    public void run() {
        try {
            contextLoaderListener.contextInitialized();

            OrderController orderController = new OrderController();
            orderController.run();
        } catch (CanNotReadFileException e) {
            System.out.println("Can Not ReadFile. So Please Check This File And Try it Again.");
        }
    }
}

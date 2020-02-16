package net.class101.server1;

import net.class101.server1.support.config.RequestExceptionHandler;
import net.class101.server1.support.config.RequestHandler;
import net.class101.server1.support.context.ContextLoaderListener;
import net.class101.server1.support.exception.CanNotReadFileException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerContainerInitializer {
    private static final int NUMBER_OF_CLIENT = 1;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private final ContextLoaderListener contextLoaderListener;

    public ServerContainerInitializer() {
        this.contextLoaderListener = new ContextLoaderListener();
    }

    public void run() {
        try {
            contextLoaderListener.contextInitialized();

            RequestExceptionHandler exceptionHandler = new RequestExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);

            for (int i = 0; i < NUMBER_OF_CLIENT; i++) {
                executorService.execute(new RequestHandler(executorService));
                occurThreadException(exceptionHandler);
            }

            while (!executorService.isTerminated()) { }
            System.out.println("All Requests are completed");
        } catch (CanNotReadFileException e) {
            System.out.println("Can Not ReadFile. So Please Check This File And Try it Again.");
        }
    }

    private void occurThreadException(RequestExceptionHandler exceptionHandler) {
        if (!exceptionHandler.getErrorMessage().isEmpty()) {
            System.out.println("Error 발생 -> " + exceptionHandler.getErrorMessage());
        }
    }
}
